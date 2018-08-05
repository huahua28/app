package com.ace.cms.mapper;

import com.ace.cms.dto.AuthenticationDto;
import com.ace.cms.dto.ChannelDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthenticationMapper {

    int insert(AuthenticationDto dto);

    AuthenticationDto selectByUserId(@Param(value = "userId") Long userId);
}
