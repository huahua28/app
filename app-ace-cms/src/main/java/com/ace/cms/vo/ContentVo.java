package com.ace.cms.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentVo {
	private Long channelId;
	private Long contentId;
	private String text;
	private String picUrl;
	private String createTime;
}
