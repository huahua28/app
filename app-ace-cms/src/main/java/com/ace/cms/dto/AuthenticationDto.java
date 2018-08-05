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
public class AuthenticationDto {
    private Long userId;
    private String cardId;
    private String cardName;
    private int cardType;
    private String facadeImg;
    private String backImg;
    private Date loginTime;
    private Date updateTime;
}

