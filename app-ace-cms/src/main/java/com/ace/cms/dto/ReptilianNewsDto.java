package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReptilianNewsDto {

    private Long otherId;
    private String title;
    private int type;
    private String context;
    private String picUrl;
    private Date publishTime;

}
