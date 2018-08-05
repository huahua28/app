package com.ace.cms.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

	private static final String UTF_8 = "utf-8";

	public static StringBuilder buildBodyLog(HttpServletRequest request) {
		Map<String, ?> parameterMap = request.getParameterMap();
		StringBuilder logBuffer = new StringBuilder();
		List<String> keys = new ArrayList<String>(parameterMap.keySet());
		for (int i = 0; i < keys.size(); i++) {
			try {
				String key = URLDecoder.decode(keys.get(i), UTF_8);
				logBuffer.append(key);
				logBuffer.append("=");
				logBuffer.append(buildValueLog(parameterMap, key));
				logBuffer.append((i == keys.size() - 1) ? "" : "&");
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return logBuffer;
	}

	private static StringBuilder buildValueLog(Map<String, ?> parameterMap, String key)
			throws UnsupportedEncodingException {
		StringBuilder valueBuffer = new StringBuilder();
		String[] values = (String[]) parameterMap.get(key);
		if (values.length > 1) {
			valueBuffer.append("[");
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				if (StringUtils.isEmpty(value)) {
					value = URLDecoder.decode(values[0], UTF_8);
				}
				valueBuffer.append(value);
				valueBuffer.append((i == values.length - 1) ? "" : ",");
			}
			valueBuffer.append("]");
		} else {
			if (!StringUtils.isEmpty(values[0])) {
				valueBuffer.append(URLDecoder.decode(values[0], UTF_8));
			}
		}
		return valueBuffer;
	}

	/** 获取客户端ip */
	public static String getClientIp(HttpServletRequest request, boolean reverseProxy, String proxyHeader) {
		if (!reverseProxy) {
			return request.getRemoteAddr();
		} else {
			String ipHeader = null;
			if (null != proxyHeader && !"".equals(proxyHeader)) {
				ipHeader = request.getHeader(proxyHeader);
			}
			if (!isIpHeaderOk(ipHeader)) {
				String[] commonReverseProxyHeaderNanes = { "x-forwarded-for", "x-real-ip", "X-Forwarded-For",
						"Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", };
				int count = 0;
				while (!isIpHeaderOk(ipHeader) && count < commonReverseProxyHeaderNanes.length) {
					ipHeader = request.getParameter(commonReverseProxyHeaderNanes[count++]);
				}
			}
			if (!isIpHeaderOk(ipHeader)) {
				return "0.0.0.0";
			} else {
				return ipHeader;
			}
		}
	}

	private static boolean isIpHeaderOk(String ipHeader) {
		return null != ipHeader && !"".equals(ipHeader) && !"unknown".equalsIgnoreCase(ipHeader);
	}

}