package com.ace.cms.mapper;

import com.ace.cms.dto.ChannelDto;
import com.ace.cms.dto.ContentDto;
import com.ace.cms.dto.ReptilianNewsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReptilianNewsMapper {

	List<ReptilianNewsDto> get();

}
