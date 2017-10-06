package com.app.filter;

import com.app.application.SpringUtil;
import com.app.common.ContainsKeys;
import com.app.service.RedisCacheService;
import com.app.utils.JWTHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="SystemSecurityFilter",urlPatterns="/*",initParams = {
		@WebInitParam(name = "sqlkeys", value = "update,select,delete,alter,create,drop,exec"),
		@WebInitParam(name = "loginNeedPaths", value = "admin/;stu/;html;monitor/"),
		@WebInitParam(name = "loginUnNeedPaths", value = "admin/login;admin/dologin;stu/login;stu/dologin;student/exam/checkExamstudentStatusByAjax")
})
public class SystemSecurityFilter implements Filter {

	private String	keys				= null;

	private String	loginNeedPaths[]	= null;		// 需要登录的路径

	private String	loginUnNeedPaths[]	= null;		// 得到不必登录的过滤配置路径

	private static RedisCacheService<Object> redisCacheService = null;

	public void destroy()
	{
		this.keys = null;
		this.loginNeedPaths = null;
		this.loginUnNeedPaths = null;
	}


	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException
	{
		if(redisCacheService == null){
			redisCacheService = (RedisCacheService<Object>) SpringUtil.getBean("redisCacheService");
		}

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		if (null != loginNeedPaths) {// 必须配置参数
			boolean flag = false;
			for (int i = 0; i < loginNeedPaths.length; i++) {
				if (request.getRequestURL().toString().toLowerCase().indexOf(loginNeedPaths[i].toLowerCase()) != -1) {
					flag = true;
					break;
				}
			}
			String requesturl = request.getRequestURL().toString();
			if (flag) {// 如果是配置的过滤路径
				// 首先判断是否是可以忽略的路径
				if (null != loginUnNeedPaths) {
					String allurl = requesturl + "?" + request.getQueryString();
					for (int i = 0; i < loginUnNeedPaths.length; i++) {
						if (allurl.toLowerCase().indexOf(loginUnNeedPaths[i].toLowerCase()) != -1) {// 如果无需登录操作直接跳转
							filterChain.doFilter(request, response);
							return;
						}
					}
				}

				String token = null;
				String authkey = null;
				Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
				for(Cookie cookie : cookies){
					String key = cookie.getName();// get the cookie name
					if(StringUtils.equalsIgnoreCase("token", key)) {
						token = cookie.getValue(); // get the cookie value
					}else if(StringUtils.equalsIgnoreCase("authkey", key)) {
						authkey = cookie.getValue(); // get the cookie value
					}
				}

				if(StringUtils.isEmpty(authkey) || StringUtils.isEmpty(token)){
					RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.STU_LOGIN_URL);
					saveError(request, "请登陆！");

					clearAuthAndTokenCookies(response);

					rd.forward(request, response);
					return;
				}

				String ctx=request.getContextPath();
				String path=requesturl.split(ctx)[1];
				token = JWTHelper.unsign(token, String.class);
				if(StringUtils.isEmpty(token)) {
					RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.STU_LOGIN_URL);
					saveError(request, "请重新登陆！");

					clearAuthAndTokenCookies(response);

					rd.forward(request, response);
					return;
				}
				String vals[] = token.split("_");

				String authkeyredis = redisCacheService.getRawCacheMapValue("authkey", vals[0]).toString();
				if(!StringUtils.equalsIgnoreCase(authkeyredis, authkey)){
					RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.STU_LOGIN_URL);
					saveError(request, "请重新登陆！");

					clearAuthAndTokenCookies(response);

					rd.forward(request, response);
					return;
				}

				Long userType=Long.parseLong(vals[1]);
				if (userType != null){
					if(!checkpath(path, userType.longValue())){
						RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.STU_LOGIN_URL);
						saveError(request, "当前用户无此权限，请重新登陆！");

						clearAuthAndTokenCookies(response);

						rd.forward(request, response);
						return;
					}
				}else{
					RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.STU_LOGIN_URL);
					saveError(request, "当前用户无此权限，请重新登陆！");

					clearAuthAndTokenCookies(response);

					rd.forward(request, response);
					return;
				}
				
				/*后续完善
				// 禁止包含sql注入的查询 add by zhaoyuyang 2010.10.29
				Enumeration e = request.getParameterNames();
				// String aa = request.getQueryString();
				StringBuffer psb = new StringBuffer("");
				while (e.hasMoreElements()) {
					String pname = e.nextElement().toString();
					if (!"method".equals(pname) && !"action".equals(pname)) {
						psb.append(request.getParameter(pname) + "----");
					}
				}
				String ps = psb.toString();

				if (keys != null) {
					String key[] = keys.split(",");
					for (int i = 0; i < key.length; i++) {
						if (ps.indexOf(key[i]) != -1) {
							// 如发现包含关键字,则在此进行处理
							request.setAttribute("error", key[i].trim());
							RequestDispatcher rd = request.getSession().getServletContext().getRequestDispatcher(ContainsKeys.SESSION_ERROR_SQL_URL);
							rd.forward(request, response);
							return;
						}
					}
				}*/

				filterChain.doFilter(request, response);

				authkeyredis = redisCacheService.getRawCacheMapValue("authkey", vals[0]).toString();
				setAuthAndTokenCookies(response, vals[0], vals[1], authkeyredis);
			}else {
				filterChain.doFilter(request, response);
			}

		}

	}
		private boolean checkpath(String path, Long usertype){
			boolean t=false;
			if(usertype == 0l){//考生
			for(String p:ContainsKeys.stupath){
				if(path.startsWith(p)){
					t=true;
				}
			}
			}else if(usertype == 1l){//管理员
				for(String p:ContainsKeys.adminpath){
					if(path.startsWith(p)){
						t=true;
					}
			}
			}else if(usertype == 2l){//监控
				for(String p:ContainsKeys.monitorpath){
					if(path.startsWith(p)){
						t=true;
					}
				}
			}
			
			return t;
		}
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.keys = filterConfig.getInitParameter("sqlkeys");

		// 需要验证是否登录的路径，必须配置
		String loginNeedPath = filterConfig.getInitParameter("loginNeedPaths");
		if (null != loginNeedPath) {
			loginNeedPaths = loginNeedPath.split(";");
		}

		// 无需验证是否登录，此路径是loginNeedPaths中内部的路径
		String loginUnNeedPath = filterConfig.getInitParameter("loginUnNeedPaths");
		if (null != loginUnNeedPath) {
			loginUnNeedPaths = loginUnNeedPath.split(";");
		}
	}

	protected void saveError(HttpServletRequest request, String message)
	{
		if (StringUtils.isEmpty(message)) {
			return;
		}
		request.setAttribute("ERROR", message);
	}

	private void clearAuthAndTokenCookies(HttpServletResponse response){
		Cookie cookie = new Cookie("authkey",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		cookie = new Cookie("token",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private void setAuthAndTokenCookies(HttpServletResponse response, String userid, String usertype, String authkey){
		String token = JWTHelper.sign(userid + "_" + usertype, 60L * 1000L * 60L);
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
}
