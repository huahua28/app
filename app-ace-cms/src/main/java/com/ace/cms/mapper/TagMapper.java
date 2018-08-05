package com.ace.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ace.cms.dto.TagDto;

public interface TagMapper {
	Long addTag(TagDto dto);

	void tagCount(@Param("tagId")Long tagId, @Param("num")long num);

	List<TagDto> searchTag(@Param("keyword")String keyword);

	List<TagDto> recommendTag();
}
