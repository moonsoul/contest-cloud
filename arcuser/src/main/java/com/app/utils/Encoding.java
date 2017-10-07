package com.app.utils;

import java.security.Security;

/**
 * MD5加密算法(不可逆的加密过程)
 * 
 * @author Lian
 * @create on 2010-09-27
 */
public class Encoding {

	private static boolean	debug	= false;

	/**
	 * 算法提供商
	 */
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * 构造方法
	 */
	public Encoding() {
	}

	// md5()信息摘要, 不可逆
	private static String md5(byte[] input) throws Exception
	{

		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5");

		if (debug) {
			System.out.println("MD5摘要前的二进串:" + byte2hex(input));
			System.out.println("MD5摘要前的字符串:" + new String(input));
		}
		alg.update(input);
		byte[] digest = alg.digest();
		if (debug) {
			System.out.println("MD5摘要后的二进串:" + byte2hex(digest));
		}

		return byte2string(digest);
	}

	/**
	 * 字节码转换成16进制字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b)
	{

		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0");
				hs.append(stmp);
			}
			else {
				hs.append(stmp);
			}

			if (n < b.length - 1) {
				hs.append(":");
			}
		}

		return hs.toString().toUpperCase();
	}

	/**
	 * 字节码转换成自定义字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2string(byte[] b)
	{
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0");
				hs.append(stmp);
			}
			else {
				hs.append(stmp);
			}

			// if (n<b.length-1) hs=hs+":";
		}

		return hs.toString().toUpperCase();
	}

	/**
	 * 加密接口
	 * 
	 * @param text
	 * @return
	 */
	public static String encodeCmd(String text)
	{
		return encodeCmd(text, false);
	}

	/**
	 * MD5加密的接口，不可逆
	 * 
	 * @param text
	 * @param newDebug
	 * @return
	 */
	public static String encodeCmd(String text, boolean newDebug)
	{

		String encodedText = "";

		try {
			encodedText = md5(text.getBytes());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("加密Encoding.java:" + e.toString());
		}

		return encodedText;
	}

	// public static void main(String arg[]) {
	// Encoding encod = new Encoding();
	// try {
	// System.out.println(encod.encodeCmd("111111"));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
