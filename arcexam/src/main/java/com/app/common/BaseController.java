package com.app.common;

import com.app.service.RedisCacheService;
import com.app.utils.JWTHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class BaseController {
	private static final Logger logger	= LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected RedisCacheService<Object> redisCacheService;

    @Autowired
    protected RestTemplate restTemplate;

    protected <T> T invokeRemotePost(String url, Map valmap, Class<T> responseType) {
        MultiValueMap mvm = new LinkedMultiValueMap();
        mvm.setAll(valmap);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(mvm, requestHeaders);
        return restTemplate.postForObject(url,requestEntity, responseType);
    }

    protected <T> T invokeRemoteGet(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    protected void setAuthAndTokenCookies(HttpServletResponse response, String userid, String usertype, String authkey){
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

    public static void main(String[] args) throws Exception {

    }
}
