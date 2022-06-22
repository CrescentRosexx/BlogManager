package com.zhang.blogmanager1.entity.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesReq {
    private Integer limit;
    private Integer page;

    private String author;
    private String title;
    private Integer audit;

    private String publishBegin;
    private String publishEnd;

    private List<String> labels;
}
