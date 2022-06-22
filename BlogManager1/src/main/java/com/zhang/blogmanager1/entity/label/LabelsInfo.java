package com.zhang.blogmanager1.entity.label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelsInfo {
    private Integer labelid;
    private String labelname;
    private Integer quotenum;
}
