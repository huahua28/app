package com.ace.cms.vo.content;

import com.ace.cms.vo.user.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalContent {
    //图片
    private List picArr;
    //内容
    private String txt;
    //内容ID
    private Long contentId;
    //评论数
    private Integer commentNum;

    private boolean anonymous;
}
