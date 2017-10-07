package com.app.common;

import com.app.entity.Tbuser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 封装各种生成唯一性ID算法的工具类.
 * </p>
 * 
 * @author Zhao YuYang 2016年3月8日 下午1:16:07
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 
	 * @param str
	 *            源字符
	 * @return MD5 值
	 */
	public static String getMD5(String str) {
		Md5Hash md5 = new Md5Hash(str);
		return md5.toBase64();
	}

	/**
	 * 功能：判断字符串是否为数字或字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericOrAlphabet(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
				continue;
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9') {
				continue;
			} else {
				return false;
			}
		}

		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(Identities.uuid());
		System.out.println(Identities.uuid2());
		System.out.println(Identities.randomLong());
		System.out.println(Identities.getMD5("10151001"));
		System.out.println(Identities.getMD5("2222"));
		System.out.println(Identities.getMD5("tea"));
		Tbuser tu = new Tbuser();
		tu.setUserid("id11");
		Object[] o = tu.entityInsertSqlParm();
		String sql = o[0].toString();
		Object[] p = (Object[]) o[1];
		
		String servertime = DateUtil.getOracleFormatDateStr(new Date());
		
		int cankao1 = DateUtil.compare_date("2016-03-25 17:40", "2016-03-25 17:40");
		boolean cankao = DateUtil.compare_date("2016-03-25 17:40", servertime) >0;
		System.out.println(cankao+""+cankao1);
		
		String name = "1.1.xls";
		System.out.println(StringUtils.substringAfterLast(name, "."));
	}

}
