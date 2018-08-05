package com.ace.cms.vo.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
	private String userImg;
    private String username;
    private Long userId;
    private Integer gender;
    private List<UserTag> tag; 
}
