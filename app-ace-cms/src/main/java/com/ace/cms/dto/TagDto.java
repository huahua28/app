package com.ace.cms.dto;

import java.util.Date;

import com.ace.cms.dto.ContentDto.ContentDtoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {
    private Long id;
    private Long count;
    private String tag;

}
