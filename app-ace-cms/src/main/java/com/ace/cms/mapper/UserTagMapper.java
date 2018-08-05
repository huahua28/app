package com.ace.cms.mapper;

import com.ace.cms.dto.UserTagDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTagMapper {

    List<UserTagDto> getUserTagInfo(@Param("authorId") Long authorId);

}
