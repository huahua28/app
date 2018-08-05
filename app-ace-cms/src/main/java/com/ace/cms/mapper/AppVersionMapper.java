package com.ace.cms.mapper;

import com.ace.cms.dto.AppVersionDto;
import org.apache.ibatis.annotations.Param;

public interface AppVersionMapper {

	AppVersionDto selectByAppType(@Param(value = "appType") String appType);
}
