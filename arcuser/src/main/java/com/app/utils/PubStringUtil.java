package com.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共工具类。一些字符串转换，格式转换等功能可以放这里
 * 
 * @author lb
 * 
 */
public class PubStringUtil {

	public static boolean isNull(String str)
	{
		if (str == null) return true;
		if ("".equals(str)) return true;
		return false;
	}

	public static boolean isNotNull(String str)
	{
		return !isNull(str);
	}

	public static String getDateString()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String dt = df.format(date);
		return dt;
	}

	public static void main(String[] a)
	{
		System.out.println(getDateString());
	}
}
