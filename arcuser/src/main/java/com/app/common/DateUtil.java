package com.app.common;

import java.text.*;
import java.util.*;

/**
 * 提供有关日期的实用方法集
 * 
 * @create on 2010-09-21
 * @author Henry
 * 
 */
public class DateUtil {

	public static int compare_date(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("DATE1 在DATE2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("DATE1在DATE2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static boolean compare_date1(String DATE1, String DATE2) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm",
		// Locale.CHINA);
		boolean ff = true;
		try {
			// Date dt1 = df.parse(DATE1);
			// Date dt2 = df.parse(DATE2);
			//
			// boolean flag = dt1.before(dt2);
			// if (flag) {
			// System.out.print("早于今天");
			// } else {
			// System.out.print("晚于今天");
			// }

			try {
				DateFormat df = DateFormat.getDateTimeInstance();
				ff = df.parse(DATE1).before(df.parse(DATE2));
			} catch (ParseException e) {
				e.printStackTrace();
				ff = false;
			}
			// if (dt1.getTime() > dt2.getTime()) {
			// System.out.println("dt1 在dt2前");
			// return 1;
			// } else if (dt1.getTime() < dt2.getTime()) {
			// System.out.println("dt1在dt2后");
			// return -1;
			// } else {
			// return 0;
			// }
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return ff;
	}

	protected DateUtil() {
	}

	/**
	 * 返回系统当前日期
	 * 
	 * @return Date
	 */
	public static java.sql.Date getDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * 日期格式化，格式为YYYY-MM-DD
	 * 
	 * @param Date
	 * @return String
	 */
	public static String getFormatDateStr(Date d) {
		return getDate(d, "YYYY-MM-DD");
	}

	/**
	 * 将日期变量转换为"YYYY年MM月DD日"形式
	 * 
	 * @param Date
	 * @return String
	 */
	public static String getChineseDate(Date d) {
		if (d == null)
			return new String();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
				new DateFormatSymbols());

		String dtrDate = df.format(d);
		return dtrDate.substring(0, 4) + "年"
				+ Integer.parseInt(dtrDate.substring(4, 6)) + "月"
				+ Integer.parseInt(dtrDate.substring(6, 8)) + "日";
	}

	/**
	 * 获取当前日期为字符串
	 * 
	 * @return String - 当前日期字符串，格式为YYMMDD
	 */
	public static String getCurrentDate_String() {
		Calendar cal = Calendar.getInstance();

		String currentDate = null;
		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		String currentDay = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0"
					+ (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1))
					.toString();
		if (cal.get(Calendar.DAY_OF_MONTH) < 10)
			currentDay = "0"
					+ (new Integer(cal.get(Calendar.DAY_OF_MONTH))).toString();
		else
			currentDay = (new Integer(cal.get(Calendar.DAY_OF_MONTH)))
					.toString();
		currentDate = currentYear + currentMonth + currentDay;
		return currentDate;
	}

	/**
	 * 日期格式定义,按照指定格式获取当前日期为字符串
	 * 
	 * @param strFormat
	 *            String - 日期格式定义
	 * @return String 当前日期字符串
	 */
	public static String getCurrentDate_String(String strFormat) {
		Calendar cal = Calendar.getInstance();
		Date d = cal.getTime();

		return getDate(d, strFormat);
	}

	/**
	 * 比较两个日期(年月型，格式为YYYYMM)之间相差月份
	 * 
	 * @param dealMonth
	 *            - 开始年月
	 * @param alterMonth
	 *            - 结束年月
	 * @return alterMonth-dealMonth相差的月数
	 */
	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
			length = -1;

		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				length = -2;
			} else {
				int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
				int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
				int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
				int alterMonthInt = Integer
						.parseInt(alterMonth.substring(4, 6));
				length = (dealYearInt - alterYearInt) * 12
						+ (dealMonthInt - alterMonthInt);
			}
		}

		return length;
	}

	/**
	 * 得到日期中的年份
	 * 
	 * @param date
	 *            java.util.date 日期
	 * @return yyyy格式的年份
	 */
	public static int convertDateToYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy",
				new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 得到日期中年月组成的字符串
	 * 
	 * @param d
	 *            java.util.date日期
	 * @return yyyyMM格式的年月字符串
	 */
	public static String convertDateToYearMonth(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中年月日组成的字符串
	 * 
	 * @param d
	 *            java.util.date
	 * @return yyyyMMdd格式的年月日字符串
	 */
	public static String convertDateToYearMonthDay(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
				new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到两个日期之间相差的天数
	 * 
	 * @param newDate
	 *            大的日期
	 * @param oldDate
	 *            小的日期
	 * @return newDate-oldDate相差的天数
	 */
	public static int daysBetweenDates(Date newDate, Date oldDate) {
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(Calendar.DAY_OF_YEAR);
		int nyear = caln.get(Calendar.YEAR);
		int oyear = calo.get(Calendar.YEAR);
		while (nyear > oyear) {
			calo.set(Calendar.MONTH, 11);
			calo.set(Calendar.DATE, 31);
			days = days + calo.get(Calendar.DAY_OF_YEAR);
			oyear = oyear + 1;
			calo.set(Calendar.YEAR, oyear);

		}
		int nday = caln.get(Calendar.DAY_OF_YEAR);
		days = days + nday - oday;

		return days;
	}

	/**
	 * 当前日是否为周末
	 * 
	 * @param day
	 * @return
	 */
	public static boolean isWeekend(Date day) {
		boolean isWeekend = false;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(day);
		if (GregorianCalendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)
				|| GregorianCalendar.SUNDAY == calendar
						.get(Calendar.DAY_OF_WEEK)) {
			isWeekend = true;
		} else {
			isWeekend = false;
		}
		return isWeekend;
	}

	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 * 
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的天数
	 * @return date加上intBetween天后的日期
	 */
	public static Date getDateBetween(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}

	/**
	 * 按指定格式取得与原日期相差一定天数的日期，返回String型日期
	 * 
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的日期
	 * @param strFromat
	 *            返回日期的格式
	 * @return date加上intBetween天后的日期
	 */
	public static String getDateBetween_String(Date date, int intBetween,
			String strFromat) {
		Date dateOld = getDateBetween(date, intBetween);
		return getDate(dateOld, strFromat);
	}

	/**
	 * 得到将年月型字符串增加1月后的日期字符串
	 * 
	 * @param yearMonth
	 *            yyyyMM格式
	 * @return yearMonth增加一个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + 1;
		if (month <= 12 && month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month < 10)
			return yearMonth.substring(0, 4) + "0"
					+ (new Integer(month)).toString();
		else
			// if(month>12)
			return (new Integer(year + 1)).toString() + "0"
					+ (new Integer(month - 12)).toString();

	}

	/**
	 * 得到将年月型字符串增加指定月数后的日期字符串
	 * 
	 * @param yearMonth
	 *            yyyyMM格式
	 * @param addMonth
	 *            增加的月数
	 * @return yearMonth增加addMonth个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth, int addMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + addMonth;
		year = year + month / 12;
		month = month % 12;
		if (month <= 12 && month >= 10)
			return year + (new Integer(month)).toString();
		else
			return year + "0" + (new Integer(month)).toString();

	}

	/**
	 * 得到将年月型字符串减去1月后的日期字符串
	 * 
	 * @param yearMonth
	 *            - yyyyMM格式
	 * @return - yearMonth减少一个月的日期，yyyyMM格式
	 */
	public static String descreaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month - 1;
		if (month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month > 0 && month < 10)
			return yearMonth.substring(0, 4) + "0"
					+ (new Integer(month)).toString();
		else
			// if(month>12)
			return (new Integer(year - 1)).toString()
					+ (new Integer(month + 12)).toString();

	}

	/**
	 * 得到将年月型字符串减去1月后的日期字符串
	 * 
	 * @return - yearMonth减少一个月的日期，yyyy-MM-dd格式
	 */
	public static String getPriorMonthToday() {
		String yearMonthDay = getCurrentDate_String("yyyy-mm-dd");
		int year = (new Integer(yearMonthDay.substring(0, 4))).intValue();
		int month = (new Integer(yearMonthDay.substring(5, 7))).intValue();
		String day = yearMonthDay.substring(8, 10);
		month = month - 1;
		if (month >= 10)
			return year + "-" + (new Integer(month)).toString() + "-" + day;
		else if (month > 0 && month < 10)
			return year + "-" + "0" + (new Integer(month)).toString() + "-"
					+ day;
		else
			return year + "-" + (new Integer(month + 12)).toString() + "-"
					+ day;

	}

	/**
	 * 获取当前日期为日期型
	 * 
	 * @return 当前日期，java.util.Date类型
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();

		// String currentDate = null;
		Date d = cal.getTime();

		return d;
	}

	/**
	 * 获取当前年月的字符串
	 * 
	 * @return 当前年月，yyyyMM格式
	 */
	public static String getCurrentYearMonth() {
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0"
					+ (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1))
					.toString();
		return (currentYear + currentMonth);
	}

	/**
	 * 获取当前年为整型
	 * 
	 * @return 获取当前日期中的年，int型
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 * 
	 * @param strDate
	 *            - 日期
	 * @param oracleFormat
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static java.sql.Date stringToSqlDate(String strDate,
			String oracleFormat) {
		Date date = stringToDate(strDate, oracleFormat);
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 * 
	 * @param strDate
	 *            - 日期
	 * @param oracleFormat
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static Date stringToDate(String strDate, String oracleFormat) {
		if (strDate == null)
			return null;
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);

		Date myDate;
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}

		return myDate;
	}

	/**
	 * 将字符串yyyy-mm-dd转换成date格式
	 * 
	 * @author xuyt
	 * @param String
	 *            date
	 * @param
	 * @return java.sql.Date
	 */
	public static synchronized java.sql.Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ret = sdf.parse(date, new ParsePosition(0));
		return new java.sql.Date(ret.getTime());
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 * 
	 * @param d
	 *            - 日期
	 * @param format
	 *            - 指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String dateToString(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat,
				new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 * 
	 * @param d
	 *            - 日期
	 * @param format
	 *            - 指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String getDate(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat,
				new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， IF 第一个代表的时间 >
	 * 第二个代表的时间，返回真，ELSE 返回假
	 * 
	 * @param s1
	 *            年月型字符串
	 * @param s2
	 *            年月型字符串
	 * @return 如果s1大于等于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreatEqual(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			return (Integer.parseInt(temp3) >= Integer.parseInt(temp4));
		} else
			return false;
	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， IF 第一个代表的时间 >
	 * 第二个代表的时间，返回真，ELSE 返回假
	 * 
	 * @param s1
	 *            年月型字符串
	 * @param s2
	 *            年月型字符串
	 * @return 如果s1大于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreater(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			return (Integer.parseInt(temp3) > Integer.parseInt(temp4));
		} else
			return false;
	}

	/**
	 * 将日期型数据转换成Oracle要求的标准格式的字符串
	 * 
	 * @param d
	 *            java.util.Date
	 * @return 格式化后的字符串
	 */
	public static String getOracleFormatDateStr(Date d) {
		return getDate(d, "YYYY-MM-DD HH24:MI:SS");
	}

	/**
	 * 字串6位，前4代表年，后2代表月， 返回给定日期中的月份中的最后一天 param term(YYYYMMDD)
	 * 
	 * @param term
	 *            - 年月，格式为yyyyMM
	 * @return String 指定年月中该月份的天数
	 */
	public static String getLastDay(String term) {

		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));

		String getLastDay = "";

		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0) {
				getLastDay = "29";
			} else {
				getLastDay = "28";
			}
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9
				|| getMonth == 11) {
			getLastDay = "30";
		} else {
			getLastDay = "31";
		}
		return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月"
				+ getLastDay + "日";
	}

	/**
	 * 返回两个年月(例如：201010)之间相差的月数，年月格式为yyyyMM
	 * 
	 * @param strDateBegin
	 *            - String
	 * @param strDateEnd
	 *            String
	 * @return String strDateEnd-strDateBegin相差的月数
	 */
	public static String getMonthBetween(String strDateBegin, String strDateEnd) {
		try {
			int intMonthBegin;
			int intMonthEnd;
			String strOut;
			if (strDateBegin.equals("") || strDateEnd.equals("")
					|| strDateBegin.length() != 6 || strDateEnd.length() != 6)
				strOut = "";
			else {
				intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4))
						* 12 + Integer.parseInt(strDateBegin.substring(4, 6));
				intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12
						+ Integer.parseInt(strDateEnd.substring(4, 6));
				strOut = String.valueOf(intMonthBegin - intMonthEnd);
			}
			return strOut;
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 将yyyyMMDD格式的日期转换为yyyy-MM-DD格式的日期 返回带'-'的日期(例如：20101009 转换为 2010-10-09)
	 * 
	 * @param strDate
	 *            String yyyyMMDD格式的日期
	 * @return String - yyyy-MM-DD格式的日期
	 */
	public static String getStrHaveAcross(String strDate) {
		try {
			return strDate.substring(0, 4) + "-" + strDate.substring(4, 6)
					+ "-" + strDate.substring(6, 8);
		} catch (Exception e) {
			return strDate;
		}
	}

	/**
	 * 取得当前日期的下一个月的第一天
	 * 
	 * @return 当前日期的下个月的第一天，格式为yyyyMMDD
	 */
	public static String getFirstDayOfNextMonth() {
		String strToday = getCurrentDate_String();
		return increaseYearMonth(strToday.substring(0, 6)) + "01";
	}

	/**
	 * 将yyyyMM各式转换成yyyy年MM月格式
	 * 
	 * @param yearMonth
	 *            年月类型的字符串
	 * @return yyyy年MM月格式的日期
	 */
	public static String getYearAndMonth(String yearMonth) {
		if (null == yearMonth)
			return "";
		String ym = yearMonth.trim();
		if (6 != ym.length())
			return ym;
		String year = ym.substring(0, 4);
		String month = ym.substring(4);
		return new StringBuffer(year).append("年").append(month).append("月")
				.toString();
	}

	/**
	 * 将输入的月数转化成"X年X月"格式的字符串
	 * 
	 * @param month
	 * @return String
	 */
	public static String month2YearMonth(String month) {
		String yearMonth = "";
		int smonth = 0;
		int year = 0;
		int rmonth = 0;

		if ((null == month) || ("0".equals(month)) || "".equals(month.trim())) {
			return "0月";
		}

		smonth = Integer.parseInt(month);
		year = smonth / 12;
		rmonth = smonth % 12;

		if (year > 0) {
			yearMonth = year + "年";
		}
		if (rmonth > 0) {
			yearMonth += rmonth + "个月";
		}

		return yearMonth;
	}

	/**
	 * 将yyyyMM各式转换成yyyy年MM月格式
	 * 
	 * @param month
	 *            月
	 * @return 返回年月型格式的日期
	 */
	public static String getYearMonthByMonth(String month) {
		if (null == month)
			return null;
		String ym = month.trim();
		if (6 != ym.length())
			return ym;
		String year = ym.substring(0, 4);
		String month1 = ym.substring(4, ym.length());
		return new StringBuffer(year).append("年").append(month1).append("月")
				.toString();
	}

	/**
	 * 得到将date增加指定月数后的date
	 * 
	 * @param date
	 *            java.util.Date
	 * @param intBetween
	 *            指定的月差距
	 * @return date加上intBetween月数后的日期
	 */
	public static Date increaseMonth(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.MONTH, intBetween);
		return calo.getTime();
	}

	/**
	 * 得到将date增加指定年数后的date
	 * 
	 * @param date
	 *            java.util.Date
	 * @param intBetween
	 *            指定的年差距
	 * @return date加上intBetween年数后的日期
	 */
	public static Date increaseYear(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.YEAR, intBetween);
		return calo.getTime();
	}

	/**
	 * 返回整数秒转换为字符串【00:00:00】
	 * 
	 * @return String
	 */
	public static String getTimeInt2Str(int iTime) {
		String str = "";
		try {
			if (iTime < 0) {
				iTime = 0;
			}
			int iH = iTime / 3600;// 总小时数
			int iM = iTime / 60;// 总秒数
			iM = iM % 60;// 分数
			int iS = iTime % 60;// 秒数
			if (iH < 10) {
				str = "0" + iH;
			} else {
				str = "" + iH;
			}
			if (iM < 10) {
				str += ":0" + iM;
			} else {
				str += ":" + iM;
			}
			if (iS < 10) {
				str += ":0" + iS;
			} else {
				str += ":" + iS;
			}
		} catch (Throwable ee) {
			ee.printStackTrace();
			str = "" + iTime;
		}
		return str;
	}

	/**
	 * 返回字符串【00:00:00】转换为整数秒
	 * 
	 * @return String
	 */
	public static int getTimeStr2Int(String str) {
		int iTotal = 0; // 秒
		try {
			int iIndex1 = str.indexOf(":");
			int iIndex2 = str.indexOf(":", iIndex1 + 1);
			if (iIndex2 <= 0) {
				return 0;
			}
			int iH = Integer.parseInt(str.substring(0, iIndex1));
			int iM = Integer.parseInt(str.substring(iIndex1 + 1, iIndex2));
			int iS = Integer.parseInt(str.substring(iIndex2 + 1));
			iTotal = (iH * 60 + iM) * 60 + iS;
		} catch (Throwable ee) {
			ee.printStackTrace();
		}
		return iTotal;
	}

	/**
	 * 得到两个日期的时间差
	 * 
	 * @param date1
	 *            yyyy-MM-dd
	 * @param date2
	 *            yyyy-MM-dd
	 * @return
	 */
	public static Long compareDate(String date1, String date2) {

		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;

		Date mydate = null;

		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (ParseException e) {

			e.printStackTrace();

			return null;
		}

		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);

		return new Long(day);
	}

	public static long calSecondsBetweenDate(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date2 += ":00";
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() < dt2.getTime()) {
				return (dt2.getTime() - dt1.getTime()) / 1000;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 方法使用说明
	 * 
	 * @param String
	 *            []
	 */
	public static void main(String[] args) {
		System.out.println(new Date()); // 当前日期时间 Sat Oct 09 13:21:56 CST 2010
		System.out.println(getDate()); // 返回系统当前日期(返回:2010-10-09)
		System.out.println(DateUtil.getFormatDateStr(new Date())); // 日期格式化，格式为YYYY-MM-DD(返回:2010-10-09)
		System.out.println(DateUtil.getChineseDate(new Date())); // 将日期变量转换为"YYYY年MM月DD日"形式(返回:2010年10月9日)
		System.out.println(DateUtil.getCurrentDate_String()); // 获取当前日期为字符串(返回:20101009)
		System.out.println(DateUtil
				.getCurrentDate_String("yyyy-mm-dd hh24:mi:ss")); // 日期格式定义,按照指定格式获取当前日期为字符串(返回:2010-10-09
																	// 14:39:12)
		System.out.println(DateUtil.calBetweenTwoMonth("201008", "201010")); // 比较两个日期(年月型，格式为YYYYMM)之间相差月份(返回:-2)
		System.out.println(DateUtil.convertDateToYear(new Date())); // 得到日期中的年份(返回:2010)
		System.out.println(DateUtil.convertDateToYearMonth(new Date())); // 得到日期中年月组成的字符串(返回:201010)
		System.out.println(DateUtil.convertDateToYearMonthDay(new Date())); // 得到日期中年月日组成的字符串(返回:20101009)
		System.out.println(DateUtil.daysBetweenDates(new Date(), new Date())); // 得到两个日期之间相差的天数(返回:0)
		System.out.println(DateUtil.isWeekend(new Date())); // 当前日是否为周末(返回:true)
		System.out.println(DateUtil.getDateBetween(new Date(), 1)); // 取得与原日期相差一定天数的日期，返回Date型日期(返回:Sun
																	// Oct 10
																	// 13:29:49
																	// CST 2010)
		System.out.println(DateUtil.getDateBetween_String(new Date(), 10,
				"YYYY-MM-DD")); // 按指定格式取得与原日期相差一定天数的日期(返回:2010-10-19)
		System.out.println(DateUtil.increaseYearMonth("201010")); // 得到将年月型字符串增加1月后的日期字符串(返回:201011)
		System.out.println(DateUtil.increaseYearMonth("201010", 6)); // 得到将年月型字符串增加指定月数后的日期字符串(返回:201104)
		System.out.println(DateUtil.descreaseYearMonth("201010")); // 得到将年月型字符串减去1月后的日期字符串(返回:201009)
		System.out.println(DateUtil.getPriorMonthToday()); // 得到将年月型字符串减去1月后的日期字符串(返回:2010-09-09)
		System.out.println(DateUtil.getCurrentDate()); // 获取当前日期为日期型(返回:Sat Oct
														// 09 13:41:41 CST 2010)
		System.out.println(DateUtil.getCurrentYearMonth()); // 获取当前年月的字符串(返回:201010)
		System.out.println(DateUtil.getCurrentYear()); // 获取当前年为整型(返回:2010)
		System.out.println(DateUtil.stringToSqlDate("2010-10-09 13:48:20",
				"yyyy-mm-dd hh24:mi:ss")); // 将指定格式的字符串转换为日期型(返回:2010-10-09)
		System.out.println(DateUtil.stringToDate("2010-10-09 13:48:20",
				"yyyy-mm-dd hh24:mi:ss")); // 将指定格式的字符串转换为日期型(返回:Sat
											// Oct 09 13:48:20
											// CST 2010)
		System.out.println(DateUtil.stringToDate("2010-10-09")); // 将字符串yyyy-mm-dd转换成date格式(返回:2010-10-09)
		System.out.println(DateUtil.dateToString(new Date(), "yyyy/mm/dd")); // 获取输入格式的日期字符串，字符串遵循Oracle格式(返回:2010/10/09)
		System.out.println(DateUtil.getDate(new Date(), "yyyy/mm/dd")); // 获取输入格式的日期字符串，字符串遵循Oracle格式(返回:2010/10/09)
		System.out.println(DateUtil.yearMonthGreatEqual("201010", "201012")); // 比较两个年月型日期的大小，日期格式为yyyyMM
																				// 两个字串6位，前4代表年，后2代表月(返回:false)
		System.out.println(DateUtil.yearMonthGreater("201010", "201012")); // 比较两个年月型日期的大小，日期格式为yyyyMM
																			// 两个字串6位，前4代表年，后2代表月(返回:false)
		System.out.println(DateUtil.getOracleFormatDateStr(new Date())); // 将日期型数据转换成Oracle要求的标准格式的字符串(返回:2010-10-09
																			// 13:58:59)
		System.out.println(DateUtil.getLastDay("20101009")); // 字串6位，前4代表年，后2代表月，
																// 返回给定日期中的月份中的最后一天(返回:2010年10月31日)
		System.out.println(DateUtil.getMonthBetween("201008", "201010")); // 返回两个年月(例如：201010)之间相差的月数，年月格式为yyyyMM(返回:-2)
		System.out.println(DateUtil.getStrHaveAcross("20101009")); // 将yyyyMMDD格式的日期转换为yyyy-MM-DD格式的日期
																	// 返回带'-'的日期(返回:2010-10-09)
		System.out.println(DateUtil.getFirstDayOfNextMonth()); // 取得当前日期的下一个月的第一天(返回:20101101)
		System.out.println(DateUtil.getYearAndMonth("201010")); // 将yyyyMM各式转换成yyyy年MM月格式(返回:2010年10月)
		System.out.println(DateUtil.month2YearMonth("20")); // 将输入的月数转化成"X年X月"格式的字符串(返回:1年8个月,即20个月)
		System.out.println(DateUtil.getYearMonthByMonth("201010")); // 将yyyyMM各式转换成yyyy年MM月格式(返回:2010年10月)
		System.out.println(DateUtil.getFormatDateStr(DateUtil.increaseMonth(
				new Date(), 2))); // 得到将date增加指定月数后的date(返回:Thu
									// Dec 09 14:15:01 CST
									// 2010)
		System.out.println(DateUtil.getFormatDateStr(DateUtil.increaseYear(
				new Date(), 2))); // 得到将date增加指定年数后的date(返回:Tue
									// Oct 09 14:18:55 CST
									// 2012)
		System.out.println(DateUtil.getTimeInt2Str(3600)); // 返回整数秒转换为字符串(返回:01:00:00，3600秒即1小时)
		System.out.println(DateUtil.getTimeStr2Int("01:00:00")); // 返回整数秒转换为字符串(返回:3600秒)
		System.out.println(DateUtil.compareDate("2010-10-09", "2010-10-12")); // 得到两个日期的时间差(返回:-3)
	}
}
