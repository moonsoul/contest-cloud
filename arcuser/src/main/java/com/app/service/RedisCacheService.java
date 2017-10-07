package com.app.service;

import com.app.dao.RedisCacheDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("redisCacheService")
public class RedisCacheService <T> {
	private final static Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

	@Autowired
	private RedisCacheDAO<T> redisCacheDAO;

	public <T> ValueOperations<String,T> setCacheObject(String key,T value)
	{
		return redisCacheDAO.setCacheObject(key, value);
	}

	public <T> ValueOperations<String,T> setCacheObject(String key,T value, long timeout)
	{
		return redisCacheDAO.setCacheObject(key,value,timeout);
	}
	
	public <T> T getCacheObject(String key/*,ValueOperations<String,T> operation*/)
	{
		return redisCacheDAO.getCacheObject(key);
	}

	public <T> ListOperations<String, T> setCacheList(String key,List<T> dataList)
	{
		return redisCacheDAO.setCacheList(key, dataList);
	}

	public <T> List<T> getCacheList(String key)
	{
		return redisCacheDAO.getCacheList(key);
	}

	public <T> BoundSetOperations<String,T> setCacheSet(String key,Set<T> dataSet)
	{
		return redisCacheDAO.setCacheSet(key, dataSet);
	}

	public Set<T> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/)
	{
		return redisCacheDAO.getCacheSet(key);
	}

	public <T> HashOperations<String,String,T> setCacheMap(String key, Map<String,T> dataMap, long timeout)
	{
		return redisCacheDAO.setCacheMap(key, dataMap, timeout);
	}
	
	public <T> HashOperations<String,String,T> setCacheMap(String key,Map<String,T> dataMap)
	{
		return redisCacheDAO.setCacheMap(key, dataMap, 600);
	}

	public <T> Map<String,T> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	{
		return redisCacheDAO.getCacheMap(key);
	}

	public <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap)
	{
		return redisCacheDAO.setCacheIntegerMap(key, dataMap);
	}

	public <T> Map<Integer,T> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	{
		return redisCacheDAO.getCacheIntegerMap(key);
	}
	
	public HashOperations setRawCacheMap(String key,Map dataMap, long timeout)
	{
		return redisCacheDAO.setRawCacheMap(key, dataMap, timeout);
	}
	
	public HashOperations setRawCacheMap(String key,Map dataMap)
	{
		return redisCacheDAO.setRawCacheMap(key, dataMap, 600);
	}
	
	public Map getRawCacheMap(String key){
		return redisCacheDAO.getRawCacheMap(key);
	}
	
	public HashOperations setRawCacheMapValue(String key, Object datakey, Object value){
		return redisCacheDAO.setRawCacheMapValue(key, datakey, value);
	}

	public Object getRawCacheMapValue(String key, Object datakey){
		return redisCacheDAO.getRawCacheMapValue(key, datakey);
	}

	public HashOperations removeRawCacheMapValues(String key, Object... datakey){
		return redisCacheDAO.removeRawCacheMapValues(key, datakey);
	}
	
	public void deleteRedisValue(String key)
	{
		redisCacheDAO.deleteRedisValue(key);
	}

}
