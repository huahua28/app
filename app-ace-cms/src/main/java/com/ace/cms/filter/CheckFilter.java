package com.ace.cms.filter;

import com.ace.cms.vo.Response;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.utils.LogUtils;
import com.ace.cms.utils.MD5Encrypt;
import com.ace.cms.utils.MobileUtil;
import com.ace.cms.utils.WriterResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zivs.zheng
 * @description: session验证, 签名验证，基础请求参数验证拦截器
 * @date 2018/4/27 10:31
 */
@Slf4j
public class CheckFilter implements Filter {

    private static final int LOW_VERSION = 100;

    private Set<String> whiteUrls = new HashSet<>();
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Properties whiteUrlsProperties = new Properties();
        InputStreamReader isr = null;
        try {
            InputStream in = this.getClass().getResourceAsStream("/request-white-urls.yml");
            isr = new InputStreamReader(in, UTF8);
            whiteUrlsProperties.load(isr);
            // 初始化白名单
            initWhiteUrls(whiteUrlsProperties);
        } catch (IOException e) {
            log.error("initialize CheckFilter error: ", e);
            throw new ServletException(e);
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    log.error("initialize CheckFilter error: ", e);
                }
            }
        }
    }

    private void initWhiteUrls(Properties properties) {
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            if (name.startsWith("/")) {
                whiteUrls.add(name);
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");  // 若有端口需写全（协议+域名+端口）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            // 请求URI
            String requestURI = request.getRequestURI();

            /** 基础请求参数 */
            String mobile = request.getParameter("mobile");
            String source = request.getParameter("source");
            String version = request.getParameter("version");
            String sessionKey = request.getParameter("sessionKey");
            String lng = request.getParameter("lng");
            String lat = request.getParameter("lat");
            String skey = request.getParameter("skey");
            String activityId = request.getParameter("activityId");

            /** 白名单校验 */
            if (whiteUrls.contains(requestURI)) {
                chain.doFilter(request, response);
                return;
            }
            /** 基础参数有效 */
            if (StringUtils.isBlank(mobile) || StringUtils.isBlank(source) || StringUtils.isBlank(version)
                    || StringUtils.isBlank(sessionKey) || StringUtils.isBlank(activityId)) {
//                throw new WebControllerException(ErrorCode.PARMAS_HAS_ERROR);
            }
            /** 手机号位数有效 */
            if (!MobileUtil.checkMobile(mobile)) {
//                throw new WebControllerException(ErrorCode.PARMAS_HAS_ERROR);
            }
            /**  允许业务范围的最低版本 */
            if (Integer.valueOf(version) < LOW_VERSION) {
//                throw new WebControllerException(ErrorCode.LOW_VERSION);
            }
            /** 版本>=123 验签校验 */
            if (Integer.valueOf(version) >= 123) {
            	//安卓129版本验签问题
            	if(!Objects.equals(source, "ANDROID") && !Objects.equals(version, 129)) {
            		this.checkSkey(mobile, sessionKey, lng, lat, requestURI, skey);
            	}
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            Response<Map<String, Object>> result;
            if (e instanceof WebControllerException) {
                result = new Response<>(((WebControllerException) e).getCode(), e.getMessage());
                log.error(" Method invoke URL:{}?{}  Exception={}.", request.getRequestURL().toString(), LogUtils.buildBodyLog(request).toString(), e.getMessage());
            } else {
                result = new Response<>(ErrorCode.SYS_ERR.getCode(), ErrorCode.SYS_ERR.getDesc());
                log.error(" Method invoke URL:{}?{}  Exception={}.", request.getRequestURL().toString(), LogUtils.buildBodyLog(request).toString(), e);
            }
            WriterResponseUtil.writerResponse(response, result);
        }
    }


    /**
     * 验证签名
     */
    private void checkSkey(String mobile, String sessionKey, String lng, String lat, String publicKey, String skey) {
        // 验签必要参数
        if (StringUtils.isBlank(publicKey) || StringUtils.isBlank(skey)) {
//            throw new WebControllerException(ErrorCode.PARMAS_HAS_ERROR);
        }
        MD5Encrypt encoderMd5 = new MD5Encrypt();
        // 签名生成
        String result = encoderMd5.encode("" + mobile.charAt(2) + mobile.charAt(4) + mobile.charAt(7) + mobile.charAt(10)
                + sessionKey + lng + lat + publicKey);
        // 验证签名
        if (!Objects.equals(result.toLowerCase(), skey.toLowerCase())) {
            // 验签不过，非法请求
            log.warn(">>>>> ILLEGAL REQUEST: mobile:{}; sessionKey:{}; lng:{}; lat:{}; publicKey:{}; skey:{}; result:{}", mobile, sessionKey, lng, lat, publicKey, skey.toLowerCase(), result.toLowerCase());
//            throw new WebControllerException(ErrorCode.ILLEGAL_REQUEST);
        }
    }
}
