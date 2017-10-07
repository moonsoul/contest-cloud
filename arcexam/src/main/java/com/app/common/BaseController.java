package com.app.common;

import com.app.service.RedisCacheService;
import com.app.service.UserManageService;
import com.app.utils.JWTHelper;
import com.qq.base.common.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class BaseController {
	private static final Logger	logger	= LoggerFactory.getLogger(BaseController.class);

    public static String OK = "200";
    public static String ERROR = "300";
    public static String TIME_OUT = "301";
    public static String CLOSE_CURRENT = "closeCurrent";
    // DWZ框架信息回调函数后的返回路径
    public static String DWZ_COMMON_PAGE = "dwzCommon";
    // DWZ框架超时回调函数的返回路径
    public static String DWZ_TIME_OUT = "dwzTimeout";

    @Autowired
    protected RedisCacheService<Object> redisCacheService;

    @Autowired
    protected UserManageService userManageService;

    protected void setAuthAndTokenCookies(HttpServletResponse response, String userid, String usertype, String authkey, String usercode){
        String token = JWTHelper.sign(userid + "_" + usertype + "_" + usercode, 60L * 1000L * ContainsKeys.EXPIRE_TIME);
        //封装成对象返回给客户端
        Cookie cookie = new Cookie("authkey",authkey);
        //cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        cookie = new Cookie("token",token);
        //cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    protected void clearAuthAndTokenCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("authkey",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 执行DWZ框架的回调函数,封装了操作的结果
     *
     * @param model spring Model 传输对象
     * @param statusCode 表示在当前页面操作是否成功，调用BaseController 常量 OK 表示成功；ERROR 表示失败；TIMEOUT 超时
     * @param callbackType 表示当前操作完成是否关闭当前页面 null 表示不关闭，调用BaseController 常量 CLOSE_CURRENT 表示关闭
     * @param message 成功或失败后的自定义提示信息
     * @param navTabId 返回后需要刷新的 tab 的ID ,如不填写则不刷新
     */
    public void dwzCallback(Model model, String statusCode, String callbackType, String message, String navTabId) {
        model.addAttribute("statusCode", StringUtils.isNotEmpty(statusCode) ? statusCode : OK);
        model.addAttribute("callbackType", StringUtils.isNotEmpty(callbackType) ? callbackType : "");
        model.addAttribute("message", StringUtils.isNotEmpty(message) ? message : "操作成功！");
        model.addAttribute("navTabId", StringUtils.isNotEmpty(navTabId) ? navTabId : "");
    }


    /**
     * 执行DWZ框架的回调函数,封装了操作的结果
     *
     * @param model Spring Model 传输对象
     * @param statusCode 表示在当前页面操作是否成功，调用BaseController 常量 OK 表示成功；ERROR 表示失败；TIMEOUT 超时
     * @param message 成功或失败后的自定义提示信息
     */
    public void dwzCallback(Model model, String statusCode, String message) {
        dwzCallback(model, statusCode, null, message, null);
    }

    /**
     * 绑定分页对象参数
     *
     * @param model model Spring Model 对象
     * @param page 分页对象
     * @param listName 返回结果集的变量命名
     */
    public void bindPageQuery(Model model, Page page, String listName) {
        List list = (List) page.getResult();
        model.addAttribute("totalRows", page.getTotalCountForInteger());
        model.addAttribute("currentPage", page.getCurrentPageNo());
        model.addAttribute(listName, list);
        String[] param = new String[] { Integer.toString(list.size()), page.getTotalCountForInteger().toString(),
                Long.toString(page.getCurrentPageNo()) };
    }

    public void dwzCallback(Model model, String statusCode, String callbackType, String message, String navTabId, String rel, String forwardUrl) {
        model.addAttribute("statusCode", StringUtils.isNotEmpty(statusCode) ? statusCode : OK);
        model.addAttribute("callbackType", StringUtils.isNotEmpty(callbackType) ? callbackType : "");
        model.addAttribute("message", StringUtils.isNotEmpty(message) ? message : "");
        model.addAttribute("rel", StringUtils.isNotEmpty(rel) ? rel : "");
        model.addAttribute("navTabId", StringUtils.isNotEmpty(navTabId) ? navTabId : "");
        model.addAttribute("forwardUrl", StringUtils.isNotEmpty(forwardUrl) ? forwardUrl : "");
    }

    public void renderHtml(HttpServletResponse response, String text)
    {
        try
        {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(text);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {

    }
}
