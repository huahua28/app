package com.ace.cms.controller;

import com.ace.cms.service.OpportunityService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.opportunity.OpportunityVo;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/opportunity", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class OpportunityController extends AbstractController{

    @Autowired
    private OpportunityService opportunityService;

    @RequestMapping(value = "/list")
    public Response<List<OpportunityVo>> opportunityList(
    		@RequestParam(value = "jsessionId", required = false) String jsessionId,
            @RequestParam(value = "first", required = false,defaultValue = "1") int first,
            @RequestParam(value = "count", required = false,defaultValue = "10") int count){
        List<OpportunityVo> result = opportunityService.getList(first,count);
        return new Response<>(result);
    }
    

    @RequestMapping(value = "/get")
    public Response<OpportunityVo> opportunityGet(
    		@RequestParam(value = "jsessionId", required = false) String jsessionId,
            @RequestParam(value = "opportunityId", required = true) int opportunityId){
        OpportunityVo result = opportunityService.getDetail(opportunityId);
        return new Response<>(result);
    }

}


