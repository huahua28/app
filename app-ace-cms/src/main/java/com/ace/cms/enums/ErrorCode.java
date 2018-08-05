package com.ace.cms.enums;

import org.apache.commons.lang.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	OVERFLOW("OVERFLOW", "系统繁忙，请稍后再试"),
	PARMAS_HAS_ERROR("PARMAS_HAS_ERROR","参数异常"),
    SYS_ERR("SYS_ERR", "操作失败,请稍后再试"),
	NAME_ALREADY_EXIST("NAME_ALREADY_EXIST", "用户名已存在"),
	MOBILE_IS_REGISTER("MOBILE_IS_REGISTER", "手机号已注册"),
	LOGIN_OUT_OF_TIME("LOGIN_OUT_OF_TIME","登录过期"),
	USER_NOT_EXIST("USER_NOT_EXIST","用户不存在"),
	MOBILE_IS_NOT_REGISTER("MOBILE_IS_NOT_REGISTER", "手机号还未注册"),
	SENSITIVE_WORDS("SENSITIVE_WORDS", "用户名有特殊或敏感词汇"),
	USER_OR_PASSWORD_ERROR("USER_OR_PASSWORD_ERROR","账号或密码错误，请核对后再试"),
	OLD_PASSWORD_ERROR("OLD_PASSWORD_ERROR","旧密码输入错误，请核对后再试"),
	TWO_INCONSISTENCIES_IN_CIPHER_INPUT("TWO_INCONSISTENCIES_IN_CIPHER_INPUT","两次密码输入不一致"),
	;

    private String code;
    // 中文描述
    private String desc;

	public static ErrorCode getErrorCodeByCode(String code) {
		for (ErrorCode errorCodeEnum : ErrorCode.values()) {
			if (StringUtils.equals(code, errorCodeEnum.getCode())) {
				return errorCodeEnum;
			}
		}
		return null;
	}
}