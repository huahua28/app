package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentPictureDto {

    private Long contentId;
    private Long priority;
    private String imgPath;
    private String description;

}
