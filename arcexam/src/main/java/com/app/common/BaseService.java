package com.app.common;

import com.app.dao.RedisCacheDAO;
import com.app.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class BaseService {
	private static final Logger	logger	= LoggerFactory.getLogger(BaseService.class);

    @Autowired
    protected RedisCacheDAO<Object> redisCacheDAO;

    @Autowired
    protected RestTemplate restTemplate;

    protected <T> T invokeRemotePost(String url, Map valmap, Class<T> responseType) {
        MultiValueMap mvm = new LinkedMultiValueMap();
        mvm.setAll(valmap);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(mvm, requestHeaders);
        return restTemplate.postForObject(url, requestEntity, responseType);
    }

    protected <T> T invokeRemotePost_(String url, Object obj, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JsonMapper json = JsonMapper.nonEmptyMapper();
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toJson(obj), headers);
        return restTemplate.postForObject(url, formEntity, responseType);
    }

    protected <T> T invokeRemoteGet(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    public static void main(String[] args) throws Exception {

    }
}
