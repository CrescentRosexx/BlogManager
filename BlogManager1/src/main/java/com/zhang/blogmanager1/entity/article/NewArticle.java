package com.zhang.blogmanager1.entity.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewArticle {
    private Integer userid;
    private String title;
    private String content;
    private Date publishtime;
    private Integer audit;
    private List<String> labels;
}
