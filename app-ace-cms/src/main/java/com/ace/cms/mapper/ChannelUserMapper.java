package com.ace.cms.mapper;

import com.ace.cms.dto.ChannelDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChannelUserMapper {

	int insert(@Param("channelId") Integer channelId,@Param("userId") Long userId);

	int count(@Param("channelId") Integer channelId,@Param("userId") Long userId);

}
