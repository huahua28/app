package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto {

    private Long contentId;
    private Long channelId;
    private Long userId;
    private Long typeId;
    private Long modelId;
    private Long siteId;
    private Date sortDate;
    private int commentsDay;
    private int isRecommend;
    private int status;

    private String txt;
    private String txt1;
    private String title;
    private Long authorId;

}
