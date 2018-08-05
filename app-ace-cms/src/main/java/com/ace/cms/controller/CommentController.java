package com.ace.cms.controller;

import com.ace.cms.dto.UserDto;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.service.CommentService;
import com.ace.cms.service.UserService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.comment.Comment;
import com.ace.cms.vo.comment.PersonalComment;
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
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class CommentController extends AbstractController{

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/list")
    public Response<List<Comment>> get(@RequestParam(value = "jsessionId") String jsessionId,
                                       @RequestParam(value = "contentId") Long contentId,
                                       @RequestParam(value = "onlyRoot", required = false, defaultValue = "false") boolean onlyRoot,
                                       @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                       @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<Comment> result = commentService.getCommentById(contentId,onlyRoot,first,count);
        return new Response<>(result);

    }

    @RequestMapping(value = "/newsList")
    public Response<List<Comment>> newsList(@RequestParam(value = "contentId") Long contentId,
                                       @RequestParam(value = "onlyRoot", required = false, defaultValue = "false") boolean onlyRoot,
                                       @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                       @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        List<Comment> result = commentService.getCommentById(contentId,onlyRoot,first,count);
        return new Response<>(result);

    }

//    @RequestMapping(value = "/personalList")
//    public Response<List<PersonalComment>> personalGet(@RequestParam(value = "jsessionId") String jsessionId,
//                                       @RequestParam(value = "first", required = false, defaultValue = "1") int first,
//                                       @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
//        UserVo userVo = getUserVoBySession(jsessionId);
//        if(Objects.equals(userVo, null)) {
//            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
//        }
//        List<PersonalComment> result = commentService.getPersonalComment(userVo.getUserId(),first,count);
//        return new Response<>(result);
//
//    }
    
    @RequestMapping(value = "/wxlist", method = RequestMethod.GET)
    public Response<List<Comment>> wxList(
                                       @RequestParam(value = "contentId") Long contentId,
                                       @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                       @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        List<Comment> result = commentService.getCommentOnlyById(contentId,first,count);
        return new Response<>(result);

    }
    
    @RequestMapping(value = "/add")
    public Response<Boolean> add(@RequestParam(value = "jsessionId") String jsessionId,
                                        @RequestParam(value = "contentId") Long contentId,
                                        @RequestParam(value = "parentCommentId") Long parentCommentId,
                                        @RequestParam(value = "isanonymous") int isanonymous,
                                        @RequestParam(value = "text") String text) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        if(text.length()>1000) {
            text=text.substring(0,999);
        }
        commentService.add(contentId,userVo.getUserId(),parentCommentId,text,isanonymous);
        return new Response<>(true);
    }




}


