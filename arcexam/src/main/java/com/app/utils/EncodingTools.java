/**
 * 
 */
package com.app.utils;

import java.io.UnsupportedEncodingException;

/**
 * 
 * Title:字符编码工具类
 * 
 * @author: Henry
 * @version 1.0
 */
public class EncodingTools {

	/**
	 * 转换编码 ISO-8859-1到GB2312
	 * 
	 * @param text
	 * @return
	 */
	public String toEncoding(String text, String coding, String toCoding)
	{
		String result = "";
		try {
			result = new String(text.getBytes(coding), toCoding);
		}
		catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 ISO-8859-1到GB2312
	 * 
	 * @param text
	 * @return
	 */
	public static String ISOtoGB2(String text)
	{
		return new EncodingTools().toEncoding(text, "ISO-8859-1", "GB2312");
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 * 
	 * @param text
	 * @return
	 */
	public static String GB2toISO(String text)
	{
		return new EncodingTools().toEncoding(text, "GB2312", "ISO-8859-1");
	}

	/**
	 * 转换编码 ISO-8859-1到Utf8
	 * 
	 * @param text
	 * @return
	 */
	public static String ISOtoUTF(String text)
	{
		return new EncodingTools().toEncoding(text, "ISO-8859-1", "UTF-8");
	}

	/**
	 * 转换编码 Utf8到ISO-8859-1
	 * 
	 * @param text
	 * @return
	 */
	public static String UTFtoISO(String text)
	{
		return new EncodingTools().toEncoding(text, "UTF-8", "ISO-8859-1");
	}

	/**
	 * Utf8URL编码
	 * 
	 * @param s
	 * @return
	 */
	public static String utf8URLencode(String text)
	{

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			}
			else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				}
				catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return result.toString();
	}

	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	public static String utf8URLdecode(String text)
	{

		String result = "";

		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1) return text;
			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text.trim().equals("") || text.length() < 9) {
					return result;
				}

				result = result + CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	/**
	 * Utf8URL编码转字符
	 * 
	 * @param text
	 * @return
	 */
	private static String CodeToWord(String text)
	{
		String result;
		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			}
			catch (UnsupportedEncodingException ex) {
				result = null;
			}
		}
		else {
			result = text;
		}
		return result;
	}

	/**
	 * 编码是否有效 *
	 * 
	 * @param text
	 *            *
	 * @return
	 */
	private static boolean Utf8codeCheck(String text)
	{

		StringBuffer sign = new StringBuffer();

		if (text.startsWith("%e")) {
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1) {
					p++;
				}

				sign.append(p);
			}
		}

		return sign.toString().equals("147-1");
	}

	/**
	 * 是否Utf8Url编码
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isUtf8Url(String text)
	{
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}

}
