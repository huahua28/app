package com.ace.cms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

@Getter
@AllArgsConstructor
public enum TableNameEnum {

	JC_USER("jc_user", "用户对象"),
	JC_CONTENT("jc_content", "CMS内容表对象"),
	JC_COMMENT("jc_comment", "CMS评论表对象"),
	;

    private String code;
    // 中文描述
    private String desc;

	public static TableNameEnum getErrorCodeByCode(String code) {
		for (TableNameEnum errorCodeEnum : TableNameEnum.values()) {
			if (StringUtils.equals(code, errorCodeEnum.getCode())) {
				return errorCodeEnum;
			}
		}
		return null;
	}
}