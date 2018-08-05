package com.ace.cms.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 描述类的作用
 * Created by:lixin Date:16/8/14 Time:上午9:00
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AppVersionDto {
    private int id;
    private String appVersion;
    private String appType;
    private String appUrl;
    private String isForce;
    private String status;
    private String content;
    private int minVersion;
    private int alignType;
    private Date createdTime;
    private Date updatedTime;

}
