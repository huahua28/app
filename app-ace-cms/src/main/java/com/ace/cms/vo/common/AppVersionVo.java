package com.ace.cms.vo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述类的作用
 *
 */
@Data
public class AppVersionVo {

    private String appVersion;
    private String appUrl;
    private String isForce;
    private String content;
    private int minVersion;
    private int alignType;
}
