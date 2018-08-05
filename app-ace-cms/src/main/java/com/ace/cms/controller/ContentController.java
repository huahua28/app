package com.ace.cms.controller;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.service.ContentService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.content.PersonalContent;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class ContentController extends AbstractController{

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/list")
    public Response<List<Content>> contentList(@RequestParam(value = "jsessionId") String jsessionId,
                                               @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                               @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<Content> result = contentService.getListByChannelId(75,first,count);
        return new Response<>(result);

    }

    
    @RequestMapping(value = "/personalList")
    public Response<List<PersonalContent>> personalContentList(@RequestParam(value = "jsessionId") String jsessionId,
                                               @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                               @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<PersonalContent> result = contentService.getPersonalListByChannelId(userVo.getUserId(),75,first,count);
        return new Response<>(result);

    }

    
    
    @RequestMapping(value = "/wxlist", method = RequestMethod.GET)
    public Response<List<Content>> wxContentList(
                                               @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                               @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        List<Content> result = contentService.getListByChannelId(75,first,count);
        return new Response<>(result);

    }


    @RequestMapping(value = "/get")
    public Response<Content> get(@RequestParam(value = "jsessionId") String jsessionId,
                                       @RequestParam(value = "contentId") Long contentId) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        Content result = contentService.get(contentId);
        return new Response<>(result);

    }
    
    
    

    @RequestMapping(value = "/wxget", method = RequestMethod.GET)
    public Response<Content> wxContentGet(@RequestParam(value = "contentId") Long contentId) {
        Content result = contentService.get(contentId);
        return new Response<>(result);

    }

    /**
     * idea
     * @param jsessionId
     * @param imgUrl1
     * @param imgUrl2
     * @param isanonymous
     * @param txt
     * @return
     */
    @RequestMapping(value = "/add")
    public Response<Boolean> add(@RequestParam(value = "jsessionId") String jsessionId,
                                 @RequestParam(value = "imgUrl1" ,required = false) String imgUrl1,
                                 @RequestParam(value = "imgUrl2" ,required = false) String imgUrl2,
                                 @RequestParam(value = "isanonymous") int isanonymous,
                                 @RequestParam(value = "txt") String txt) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }

        if(txt.length()>5000) {
            txt=txt.substring(0,4999);
        }
        return new Response<>(contentService.add(userVo.getUserId(), txt, imgUrl1, imgUrl2, isanonymous));

    }




}


