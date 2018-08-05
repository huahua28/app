package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private Long contentId;
    private Long parentId;
    private Long commentUserId;
    private Long ancestorId;
    private String text;
    private Date createTime;
    private int replyCount;
    private int isRecommend;
    private String fakeName;
    //保存的时候使用的
    private Long siteId;
    private Long channelId;
    private List<CommentDto> childList;
    private Date contentDate;
    private Long replyUserId;
}
