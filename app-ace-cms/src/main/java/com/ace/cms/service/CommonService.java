package com.ace.cms.service;

import com.ace.cms.vo.common.AppVersionVo;
import com.ace.cms.vo.content.Content;
import com.ace.cms.vo.news.NewsVo;

import java.util.List;

public interface CommonService {

    AppVersionVo getAppVersion(String source);

}
