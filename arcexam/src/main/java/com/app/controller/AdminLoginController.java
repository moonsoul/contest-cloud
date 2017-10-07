package com.app.controller;

import com.app.common.BaseController;
import com.app.common.ContainsKeys;
import com.app.common.RetResult;
import com.app.dto.UserDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminLoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    @RequestMapping(path = "/login")
    //@ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        logger.info("login");
        return "admin/login";
    }

    @RequestMapping(path = "/hi")
    //@ResponseBody
    public String hi(HttpServletRequest request, HttpServletResponse response) {
        logger.info("hi");
        return "admin/hi";
    }

    @RequestMapping(path = "/doLogin")
    //@ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("username") String usercode, @RequestParam("password") String password) {
        String ip = request.getRemoteAddr();
        Map mvm = new HashMap();
        mvm.put("username", usercode);
        mvm.put("password", password);
        mvm.put("ip", ip);

        logger.info("before login");

        RetResult<UserDTO> ret = null;
        ret = userManageService.doAdminLogin(mvm);

        UserDTO user = null;
        if (null != ret) {
            String retMessage = ret.getRetMessage();
            if (StringUtils.equals(retMessage, ContainsKeys.RET_ERR)) {
                return "error/500";
            }

            if (!StringUtils.equals(retMessage, ContainsKeys.RET_OK)) {
                model.addAttribute("message", retMessage);
                return "admin/login";
            }
            user = new UserDTO();
            try {
                BeanUtils.populate(user, (Map)ret.getRetObj());
            } catch (Exception e) {
                e.printStackTrace();
                return "error/500";
            }

            String userid = user.getUserid();
            String authkey = user.getAuthkey();
            String usertype = user.getUsertype().toString();

            setAuthAndTokenCookies(response, userid, usertype, authkey, usercode);
        } else {
            return "admin/login";
        }

        logger.info("after login");

        model.addAttribute("currentUser", user);
        return "admin/index";
    }

    @RequestMapping(path = "/doLogout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(request.getAttribute("userid") != null){
            String userid = request.getAttribute("userid").toString();
            redisCacheService.removeRawCacheMapValues("authkey", userid);
        }
        clearAuthAndTokenCookies(response);
        return "admin/login";
    }


    public static void main(String[] args) throws Exception {

    }
}
