package com.zhang.blogmanager1.entity.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesInfo {
    private Integer likeid;
    private String name;
    private Integer articleid;
    private String title;
    private Date liketime;
}
