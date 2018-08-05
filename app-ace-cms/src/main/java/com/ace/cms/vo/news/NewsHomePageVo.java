package com.ace.cms.vo.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsHomePageVo {
    //图片
    private List picArr;
    //内容
    private String txt;
    //标题
    private String title;
    //内容ID
    private Long contentId;

}
