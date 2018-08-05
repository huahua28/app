package com.ace.cms.service;

import com.ace.cms.dto.ReptilianNewsDto;
import com.ace.cms.exceptions.SequenceException;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.content.PersonalContent;
import com.ace.cms.vo.news.NewsHomePageVo;
import com.ace.cms.vo.news.NewsVo;

import java.util.List;

public interface ContentService {

    List<Content> getListByChannelId(int channelId, int start, int pageSize);

    Content get(Long contentId);

    boolean add(Long userId, String txt, String imgUrl1, String imgUrl2, int isanonymous);

    List<NewsHomePageVo> getListByName(String name, int start, int pageSize);

    List<NewsHomePageVo> getNewsList(int channelId, int start, int pageSize);

    NewsVo getNews(Long contentId);

    boolean batchNews(ReptilianNewsDto dto) throws SequenceException;

	List<PersonalContent> getPersonalListByChannelId(Long userId, int i, int first, int count);

}
