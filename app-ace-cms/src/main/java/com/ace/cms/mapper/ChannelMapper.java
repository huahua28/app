package com.ace.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ace.cms.dto.ChannelDto;

public interface ChannelMapper {

	List<ChannelDto> getListByParentId(@Param("parentId") Integer channelId);

	List<ChannelDto> getListByUserId(@Param("parentId") Integer parentId, @Param("userId") Long userId);

	ChannelDto getByChannelId(@Param("channelId") Long channelId);

}
