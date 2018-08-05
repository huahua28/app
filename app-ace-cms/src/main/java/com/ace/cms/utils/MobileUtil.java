package com.ace.cms.utils;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileUtil {
	
	private final static Pattern PATTERN = Pattern.compile("^[1][0-9]{10}$");
	
	/**
	 * @Description: 验证手机号 
	 * @author: sanhu
	 * @date: 2018年4月9日  下午3:42:12
	 */
	public static boolean checkMobile(String mobile) {
		if(StringUtils.isBlank(mobile)) {
			log.warn("WARN checkMobile failed, mobile:{}", mobile);
			return false;
		}
		boolean flag = PATTERN.matcher(mobile).find();
		if(!flag) {
			log.warn("WARN checkMobile failed, mobile:{}", mobile);
		}
		return flag;
	}
	
}
