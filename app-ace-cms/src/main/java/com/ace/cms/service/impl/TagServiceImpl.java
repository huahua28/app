package com.ace.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.cms.dto.TagDto;
import com.ace.cms.mapper.TagMapper;
import com.ace.cms.service.TagService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

	@Override
	public Long addTag(String tag) {
		TagDto td=new TagDto();
		td.setTag(tag);
		tagMapper.addTag(td);
		return td.getId();
		 
	}

	@Override
	public List<TagDto> searchTag(String keyword) {
		
		return tagMapper.searchTag(keyword);
	}

	@Override
	public List<TagDto> recommendTag() {
		// TODO Auto-generated method stub
		return tagMapper.recommendTag();
	}
    
    

}
