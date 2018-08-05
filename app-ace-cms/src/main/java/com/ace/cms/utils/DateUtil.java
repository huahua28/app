package com.ace.cms.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <ul>
 * <li>java时间工具类</li>
 * </ul>
 */
public class DateUtil {

	/** 日期格式：yyyyMMdd */
	public static final String datePattern = "yyyyMMdd";
	/** 日期格式：yyMMdd */
	public static final String shortDatePattern = "yyMMdd";
	/** 日期时间格式：yyyyMMddHHmmss */
	public static final String fullPattern = "yyyyMMddHHmmss";
	/** 日期时间格式：yyyyMMddHHmmss */
	public static final String readPattern = "yyyy-MM-dd HH:mm:ss,SSS";

	public static final String timePattern = "yyyy-MM-dd HH:mm:ss";

	public static final String mdPattern = "MM-dd";
	/** 日期时间格式：yyMMddHHmmss */
	public static final String partPattern = "yyMMddHHmmss";

	public static final String dateStr = "yyyy-MM-dd";

	public static final String times = "HH:mm:ss";

	private static final String INVALID_PARAM_MSG = "The payDate could not be null!";

	/**
	 * 获取当前日期
	 *
	 * @return 当前日期
	 */
	public static Date getCurrentDate() {
		return DateTime.now().toDate();
	}

	/**
	 * 获取当前时间 格式： yyyyMMddHHmmss
	 *
	 * @return 字符日期 格式：yyyyMMddHHmmss
	 */
	public static String getCurrent() {
		return getCurrent(fullPattern);
	}

	/**
	 * 获取当前时间 格式： 自定义
	 *
	 * @param pattern
	 *            时间格式
	 * @return 自定义格式的当前时间
	 */
	public static String getCurrent(String pattern) {
		return DateTime.now().toString(pattern);
	}

	/**
	 * 将字符串转换成固定格式时间
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            自定义格式
	 * @return 转换后日期
	 */
	public static Date parse(String date, String pattern) {
		if(StringUtils.isBlank(date)) {
			return null;
		}
		DateTime dateTime = parseTime(date, pattern);
		if (dateTime == null)
			return null;
		return dateTime.toDate();
	}

	public static DateTime parseTime(String date, String pattern) {
		return DateTimeFormat.forPattern(pattern).parseDateTime(date);
	}

	public static String format(Date date, String pattern) {
		if (date == null)
			return null;
		return new DateTime(date).toString(pattern);
	}

	public static String convert(String date, String targetPattern) {
		return convert(date, fullPattern, targetPattern);
	}

	public static String convert(String date, String originPattern, String targetPattern) {
		Date originDate = parse(date, originPattern);
		return format(originDate, targetPattern);
	}

	/**
	 * 获取当前时间
	 *
	 * @return Date
	 */
	public static Date getCurrentDate(String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(new DateTime().toString(pattern), format).toDate();
	}

	/**
	 * 根据 pattern 将 dateTime 时间进行格式化
	 *
	 * 用来去除时分秒，具体根据结果以 pattern 为准
	 * 
	 * @param date
	 *            payDate 时间
	 * @return payDate 时间
	 */
	public static Date formatToDate(Date date, String pattern) {
		if (date == null)
			return null;
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return DateTime.parse(new DateTime(date).toString(pattern), format).toDate();
	}

	/**
	 * 日期增减，负数为减
	 *
	 * @param dayNum
	 *            天数
	 * @return 时间
	 */
	public static Date plusDays(int dayNum) {
		return new DateTime().plusDays(dayNum).toDate();
	}

	/**
	 * 按秒偏移,根据{@code source}得到{@code seconds}秒之后的日期<Br>
	 *
	 * @param source
	 *            , 要求非空
	 * @param seconds
	 *            , 秒数,可以为负
	 * @return 新创建的Date对象
	 */
	public static Date addSeconds(Date source, int seconds) {
		return addDate(source, Calendar.SECOND, seconds);
	}
	/**
	 * 功能： 添加Minute
	 * @param cdate
	 * @param amount
	 * @return
	 */
	public static Date addMinute(Date cdate, int amount) {
		return addDate(cdate, Calendar.MINUTE, amount);
	}
	
	/**
	 * 功能： 添加hour
	 * @param cdate
	 * @param amount
	 * @return
	 */
	public static Date addHour(Date cdate, int amount) {
		return addDate(cdate, Calendar.HOUR, amount);
	}
	
	/** 
	 * @Description: 分钟转成 “00:00”格式展示
	 * @param min
	 * @return
	 * @author: zivs.zheng
	 * @date: 2017年6月30日  上午11:00:21
	 */
	public static String minuteToStr(int min) {
		Date date = DateUtil.formatToDate(new Date(), "yyyyMMdd");
		int hours = 0;
		int surplusMin = 0;
		if (min > 59) {// 超过一小时
			hours = min / 60;
			surplusMin = min % 60;
		} else {
			surplusMin = min;
		}
		Date datetemp = DateUtil.addMinute(date, surplusMin);
		String upStr = "00";
		if (hours < 10) {
			upStr = " 0" + hours;
		} else {
			upStr = "" + hours;
		}
		return upStr + ":" + DateUtil.format(datetemp, "mm");
	}

	private static Date addDate(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * Date转为"yyyy-MM-dd"格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * @Description: 当前时间加减，2017-04-06 17:27:34 + 1 = 2017-04-07 23:59:59
	 * @author: xuziwen
	 * @data：2017年4月6日 下午5:26:55
	 */
	public static Date addDays(int expireDays) {
		Date date = DateUtil.plusDays(expireDays);
		String string = DateUtil.dateTString(date) + " 23:59:59";
		return DateUtil.parse(string, timePattern);
	}
	
	/**
	 * @Description: 当前时间加减，2017-04-06 17:27:34 + 1 = 2017-04-07 23:59:59
	 * @author: xuziwen
	 * @data：2017年4月6日 下午5:26:55
	 */
	public static Date addDays(Date startTime, int expireDays) {
		Date date = new DateTime(startTime).plusDays(expireDays).toDate();
		String string = DateUtil.dateTString(date) + " 23:59:59";
		return DateUtil.parse(string, timePattern);
	}
	
	/**
	 * @Description: 获取相隔天数
	 * @author: litao
	 * @date: 2017年6月22日  下午9:18:25
	 */
	public static long getDaySub(Date beginDate,Date endDate){
        long day=0;
        day=(endDate.getTime() -beginDate.getTime() + 12*60*60*1000)/(24*60*60*1000);
        //System.out.println("相隔的天数="+day);
        return day;
    }
	
	/**
	 * @Description:把date的小时，分钟，秒换成23:59:59
	 * @param date
	 * @return yyyy-MM-dd 23:59:59
	 * @author: zivs.zheng
	 * @date: 2017年9月7日 下午10:05:13
	 */
	public static Date getDateOfMaxHourMinuteSecond(Date date)  {
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(date);
		cl1.set(Calendar.HOUR_OF_DAY, 23);
		cl1.set(Calendar.MINUTE, 59);
		cl1.set(Calendar.SECOND, 59);
		return cl1.getTime();
	}
	/**
	 * @Description:把date的小时，分钟，秒换成00:00:00
	 * @param date
	 * @return yyyy-MM-dd 00:00:00
	 * @author: zivs.zheng
	 * @date: 2017年9月7日 下午10:05:13
	 */
	public static Date getDateOfMinHourMinuteSecond(Date date)  {
		Calendar cl1 = Calendar.getInstance();
		cl1.setTime(date);
		cl1.set(Calendar.HOUR_OF_DAY, 00);
		cl1.set(Calendar.MINUTE, 00);
		cl1.set(Calendar.SECOND, 00);
		cl1.set(Calendar.MILLISECOND, 00);
		return cl1.getTime();
	}
    /**
     * @Description:获取本周剩余的秒数
     * @return
     * @author: zivs.zheng
     * @date: 2017年9月18日 下午7:07:32
     */
    public static int oddSecondOfWeek() {
        DateTime start = new DateTime();
        DateTime end =
            new DateTime().dayOfWeek().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }
    /**
     * 获取今天剩余的秒数
     * @return 秒数
     * @author: zivs.zheng
     * @date: 2017年9月18日 下午7:07:32
     */
    public static int oddSecondOfDay() {
        DateTime start = new DateTime();
        DateTime end = new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        return Seconds.secondsBetween(start, end).getSeconds();
    }

}
