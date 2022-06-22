package com.zhang.blogmanager1.entity.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeReq {
    private Integer page;
    private Integer limit;
    private String author;
    private Integer articleid;
    private String title;
    private String publishBegin;
    private String publishEnd;
}
