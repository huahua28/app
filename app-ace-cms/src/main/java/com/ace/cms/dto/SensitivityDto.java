package com.ace.cms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitivityDto {
    private Long sensitivityId;
    private String search;
    private String replacement;
}
