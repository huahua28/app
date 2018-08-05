package com.ace.cms.controller;

import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.service.CommonService;
import com.ace.cms.service.ContentService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.common.AppVersionVo;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class CommonController extends AbstractController{

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/sendCode")
    public Response<Boolean> sendCode(@RequestParam(value = "mobile")  String mobile){
        //缓存中添加短信验证码
        return new Response<Boolean>(true);
    }

    @RequestMapping(value = "/verifyCode")
    public Response<Boolean> verifyMobile(@RequestParam(value = "mobile")  String mobile){
        //用户验证码校验
        return new Response<>(true);
    }

    @RequestMapping(value = "/appVersion")
    public Response<AppVersionVo> appVersion(@RequestParam(value = "source") String source
            , @RequestParam(value = "version") int version) {
        AppVersionVo appVersionVo = commonService.getAppVersion(source);
        int minVersion = appVersionVo.getMinVersion();
        if(minVersion > version) {
            appVersionVo.setIsForce("YES");
        }
        return new Response<>(appVersionVo);
    }


}


