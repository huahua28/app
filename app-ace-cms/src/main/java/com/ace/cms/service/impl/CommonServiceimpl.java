package com.ace.cms.service.impl;

import com.ace.cms.dto.AppVersionDto;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.mapper.AppVersionMapper;
import com.ace.cms.service.CommonService;
import com.ace.cms.vo.common.AppVersionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommonServiceimpl implements CommonService{

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersionVo getAppVersion(String source) {
        AppVersionDto appVersionDto = appVersionMapper.selectByAppType(source);
        if(Objects.equals(appVersionDto, null)) {
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }
        AppVersionVo appVersionVo = new AppVersionVo();
        BeanUtils.copyProperties(appVersionDto, appVersionVo);
        return appVersionVo;
    }
}
