package com.ace.cms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class OpportunityDto {

	    private Long opportunityId;
	    private Long userId;
	    private Date createTime;
	    private String logo;
	    private String title;
	    private int locale;
	    private int direction;
	    private int stage;
	    private String highlight;
	    private String description;
	    private int member;
	    private int finance;
	    private float money;
	    private String recruitment;
	    private String pic1;
	    private String pic2;
	    private String pic3;
	    private String url1;
	    private String url2;
	    private String url3;
	    private Date finishTime;
	    private float price;

}
