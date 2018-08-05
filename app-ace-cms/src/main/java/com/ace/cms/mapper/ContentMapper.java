package com.ace.cms.mapper;

import com.ace.cms.dto.ChannelDto;
import com.ace.cms.dto.ContentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper {

	List<ContentDto> getListByChannelId(@Param("channelId") Integer channelId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

	int addComments(@Param("contentId") Long contentId);

	ContentDto getByContentId(@Param("contentId") Long contentId);

	int insert(ContentDto contentDto);

	List<ContentDto> getListByName(@Param("name") String name, @Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("list") List<ChannelDto> channelDtos);

	List<ContentDto> homePage(@Param("channelId") Integer channelId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

	ContentDto getNewsByContentId(@Param("contentId") Long contentId);

	int saveContentExe(@Param("contentId") Long contentId, @Param("title") String title);

	int saveContentTxt(@Param("contentId") Long contentId, @Param("txt") String txt,@Param("txt1") String txt1);

	int saveContentChannel(@Param("contentId") Long contentId, @Param("channelId") Long channelId);

	int saveContentCheck(@Param("contentId") Long contentId, @Param("checkStep") int checkStep,@Param("isRejected") int isRejected);

	List<ContentDto> getPersonalListByChannelId(@Param("userId") Long userId,@Param("channelId") Integer channelId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
