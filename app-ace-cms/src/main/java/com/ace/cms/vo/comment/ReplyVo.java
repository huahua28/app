package com.ace.cms.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyVo {

    private String userimg;
    private String createTime;
    private Long replyId;
    private boolean anonymous;
    private String text;
    private Long userId;
    private String username;
    private Parent parent;
}
