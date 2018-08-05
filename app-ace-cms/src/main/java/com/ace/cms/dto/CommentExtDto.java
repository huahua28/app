package com.ace.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentExtDto {

    private Long commentId;
    private String ip;
    private String text;

    private String reply;
}
