package com.ace.cms.service;

import java.util.List;

import com.ace.cms.dto.TagDto;

public interface TagService {

    Long addTag(String tag);

	List<TagDto> searchTag(String keyword);

	List<TagDto> recommendTag();

}
