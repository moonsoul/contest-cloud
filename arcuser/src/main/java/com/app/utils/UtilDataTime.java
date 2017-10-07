package com.app.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilDataTime {

	public static String getFormatDate(Date date)
	{
		// yyyy-mm-dd HH24:MI:SS
		return new String(toDateString(date) + toTimeString(date));
	}

	public static Timestamp nowTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date nowDate()
	{
		return new Date();
	}

	/**
	 * 通过java.util.date获取星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		String weekDay = "";
		switch (calendar.get(calendar.DAY_OF_WEEK))
			{
			case 1:
				weekDay = "星期日";
				break;
			case 2:
				weekDay = "星期一";
				break;
			case 3:
				weekDay = "星期二";
				break;
			case 4:
				weekDay = "星期三";
				break;
			case 5:
				weekDay = "星期四";
				break;
			case 6:
				weekDay = "星期五";
				break;
			case 7:
				weekDay = "星期六";
				break;
			}
		return weekDay;
	}

	public static String getDateString(Date date)
	{
		String sDateTime = "";
		sDateTime = toDateString(date);
		return sDateTime;
	}

	public static Timestamp getDayStart(Timestamp stamp)
	{
		return getDayStart(stamp, 0);
	}

	public static Timestamp getDayStart(Timestamp stamp, int daysLater)
	{
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(stamp.getTime()));
		tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 0, 0, 0);
		tempCal.add(5, daysLater);
		return new Timestamp(tempCal.getTime().getTime());
	}

	public static Timestamp getNextDayStart(Timestamp stamp)
	{
		return getDayStart(stamp, 1);
	}

	public static Timestamp getDayEnd(Timestamp stamp)
	{
		return getDayEnd(stamp, 0);
	}

	public static Timestamp getDayEnd(Timestamp stamp, int daysLater)
	{
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new Date(stamp.getTime()));
		tempCal.set(tempCal.get(1), tempCal.get(2), tempCal.get(5), 23, 59, 59);
		tempCal.add(5, daysLater);
		return new Timestamp(tempCal.getTime().getTime());
	}

	public static java.sql.Date toSqlDate(String date)
	{
		Date newDate = toDate(date, "00:00:00");
		if (newDate != null) {
			return new java.sql.Date(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static java.sql.Date toSqlDate(String monthStr, String dayStr,
			String yearStr)
	{
		Date newDate = toDate(monthStr, dayStr, yearStr, "0", "0", "0");
		if (newDate != null) {
			return new java.sql.Date(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static java.sql.Date toSqlDate(int month, int day, int year)
	{
		Date newDate = toDate(month, day, year, 0, 0, 0);
		if (newDate != null) {
			return new java.sql.Date(newDate.getTime());
		}
		else {
			return null;
		}
	}

	/**
	 * xmh,增加了从java.util.date到java.sql.date的转换
	 */
	public static java.sql.Date toSqlDate(Date utildate)
	{
		if (utildate == null) {
			return null;
		}
		Calendar cale = new java.util.GregorianCalendar();
		cale.setTime(utildate);
		return new java.sql.Date(cale.getTimeInMillis());
	}

	public static Time toSqlTime(String time)
	{
		Date newDate = toDate("1/1/1970", time);
		if (newDate != null) {
			return new Time(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Time toSqlTime(String hourStr, String minuteStr,
			String secondStr)
	{
		Date newDate = toDate("0", "0", "0", hourStr, minuteStr, secondStr);
		if (newDate != null) {
			return new Time(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Time toSqlTime(int hour, int minute, int second)
	{
		Date newDate = toDate(0, 0, 0, hour, minute, second);
		if (newDate != null) {
			return new Time(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Timestamp toTimestamp(String dateTime)
	{
		Date newDate = toDate(dateTime);
		if (newDate != null) {
			return new Timestamp(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Timestamp toTimestamp(String date, String time)
	{
		Date newDate = toDate(date, time);
		if (newDate != null) {
			return new Timestamp(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Timestamp toTimestamp(String monthStr, String dayStr,
			String yearStr, String hourStr, String minuteStr, String secondStr)
	{
		Date newDate = toDate(monthStr, dayStr, yearStr, hourStr, minuteStr,
				secondStr);
		if (newDate != null) {
			return new Timestamp(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Timestamp toTimestamp(int month, int day, int year, int hour,
			int minute, int second)
	{
		Date newDate = toDate(month, day, year, hour, minute, second);
		if (newDate != null) {
			return new Timestamp(newDate.getTime());
		}
		else {
			return null;
		}
	}

	public static Date toDate(String dateTime)
	{
		String date = dateTime.substring(0, dateTime.indexOf(" "));
		String time = dateTime.substring(dateTime.indexOf(" ") + 1);
		return toDate(date, time);
	}

	public static Date toDate(String date, String time)
	{
		if (date == null || time == null) {
			return null;
		}
		int dateSlash1 = date.indexOf("/");
		int dateSlash2 = date.lastIndexOf("/");
		if (dateSlash1 <= 0 || dateSlash1 == dateSlash2) {
			return null;
		}
		int timeColon1 = time.indexOf(":");
		int timeColon2 = time.lastIndexOf(":");
		if (timeColon1 <= 0) {
			return null;
		}
		String month = date.substring(0, dateSlash1);
		String day = date.substring(dateSlash1 + 1, dateSlash2);
		String year = date.substring(dateSlash2 + 1);
		String hour = time.substring(0, timeColon1);
		String minute;
		String second;
		if (timeColon1 == timeColon2) {
			minute = time.substring(timeColon1 + 1);
			second = "0";
		}
		else {
			minute = time.substring(timeColon1 + 1, timeColon2);
			second = time.substring(timeColon2 + 1);
		}
		return toDate(month, day, year, hour, minute, second);
	}

	public static Date toDate(String monthStr, String dayStr, String yearStr,
			String hourStr, String minuteStr, String secondStr)
	{
		int month;
		int day;
		int year;
		int hour;
		int minute;
		int second;
		try {
			month = Integer.parseInt(monthStr);
			day = Integer.parseInt(dayStr);
			year = Integer.parseInt(yearStr);
			hour = Integer.parseInt(hourStr);
			minute = Integer.parseInt(minuteStr);
			second = Integer.parseInt(secondStr);
		}
		catch (Exception e) {
			return null;
		}
		return toDate(month, day, year, hour, minute, second);
	}

	public static Date toDate(int month, int day, int year, int hour,
			int minute, int second)
	{
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.set(year, month - 1, day, hour, minute, second);
		}
		catch (Exception e) {
			return null;
		}
		return new Date(calendar.getTime().getTime());
	}

	public static String toDateString(Date date)
	{
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2) + 1;
		int day = calendar.get(5);
		int year = calendar.get(1);
		String monthStr;
		if (month < 10) {
			monthStr = "0" + month;
		}
		else {
			monthStr = "" + month;
		}
		String dayStr;
		if (day < 10) {
			dayStr = "0" + day;
		}
		else {
			dayStr = "" + day;
		}
		String yearStr = "" + year;
		return yearStr + monthStr + dayStr;
	}

	public static String toTimeString(Date date)
	{
		if (date == null) {
			return "";
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return toTimeString(calendar.get(11), calendar.get(12),
					calendar.get(13));
		}
	}

	public static String toTimeString(int hour, int minute, int second)
	{
		String hourStr;
		if (hour < 10) {
			hourStr = "0" + hour;
		}
		else {
			hourStr = "" + hour;
		}
		String minuteStr;
		if (minute < 10) {
			minuteStr = "0" + minute;
		}
		else {
			minuteStr = "" + minute;
		}
		String secondStr;
		if (second < 10) {
			secondStr = "0" + second;
		}
		else {
			secondStr = "" + second;
		}
		if (second == 0) {
			return hourStr + ":" + minuteStr;
		}
		else {
			return hourStr + minuteStr + secondStr;
		}
	}

	public static String toDateTimeString(Date date)
	{
		if (date == null) {
			return "";
		}
		String dateString = toDateString(date);
		String timeString = toTimeString(date);
		if (dateString != null && timeString != null) {
			return dateString + " " + timeString;
		}
		else {
			return "";
		}
	}

	public static Timestamp monthBegin()
	{
		Calendar mth = Calendar.getInstance();
		mth.set(5, 1);
		mth.set(11, 0);
		mth.set(12, 0);
		mth.set(13, 0);
		mth.set(9, 0);
		return new Timestamp(mth.getTime().getTime());
	}

	/**
	 * 通过传递一个java.util.Date类型,得到年
	 */
	public static String getDateYear(Date udate)
	{
		String strdate = UtilDataTime.toDateString(udate);
		// DateFormat df = new DateFormat(strdate);
		// String year = df.get(DateFormat.YEAR) + "";
		String year = strdate.substring(6);
		return year.trim();
	}

	/**
	 * 通过传递一个java.util.Date类型,得到月
	 */
	public static String getDateMonth(Date udate)
	{
		String strdate = UtilDataTime.toDateString(udate);
		// DateFormat df = new DateFormat(strdate);
		// String month = df.get(DateFormat.MONTH) + 1 + "";
		String month = strdate.substring(0, 2);
		return month.trim();
	}

	/**
	 * 通过传递一个java.util.Date类型,得到日
	 */
	public static String getDateDay(Date udate)
	{
		String strdate = UtilDataTime.toDateString(udate);
		// DateFormat df = new DateFormat(strdate);
		// String day = df.get(DateFormat.DAY) + "";
		String day = strdate.substring(3, 5);
		return day.trim();

	}

	/**
	 * 获得数据库月份，用于判断插入的分区表
	 * 
	 * @param datetime
	 * @return
	 */
	public static String getMonth(String sysdate)
	{
		return sysdate.substring(5, 7).trim();
	}

	/**
	 * 获得数据库日期，用于判断插入的分区ID字段
	 * 
	 * @param datetime
	 * @return
	 */
	public static String getDay(String sysdate)
	{
		String day = "1";
		if (sysdate.substring(8, 9).equals("0")) {
			day = sysdate.substring(9, 10);
		}
		else {
			day = sysdate.substring(8, 10);
		}
		return day.trim();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar ca = Calendar.getInstance();
		return sdf.format(ca.getTime());
	}

	/**
	 * 判断时间是否为"yyyy-MM-dd"这种格式,并且交验时间是否正确
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkTime(String time)
	{
		boolean isValid = false;
		if (time != null && time.length() == 10) {// �ж�λ��
			String DatePattern = "^(?:([0-9]{4}-(?:(?:0?[1,3-9]|1[0-2])-(?:29|30)|"
					+ "((?:0?[13578]|1[02])-31)))|"
					+ "([0-9]{4}-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|"
					+ "(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|"
					+ "(?:0[48]00|[2468][048]00|[13579][26]00))-0?2-29)))$";

			Pattern p = Pattern.compile(DatePattern);
			Matcher m = p.matcher(time);
			isValid = m.matches();
		}
		return isValid;
	}

	/**
	 * 对时间进行计算，返回Date类型
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param date
	 *            要计算的时间
	 * @return
	 */
	public static Date coutDate(int year, int month, int day, Date date)
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.YEAR, year);
		ca.add(Calendar.MONTH, month);
		ca.add(Calendar.DAY_OF_MONTH, day);

		return ca.getTime();
	}

}
