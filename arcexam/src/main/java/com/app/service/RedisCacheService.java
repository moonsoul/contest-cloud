package com.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("redisCacheService")
public class RedisCacheService <T> {
	private final static Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

	@Autowired
	private RedisTemplate redisTemplate;

	public <T> ValueOperations<String,T> setCacheObject(String key,T value)
	{
		ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
		operation.set(key,value);
		return operation;
	}

	public <T> ValueOperations<String,T> setCacheObject(String key,T value, long timeout)
	{
		ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
		operation.set(key,value, timeout,TimeUnit.SECONDS);
		return operation;
	}
	
	public <T> T getCacheObject(String key/*,ValueOperations<String,T> operation*/)
	{
		ValueOperations<String,T> operation = redisTemplate.opsForValue(); 
		return operation.get(key);
	}

	public <T> ListOperations<String, T> setCacheList(String key,List<T> dataList)
	{
		ListOperations listOperation = redisTemplate.opsForList();
		if(null != dataList)
		{
			int size = dataList.size();
			for(int i = 0; i < size ; i ++)
			{
				listOperation.rightPush(key,dataList.get(i));
			}
		}
		return listOperation;
	}

	public <T> List<T> getCacheList(String key)
	{
		List<T> dataList = new ArrayList<T>();
		ListOperations<String,T> listOperation = redisTemplate.opsForList();
		Long size = listOperation.size(key);

		for(int i = 0 ; i < size ; i ++)
		{
			dataList.add((T) listOperation.leftPop(key));
		}

		return dataList;
	}

	public <T> BoundSetOperations<String,T> setCacheSet(String key,Set<T> dataSet)
	{
		BoundSetOperations<String,T> setOperation = redisTemplate.boundSetOps(key); 
		/*T[] t = (T[]) dataSet.toArray();
		setOperation.add(t);*/

		Iterator<T> it = dataSet.iterator();
		while(it.hasNext())
		{
			setOperation.add(it.next());
		}

		return setOperation;
	}

	public Set<T> getCacheSet(String key/*,BoundSetOperations<String,T> operation*/)
	{
		Set<T> dataSet = new HashSet<T>();
		BoundSetOperations<String,T> operation = redisTemplate.boundSetOps(key); 

		Long size = operation.size();
		for(int i = 0 ; i < size ; i++)
		{
			dataSet.add(operation.pop());
		}
		return dataSet;
	}

	public <T> HashOperations<String,String,T> setCacheMap(String key, Map<String,T> dataMap, long timeout)
	{
		HashOperations hashOperations = redisTemplate.opsForHash();
		if(null != dataMap)
		{
			for (Map.Entry<String, T> entry : dataMap.entrySet()) { 
				/*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); */
				hashOperations.put(key,entry.getKey(),entry.getValue());
			} 
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}

		return hashOperations;
	}
	
	public <T> HashOperations<String,String,T> setCacheMap(String key,Map<String,T> dataMap)
	{
		return setCacheMap(key, dataMap, 600);
	}

	public <T> Map<String,T> getCacheMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	{
		Map<String, T> map = redisTemplate.opsForHash().entries(key);
		/*Map<String, T> map = hashOperation.entries(key);*/
		return map;
	}

	public <T> HashOperations<String,Integer,T> setCacheIntegerMap(String key,Map<Integer,T> dataMap)
	{
		HashOperations hashOperations = redisTemplate.opsForHash();
		if(null != dataMap)
		{
			for (Map.Entry<Integer, T> entry : dataMap.entrySet()) { 
				/*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); */
				hashOperations.put(key,entry.getKey(),entry.getValue());
			} 
		}

		return hashOperations;
	}

	public <T> Map<Integer,T> getCacheIntegerMap(String key/*,HashOperations<String,String,T> hashOperation*/)
	{
		Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
		/*Map<String, T> map = hashOperation.entries(key);*/
		return map;
	}
	
	public HashOperations setRawCacheMap(String key,Map dataMap, long timeout){
		HashOperations hashOperations = redisTemplate.opsForHash();
		if(null != dataMap)
		{
			for (Object obj : dataMap.entrySet()) { 
				Map.Entry entry = (Map.Entry)obj;
				hashOperations.put(key,entry.getKey(),entry.getValue());
			}
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return hashOperations;
	}
	
	public HashOperations setRawCacheMap(String key,Map dataMap){
		return setRawCacheMap(key, dataMap, 600);
	}
	
	public Map getRawCacheMap(String key){
		Map map = redisTemplate.opsForHash().entries(key);
		return map;
	}
	
	public HashOperations setRawCacheMapValue(String key, Object datakey, Object value){
		HashOperations hashOperations = redisTemplate.opsForHash();
		hashOperations.put(key,datakey,value);
		return hashOperations;
	}

	public Object getRawCacheMapValue(String key, Object datakey){
		HashOperations hashOperations = redisTemplate.opsForHash();
		return hashOperations.get(key, datakey);
	}

	public HashOperations removeRawCacheMapValues(String key, Object... datakey){
		HashOperations hashOperations = redisTemplate.opsForHash();
		hashOperations.delete(key,datakey);
		return hashOperations;
	}
	
	public void deleteRedisValue(String key){
		redisTemplate.delete(key);
	}
	
	public void expireRedisSession(String sessionid){
		deleteRedisValue("spring:session:sessions:expires:" + sessionid);
	}
	
	public void removeRedisSession(String sessionid){
		deleteRedisValue("spring:session:sessions:" + sessionid);
	}
	
	public Map getRedisSessionMap(String sessionid){
		Map map = null;
		boolean haskey = redisTemplate.hasKey("spring:session:sessions:expires:" + sessionid);
		if(haskey){
			map = getRawCacheMap("spring:session:sessions:" + sessionid);
		}else{
			//removeRedisSession(sessionid);
		}
		
		return map;
	}
}
