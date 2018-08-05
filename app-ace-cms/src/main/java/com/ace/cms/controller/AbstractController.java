package com.ace.cms.controller;

import com.ace.cms.cache.UserCache;
import com.ace.cms.cache.UserSessionCache;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.utils.MobileUtil;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
public abstract class AbstractController {

	@Autowired
    private UserCache userCache;
    @Autowired
    private UserSessionCache userSessionCache;
    public HttpServletRequest getRequest() {
        HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return curRequest;
    }

    public HttpServletResponse getResponse() {
        HttpServletResponse curResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return curResponse;
    }

    public UserVo getUserVoBySession(String jsessionId) {
        return userSessionCache.get(jsessionId);
    }
    
    public void removeUserVoBySession(String jsessionId) {
        userSessionCache.invalidate(jsessionId);
    }

    public void removeUserVo(Long id) {
        userCache.invalidate(id);
    }
    
    /**
     * @Description: 手机号校验
     * @author: sanhu
     * @date: 2017年6月13日  下午3:28:19
     */
    public void checkMobile(String mobile) {
        if (!MobileUtil.checkMobile(mobile)) {
            throw new WebControllerException(ErrorCode.PARMAS_HAS_ERROR);
        }
    }


    /**
     * 组装http所有请求参数
     *
     * @return 请求参数
     */
    public String getAllRequestParam() {
        Enumeration<String> e = getRequest().getParameterNames();
        StringBuilder string = new StringBuilder();
        while (e.hasMoreElements()) {
            String parName = e.nextElement();
            string.append(parName + "：");
            string.append(getRequest().getParameter(parName) + "，");
        }
        return string.toString();
    }

}