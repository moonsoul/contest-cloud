package com.app.controller;

import com.app.common.BaseController;
import com.app.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/stu")
public class StudentLoginController extends BaseController{
	private static final Logger logger	= LoggerFactory.getLogger(StudentLoginController.class);

    @RequestMapping(path="/login")
    //@ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        logger.info("login");
        return "stu/login";
    }

    @RequestMapping(path="/hi")
    //@ResponseBody
    public String hi(HttpServletRequest request, HttpServletResponse response) {
        logger.info("hi");
        if(request.getSession().getAttribute("a") == null) {
            request.getSession().setAttribute("a", "a");
        }

        return "stu/hi";
    }

    @RequestMapping(path="/doLogin")
    //@ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam( "username") String username, @RequestParam("password") String password) {
        Map mvm = new HashMap();
        mvm.put("username", username);
        mvm.put("password", password);

        UserDTO user = null;
        try {
            user = invokeRemotePost("http://127.0.0.1:8018/arcuser/stu/doLogin", mvm, UserDTO.class);
        } catch (RestClientException e)         {
            logger.info(e.getMessage());
        }

        if(null != user) {
            String userid = user.getUserid();
            String authkey = user.getAuthkey();
            String usertype = user.getUsertype().toString();

            setAuthAndTokenCookies(response, userid, usertype, authkey);

            redisCacheService.setRawCacheMapValue("authkey", userid, authkey);
        }
        else{
            return "stu/login";
        }

        return "stu/index";
    }

    @RequestMapping(path="/users")
    @ResponseBody
    public List users(HttpServletRequest request, Model model) {
        return null;
    }
    
    
    public static void main(String[] args) throws Exception {

    }
}
