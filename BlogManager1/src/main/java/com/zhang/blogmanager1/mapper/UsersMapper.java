package com.zhang.blogmanager1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.user.UserReq;
import com.zhang.blogmanager1.entity.user.Users;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

public interface UsersMapper extends BaseMapper<Users> {

    //插入新用户
    void userInsert(@Param("name") String name, @Param("password") String password, @Param("registertime") Date registertime,
                   @Param("usertype") Integer usertype, @Param("sex") Integer sex, @Param("age") Integer age,
                   @Param("birth") Date birth, @Param("email") String email);
    //更新用户
    void userUpdate(@Param("userid") Integer userid, @Param("name") String name, @Param("password") String password,
                    @Param("usertype") Integer usertype, @Param("sex") Integer sex, @Param("age") Integer age,
                    @Param("birth") Date birth, @Param("email") String email);
    //删除用户
    void userDelete(@Param("userid") Integer userid);
    //批量删除用户
    void deleteBatchIds(Map<String, Object> params);
    //用户分页按条件查询
    Page<Users> selectByPage(Page<Users> page, @Param("req") UserReq userReq);

}
