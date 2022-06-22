package com.zhang.blogmanager1.entity.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelReq {
    Integer selectId;
    List<String> labels;
}
