package com.ace.cms.controller;

import com.ace.cms.service.ContentService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.news.NewsHomePageVo;
import com.ace.cms.vo.news.NewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class NewsController extends AbstractController{

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/homePage")
	public Response<List<NewsHomePageVo>> homePage(@RequestParam(value = "first", required = false, defaultValue = "1") int first,
												   @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		// 首页默认展示推荐的新闻
		List<NewsHomePageVo> result = contentService.getNewsList(90,first,count);
		return new Response<>(result);

	}

	@RequestMapping(value = "/get")
	public Response<NewsVo> get(@RequestParam(value = "contentId") Long contentId) {
		return new Response<>(contentService.getNews(contentId));

	}

	//查询频道的新闻
	@RequestMapping(value = "/channelList")
	public Response<List<NewsHomePageVo>> channelList(@RequestParam(value = "channelId") int channelId,
											   @RequestParam(value = "first", required = false, defaultValue = "1") int first,
											   @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		// 展示的是频道的内容
		List<NewsHomePageVo> result = contentService.getNewsList(channelId,first,count);
		return new Response<>(result);

	}

	//查询频道的新闻
	@RequestMapping(value = "/search")
	public Response<List<NewsHomePageVo>> search(@RequestParam(value = "name") String name,
											   @RequestParam(value = "first", required = false, defaultValue = "1") int first,
											   @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
		// 展示的是频道的内容
		List<NewsHomePageVo> result = contentService.getListByName(name,first,count);
		return new Response<>(result);

	}


}
