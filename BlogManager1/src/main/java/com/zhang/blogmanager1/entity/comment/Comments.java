package com.zhang.blogmanager1.entity.comment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private Integer commentid;
    private Integer userid;
    private Integer articleid;
    private Date publishtime;
    private String content;
}
