/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.common.utils;


import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Set;

/**
 * Cache工具类
 * @author jeeplus
 * @version 2017-1-19
 */
public class CacheUtils {
	

//	private static final String SYS_REGION = "sysCache";
//
//
//
//	/**
//	 * 获取SYS_CACHE缓存
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String key) {
//		return get(SYS_REGION, key);
//	}
//
//	/**
//	 * 写入SYS_CACHE缓存
//	 * @param key
//	 * @return
//	 */
//	public static void put(String key, Object value) {
//		put(SYS_REGION, key, value);
//	}
//
//	/**
//	 * 从SYS_CACHE缓存中移除
//	 * @param key
//	 * @return
//	 */
//	public static void remove(String key) {
//		remove(SYS_REGION, key);
//	}
//
//	/**
//	 * 获取缓存
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String region, String key) {
//		CacheObject object = J2Cache.getChannel().get(region ,key);
//		return object==null?null:object.getValue();
//	}
//
//
//
//
//	/**
//	 * 写入缓存
//	 * @param region
//	 * @param key
//	 * @param value
//	 */
//	public static void put(String region, String key, Object value) {
//		J2Cache.getChannel().set(region, key, value);
//
//	}
//
//	/**
//	 * 从缓存中移除
//	 * @param region
//	 * @param key
//	 */
//	public static void remove(String region, String key) {
//		J2Cache.getChannel().evict(region, key);
//	}
private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

	private static final String SYS_CACHE = "sysCache";

	/**
	 * 获取SYS_CACHE缓存
	 *
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	/**
	 * 获取SYS_CACHE缓存
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入SYS_CACHE缓存
	 *
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	 * 从SYS_CACHE缓存中移除
	 *
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		if( getCache(cacheName).get(key) == null){
			return null;
		}else {
			return getCache(cacheName).get(key).get();
		}
	}

	/**
	 * 获取缓存
	 *
	 * @param cacheName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入缓存
	 *
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(key, value);
	}

	/**
	 * 从缓存中移除
	 *
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}


	/**
	 * 获得一个Cache，没有则显示日志。
	 *
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
		}
		return cache;
	}





}