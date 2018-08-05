package com.ace.cms.service.impl;

import com.ace.cms.dto.OpportunityDto;
import com.ace.cms.dto.UserDto;
import com.ace.cms.vo.Page;
import com.ace.cms.vo.opportunity.OpportunityVo;
import com.ace.cms.vo.user.Author;
import com.ace.cms.vo.user.UserVo;
import com.ace.cms.mapper.OpportunityMapper;
import com.ace.cms.mapper.UserMapper;
import com.ace.cms.service.OpportunityService;
import com.ace.cms.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpportunityServiceImpl implements OpportunityService {

    @Autowired
    private OpportunityMapper opportunityMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<OpportunityVo> getList(int first,int count) {
    	Page page = new Page(first,count);
        List<OpportunityDto> list = opportunityMapper.getList(page.getStartRow(),page.getPageCount());

        List<OpportunityVo> result = new ArrayList<>();
        for(OpportunityDto dto:list) {
        	OpportunityVo vo=new OpportunityVo();
        	BeanUtils.copyProperties(dto, vo);
        	vo.setCreateTime(DateUtil.format(dto.getCreateTime(), DateUtil.timePattern));
        	if(vo.getLogo()==null)vo.setLogo("");
        	Author uv=new Author();
        	UserDto ud=userMapper.getUserInfo(dto.getUserId());
        	uv.setId(ud.getUserId());
        	uv.setImg(ud.getUserImg());
        	uv.setName(ud.getUsername());
        	uv.setTag(new ArrayList<>());
        	vo.setUser(uv);
        	result.add(vo);
        }
        return result;
    }
	@Override
	public OpportunityVo getDetail(int opportunityId) {
		OpportunityDto dto = opportunityMapper.getDetail(opportunityId);
		OpportunityVo vo=new OpportunityVo();
		BeanUtils.copyProperties(dto, vo);
		Author uv=new Author();
		UserDto ud=userMapper.getUserInfo(dto.getUserId());
    	uv.setId(ud.getUserId());
    	uv.setImg(ud.getUserImg());
    	uv.setName(ud.getUsername());
    	uv.setTag(new ArrayList<>());
    	vo.setUser(uv);
    	if(vo.getLogo()==null)vo.setLogo("");
    	if(vo.getHighlight()==null)vo.setHighlight("");
    	if(vo.getPic1()==null)vo.setPic1("");
    	if(vo.getPic2()==null)vo.setPic2("");
    	if(vo.getPic3()==null)vo.setPic3("");
    	if(vo.getUrl1()==null)vo.setUrl1("");
    	if(vo.getUrl2()==null)vo.setUrl2("");
    	if(vo.getUrl3()==null)vo.setUrl3("");
    	vo.setCreateTime(DateUtil.format(dto.getCreateTime(), DateUtil.timePattern));
    	vo.setFinishTime(DateUtil.format(dto.getFinishTime(), DateUtil.timePattern));
		return vo;
	}


}
