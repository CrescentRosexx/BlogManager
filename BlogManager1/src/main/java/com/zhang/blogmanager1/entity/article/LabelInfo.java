package com.zhang.blogmanager1.entity.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelInfo {
    private String labelname;
    private Integer support;
}
