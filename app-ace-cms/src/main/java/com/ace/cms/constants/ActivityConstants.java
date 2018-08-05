package com.ace.cms.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:常量类
 * @author: sanhu
 * @date: 2017年4月6日 上午9:34:13
 */
public class ActivityConstants {
	
	/**
	 * 新用户
	 */
	public static final String USER_TYPE_NEW = "NEW";


	
	
	/**
	 * 参与活动结果map
	 */
	public static final Map<String, String> RESULT_MAP = new HashMap<String, String>();
	static {
		RESULT_MAP.put("A0000", "参与成功");
		RESULT_MAP.put("A0001", "用户不存在");
		RESULT_MAP.put("A0002", "用户未实名认证");
		RESULT_MAP.put("A0003", "用户未交押金");
	}
	
	

}
