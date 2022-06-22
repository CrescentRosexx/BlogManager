package com.zhang.blogmanager1.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@TableName("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @TableId(value = "userid", type = IdType.AUTO) //指定id类型为自增长
    private Integer userid;
    private String name;
    private String password;
    private Date registertime;
    private Integer usertype;
    private Integer sex;
    private Integer age;
    private Date birth;
    private String email;

}
