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
public class UserExtDto {
    private Long userId;

    private String userImg;

    private String mobile;

    //1男2女
    private Integer gender;

}
