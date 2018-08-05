package com.ace.cms.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorTag {

    //1是点亮 0非点亮
    private String isOn;
    private String tag;

}
