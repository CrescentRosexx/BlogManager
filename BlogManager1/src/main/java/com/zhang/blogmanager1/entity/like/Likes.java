package com.zhang.blogmanager1.entity.like;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likes {
    private Integer likeid;
    private Integer userid;
    private Integer articleid;
    private Date liketime;
}
