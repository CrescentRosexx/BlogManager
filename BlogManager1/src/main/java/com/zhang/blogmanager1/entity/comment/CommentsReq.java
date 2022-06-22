package com.zhang.blogmanager1.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsReq {
    private Integer page;
    private Integer limit;

    private String author;
    private String title;
    private Integer audit;
    private Integer articleid;
    private String content;

    private String publishBegin;
    private String publishEnd;
}
