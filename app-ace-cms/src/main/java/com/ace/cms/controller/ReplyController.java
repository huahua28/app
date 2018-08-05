package com.ace.cms.controller;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.service.ReplyService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.comment.PersonalReply;
import com.ace.cms.vo.comment.ReceiveReply;
import com.ace.cms.vo.comment.ReplyVo;
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
@RequestMapping(value = "/reply", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class ReplyController extends AbstractController{

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/list")
    public Response<List<ReplyVo>> get(@RequestParam(value = "jsessionId") String jsessionId,
                                        @RequestParam(value = "contentId") Long contentId,
                                        @RequestParam(value = "commentId") Long commentId,
                                        @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                        @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<ReplyVo> result = replyService.getReplyArrById(contentId, commentId, first, count);
        return new Response<>(result);

    }
    
    
    @RequestMapping(value = "/newsList")
    public Response<List<ReplyVo>> newsGet(@RequestParam(value = "contentId") Long contentId,
                                        @RequestParam(value = "commentId") Long commentId,
                                        @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                        @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        List<ReplyVo> result = replyService.getReplyArrById(contentId, commentId, first, count);
        return new Response<>(result);

    }
    @RequestMapping(value = "/personalList")
    public Response<List<PersonalReply>> personalGet(@RequestParam(value = "jsessionId") String jsessionId,
                                        @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                        @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<PersonalReply> result = replyService.getReplyArrByUserId(userVo.getUserId(), first, count);
        return new Response<>(result);

    }
    
    @RequestMapping(value = "/receiveList")
    public Response<List<ReceiveReply>> receiveGet(@RequestParam(value = "jsessionId") String jsessionId,
                                        @RequestParam(value = "first", required = false, defaultValue = "1") int first,
                                        @RequestParam(value = "count", required = false, defaultValue = "10") int count) {
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }
        List<ReceiveReply> result = replyService.getReplyByUserId(userVo.getUserId(), first, count);
        return new Response<>(result);

    }

}


