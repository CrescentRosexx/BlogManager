package com.zhang.blogmanager1.entity.label;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Labels {
    private Integer labelid;
    private String labelname;
}
