package com.ace.cms.vo.comment;

import com.ace.cms.vo.ContentVo;
import com.ace.cms.vo.user.Commenter;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiveReply {

    private String createTime;
    private Long replyId;
    private boolean anonymous;
    private String text;
    private Parent parent;
    private ContentVo contentVo;
    private Commenter commenter;
}
