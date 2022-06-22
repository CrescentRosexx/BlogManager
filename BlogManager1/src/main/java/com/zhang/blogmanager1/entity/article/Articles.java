package com.zhang.blogmanager1.entity.article;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articles {
    private Integer articleid;
    private Integer userid;
    private Date publishtime;
    private String title;
    private String content;
    private Integer audit;
}
