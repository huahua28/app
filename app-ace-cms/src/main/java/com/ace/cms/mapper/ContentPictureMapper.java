package com.ace.cms.mapper;

import com.ace.cms.dto.ContentPictureDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentPictureMapper {

    List<ContentPictureDto> getPicArr(@Param("contentId") Long contentId);

    List<ContentPictureDto> getNewsPicArr(@Param("contentId") Long contentId);

    int insert(ContentPictureDto contentPictureDto);

}