package com.ace.cms.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private String img;
    private String name;
    private Long id;
    private List<AuthorTag> tag;
}
