/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.gooalgene.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author ThinkGem
 * @version 2013-05-22
 */
public class DateUtils {

	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getDateNow() {
		String nowTime = format.format(new Date());
		return nowTime;
	}
    
}
