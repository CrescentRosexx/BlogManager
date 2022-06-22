package com.zhang.blogmanager1.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsInfo {
    private Integer commentid;
    private String name;
    private String title;
    private Integer articleid;
    private Date publishtime;
    private String content;
    private Integer audit;
}
