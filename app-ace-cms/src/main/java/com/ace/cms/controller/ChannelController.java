package com.ace.cms.controller;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.service.ChannelService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.channel.ChannelVo;
import com.ace.cms.vo.user.UserVo;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/channel", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class ChannelController extends AbstractController{

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "/list")
    public Response<List<ChannelVo>> contentList(){
        List<ChannelVo> result = channelService.getListByParentId(78);
        return new Response<>(result);

    }


    @RequestMapping(value = "/add")
    public Response<Boolean> add(
            @RequestParam(value = "jsessionId") String jsessionId,
            @RequestParam(value = "channelId") int channelId){
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }

        return new Response<>(channelService.add(channelId, userVo.getUserId()));

    }

    @RequestMapping(value = "/select")
    public Response<List<ChannelVo>> select(
            @RequestParam(value = "jsessionId") String jsessionId){
        UserVo userVo = getUserVoBySession(jsessionId);
        if(Objects.equals(userVo, null)) {
            throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
        }

        return new Response<>(channelService.select(78, userVo.getUserId()));

    }

}


