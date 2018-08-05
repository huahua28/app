package com.ace.cms.service.impl;

import com.ace.cms.cache.SerialService;
import com.ace.cms.cache.UserCache;
import com.ace.cms.dto.*;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.enums.TableNameEnum;
import com.ace.cms.exceptions.SequenceException;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.mapper.*;
import com.ace.cms.service.ContentService;
import com.ace.cms.vo.Page;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.content.PersonalContent;
import com.ace.cms.vo.news.NewsHomePageVo;
import com.ace.cms.vo.news.NewsVo;
import com.ace.cms.vo.user.Author;
import com.ace.cms.vo.user.AuthorTag;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ContentPictureMapper contentPictureMapper;

    @Autowired
    private UserCache userCache;

    @Autowired
    private UserTagMapper userTagMapper;

    @Autowired
    private SerialService serialService;

    @Autowired
    private ContentTxtMapper contentTxtMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public List<Content> getListByChannelId(int channelId, int start, int pageSize) {
        Page page = new Page(start,pageSize);
        //TODO 列表后期需要添加缓存
        List<ContentDto> list = contentMapper.getListByChannelId(channelId,page.getStartRow(),page.getPageCount());

        List<Content> result = new ArrayList<>();
        //TODO 此处需要添加缓存
        for(ContentDto contentDto:list) {
            Content content = new Content();
            if(contentDto.getIsRecommend() == 0) {
                content.setAnonymous(true);
            }
            content.setCommentNum(contentDto.getCommentsDay());
            content.setTxt(contentDto.getTxt());
            Long contentId=contentDto.getContentId();
            content.setContentId(contentId);
            List<ContentPictureDto> contentPictureDtos = contentPictureMapper.getPicArr(contentDto.getContentId());
            List picArr = new ArrayList();
            for(ContentPictureDto contentPictureDto: contentPictureDtos) {
                picArr.add(contentPictureDto.getImgPath());
            }
            content.setPicArr(picArr);
            Author author = new Author();
            Long authorId=contentDto.getAuthorId();
            if(!content.isAnonymous()) {
	            UserDto userDto = userCache.get(authorId);
	            if(!Objects.equals(userDto, null)) {
	                author.setId(userDto.getUserId());
	                author.setImg(userDto.getUserImg());
	                author.setName(userDto.getUsername());
	                List<UserTagDto> userTagDtos = userTagMapper.getUserTagInfo(userDto.getUserId());
	                List<AuthorTag> authorTags = new ArrayList<>();
	                for(UserTagDto userTagDto: userTagDtos) {
	                    AuthorTag authorTag = new AuthorTag();
	                    authorTag.setTag(userTagDto.getTag());
	                    // 1点亮 0不点亮
	                    if(userTagDto.getCount() > 3) {
	                        authorTag.setIsOn("1");
	                    } else {
	                        authorTag.setIsOn("0");
	                    }
	                    authorTags.add(authorTag);
	                }
	                author.setTag(authorTags);
	            }
            }else {
            	author.setId(-1l);
            	author.setImg("");
            	author.setName(String.valueOf(contentId^authorId));
            	author.setTag(new ArrayList<>());
            }
            content.setAuthor(author);
            result.add(content);

        }
        return result;
    }

    @Override
    public List<PersonalContent> getPersonalListByChannelId(Long userId,int channelId, int start, int pageSize) {
        Page page = new Page(start,pageSize);
        //TODO 列表后期需要添加缓存
        List<ContentDto> list = contentMapper.getPersonalListByChannelId(userId,channelId,page.getStartRow(),page.getPageCount());

        List<PersonalContent> result = new ArrayList<>();
        //TODO 此处需要添加缓存
        for(ContentDto contentDto:list) {
        	PersonalContent content = new PersonalContent();
            if(contentDto.getIsRecommend() == 0) {
                content.setAnonymous(true);
            }
            content.setCommentNum(contentDto.getCommentsDay());
            content.setTxt(contentDto.getTxt());
            Long contentId=contentDto.getContentId();
            content.setContentId(contentId);
            List<ContentPictureDto> contentPictureDtos = contentPictureMapper.getPicArr(contentDto.getContentId());
            List picArr = new ArrayList();
            for(ContentPictureDto contentPictureDto: contentPictureDtos) {
                picArr.add(contentPictureDto.getImgPath());
            }
            content.setPicArr(picArr);
            result.add(content);

        }
        return result;
    }
    
    
    @Override
    public Content get(Long contentId) {
        ContentDto contentDto = contentMapper.getByContentId(contentId);
        Content content = new Content();
        if(contentDto.getIsRecommend() == 0) {
            content.setAnonymous(true);
        }
        content.setCommentNum(contentDto.getCommentsDay());
        content.setTxt(contentDto.getTxt());
        content.setContentId(contentDto.getContentId());
        List<ContentPictureDto> contentPictureDtos = contentPictureMapper.getPicArr(contentDto.getContentId());
        List picArr = new ArrayList();
        for(ContentPictureDto contentPictureDto: contentPictureDtos) {
            picArr.add(contentPictureDto.getImgPath());
        }
        content.setPicArr(picArr);
        Author author = new Author();
        Long authorId=contentDto.getAuthorId();
        if(!content.isAnonymous()) {
	        UserDto userDto = userCache.get(authorId);
	        if(!Objects.equals(userDto, null)) {
	            author.setId(userDto.getUserId());
	            author.setImg(userDto.getUserImg());
	            author.setName(userDto.getUsername());
	            List<UserTagDto> userTagDtos = userTagMapper.getUserTagInfo(userDto.getUserId());
	            List<AuthorTag> authorTags = new ArrayList<>();
	            for(UserTagDto userTagDto: userTagDtos) {
	                AuthorTag authorTag = new AuthorTag();
	                authorTag.setTag(userTagDto.getTag());
	                // 1点亮 0不点亮
	                if(userTagDto.getCount() > 3) {
	                    authorTag.setIsOn("1");
	                } else {
	                    authorTag.setIsOn("0");
	                }
	                authorTags.add(authorTag);
	            }
	            author.setTag(authorTags);
	        }
        }else {
        	author.setId(-1l);
        	author.setImg("");
        	author.setName(String.valueOf(contentId^authorId));
        	author.setTag(new ArrayList<>());
        }
        content.setAuthor(author);
        return content;
    }

    /**
     * 传闻列表  channelId-->75
     * @param userId
     * @param txt
     * @param imgUrl1
     * @param imgUrl2
     * @param imgUrl3
     * @return
     */
    @Transactional
    @Override
    public boolean add(Long userId, String txt, String imgUrl1, String imgUrl2, int isanonymous) {

        long contentId = serialService.get(TableNameEnum.JC_CONTENT.getCode());

        //匿名评论 0匿名，1 非匿名
        ContentDto contentDto = ContentDto.builder()
                .contentId(contentId)
                .userId(userId)
                .channelId(75l)
                .typeId(2l)
                .modelId(5l)
                .siteId(1l)
                .isRecommend(isanonymous)
                .build();
        if(contentMapper.insert(contentDto)<=0){
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }

        contentMapper.saveContentChannel(contentId, 75l);

        if(contentTxtMapper.insert(ContentTxtDto.builder().contentId(contentId).txt(txt).build())<=0) {
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }

        if(StringUtils.isNotBlank(imgUrl1)) {
            ContentPictureDto contentPictureDto = ContentPictureDto.builder()
                    .contentId(contentId)
                    .priority(1l)
                    .imgPath(imgUrl1).build();
            if(contentPictureMapper.insert(contentPictureDto)<=0){
                throw new WebControllerException(ErrorCode.SYS_ERR);
            }
        }

        if(StringUtils.isNotBlank(imgUrl2)) {
            ContentPictureDto contentPictureDto = ContentPictureDto.builder()
                    .contentId(contentId)
                    .priority(2l)
                    .imgPath(imgUrl2).build();
            if(contentPictureMapper.insert(contentPictureDto)<=0){
                throw new WebControllerException(ErrorCode.SYS_ERR);
            }
        }

        return true;
    }

    @Override
    public List<NewsHomePageVo> getListByName(String name, int start, int pageSize) {

        List<ChannelDto> channelDtos = channelMapper.getListByParentId(78);

        Page page = new Page(start,pageSize);
        //TODO 列表后期需要添加缓存
        List<ContentDto> list = contentMapper.getListByName(name,page.getStartRow(),page.getPageCount(),channelDtos);

        List<NewsHomePageVo> result = new ArrayList<>();
        //TODO 此处需要添加缓存
        for(ContentDto contentDto:list) {
            NewsHomePageVo news= new NewsHomePageVo();
            news.setContentId(contentDto.getContentId());
            news.setTitle(contentDto.getTitle());
            if(StringUtils.isNotBlank(contentDto.getTxt1())) {
                List<Map<String,String>> newsTxtVos = new ArrayList<>();
                newsTxtVos = (List<Map<String,String>>) JSONArray.parse(contentDto.getTxt1());
                for(Map<String,String> map : newsTxtVos) {
                    if(StringUtils.isNotBlank(map.get("text"))){
                        news.setTxt(map.get("text"));
                        break;
                    }
                }
            }

            List<ContentPictureDto> contentPictureDtos = contentPictureMapper.getNewsPicArr(contentDto.getContentId());
            List picArr = new ArrayList();
            for(ContentPictureDto contentPictureDto: contentPictureDtos) {
                picArr.add(contentPictureDto.getImgPath());
            }
            news.setPicArr(picArr);
            result.add(news);

        }
        return result;
    }

    @Override
    public List<NewsHomePageVo> getNewsList(int channelId, int start, int pageSize) {
        Page page = new Page(start,pageSize);
        //TODO 列表后期需要添加缓存
        List<ContentDto> list = contentMapper.homePage(channelId,page.getStartRow(),page.getPageCount());

        List<NewsHomePageVo> result = new ArrayList<>();
        //TODO 此处需要添加缓存
        for(ContentDto contentDto:list) {
            NewsHomePageVo news= new NewsHomePageVo();
            news.setContentId(contentDto.getContentId());
            news.setTitle(contentDto.getTitle());
            if(StringUtils.isNotBlank(contentDto.getTxt1())) {
                List<Map<String,String>> newsTxtVos = new ArrayList<>();
                newsTxtVos = (List<Map<String,String>>) JSONArray.parse(contentDto.getTxt1());
                for(Map<String,String> map : newsTxtVos) {
                    if(StringUtils.isNotBlank(map.get("text"))){
                        news.setTxt(map.get("text"));
                        break;
                    }
                }
            }
            List<ContentPictureDto> contentPictureDtos = contentPictureMapper.getNewsPicArr(contentDto.getContentId());
            List picArr = new ArrayList();
            for(ContentPictureDto contentPictureDto: contentPictureDtos) {
                picArr.add(contentPictureDto.getImgPath());
            }
            news.setPicArr(picArr);
            result.add(news);

        }
        return result;
    }

    @Override
    public NewsVo getNews(Long contentId) {
        ContentDto contentDto = contentMapper.getNewsByContentId(contentId);
        if(Objects.equals(contentDto, null)) {
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }
        NewsVo newsVo = new NewsVo();
        newsVo.setCommentNum(contentDto.getCommentsDay());
        List<Map<String,String>> newsTxtVos = new ArrayList<>();
        if(StringUtils.isNotBlank(contentDto.getTxt1())) {
            newsTxtVos = (List<Map<String,String>>) JSONArray.parse(contentDto.getTxt1());
        }
        newsVo.setTxt(newsTxtVos);
        newsVo.setContentId(contentDto.getContentId());
        newsVo.setTitle(contentDto.getTitle());
        newsVo.setSortDate(contentDto.getSortDate());
        ChannelDto channelDto = channelMapper.getByChannelId(contentDto.getChannelId());
        newsVo.setChannelName(channelDto.getChannelName());
        return newsVo;
    }

    @Transactional
    @Override
    public boolean batchNews(ReptilianNewsDto dto) throws SequenceException {
        Long contentId = serialService.get(TableNameEnum.JC_CONTENT.getCode());
        ContentDto contentDto = ContentDto.builder()
                .contentId(contentId)
                .userId(0l)
                .channelId(90l)
                .typeId(2l)
                .modelId(1l)
                .siteId(1l)
                .isRecommend(0)
                .status(1)
                .sortDate(dto.getPublishTime())
                .build();
        contentMapper.insert(contentDto);

        contentMapper.saveContentChannel(contentId, 90l);

        contentMapper.saveContentExe(contentId, dto.getTitle());

        contentMapper.saveContentCheck(contentId,1,0);

        List<Map<String,String>> newsTxtVos = new ArrayList<>();

        if(StringUtils.isBlank(dto.getContext())) {
            return true;
        }
        newsTxtVos = (List<Map<String,String>>) JSONArray.parse(dto.getContext());
        StringBuilder stringBuilder = new StringBuilder();
        for(Map<String,String> map : newsTxtVos) {
            if(StringUtils.isNotBlank(map.get("text"))) {
                stringBuilder.append("<div><p>" + map.get("text") + "</p></div>");
            } else if (StringUtils.isNotBlank(map.get("img"))){
                stringBuilder.append("<img src = \""+map.get("img")+"\"/>");
            }
        }
        contentMapper.saveContentTxt(contentId, stringBuilder.toString(), dto.getContext());

        if(StringUtils.isBlank(dto.getPicUrl())) {
            return true;
        }
        List<Map<String,String>> url = new ArrayList<>();
        url = (List<Map<String,String>>) JSONArray.parse(dto.getPicUrl());
        for(int i=0; i< url.size(); i++) {
            ContentPictureDto contentPictureDto = ContentPictureDto.builder()
                    .contentId(contentId)
                    .priority(Long.valueOf(i))
                    .imgPath(url.get(i).get("url")).build();
            contentPictureMapper.insert(contentPictureDto);
        }

        return true;
    }

}
