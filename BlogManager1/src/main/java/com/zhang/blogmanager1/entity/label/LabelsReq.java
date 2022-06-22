package com.zhang.blogmanager1.entity.label;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelsReq {
    private Integer page;
    private Integer limit;
    private Integer labelid;
    private String labelname;
}
