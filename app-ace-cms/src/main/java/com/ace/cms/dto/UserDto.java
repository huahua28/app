package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userImg;

    private Long userId;

    private Long groupId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String sessionId;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 状态 0审核通过  1禁用  2待审核
     */
    private int statu;

    /**
     * QQ授权ID
     */
    private String qqId;

    /**
     * 微信授权ID
     */
    private String wechatId;

    /**
     * 微博授权ID
     */
    private String weiboId;

    /**
     * 阿里授权ID
     */
    private String alipayId;

}
