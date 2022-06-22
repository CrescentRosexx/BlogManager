package com.zhang.blogmanager1.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {
    private String email;
    private Integer limit;
    private Integer max;
    private Integer min;
    private Integer page;
    private String name;
    private Integer usertype;
    private Integer sex;

}
