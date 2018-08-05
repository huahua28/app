package com.ace.cms.vo.news;

import com.ace.cms.aspect.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsVo {
    //内容
    private List<Map<String,String>> txt;
    //标题
    private String title;
    //内容ID
    private Long contentId;

    private int commentNum;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date sortDate;

    private String channelName;
}
