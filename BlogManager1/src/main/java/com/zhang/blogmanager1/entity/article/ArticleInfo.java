package com.zhang.blogmanager1.entity.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfo {
    private Integer articleid;
    private String name;
    private Date publishtime;
    private String title;
    private String content;
    private Integer audit;
    private Integer likenum;
    private Integer commentnum;
}
