package com.zhang.blogmanager1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.DataGenerator.Generator;
import com.zhang.blogmanager1.entity.user.UserReq;
import com.zhang.blogmanager1.entity.Results;
import com.zhang.blogmanager1.entity.user.Users;
import com.zhang.blogmanager1.mapper.UsersMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/User")
public class UsersController {
    @Resource
    private UsersMapper usersMapper;

    //获取用户列表
    @PostMapping("/list")
    public Results<Page<Users>> findPage(UserReq userReq) {
        System.out.println(userReq.toString());
//        LambdaQueryWrapper<Users> wrapper = Wrappers.<Users>lambdaQuery();

        Page<Users> page, users;
        try {
            long startTime = System.currentTimeMillis();     //获取开始时间

            page = new Page<>(userReq.getPage(), userReq.getLimit());
            users = usersMapper.selectByPage(page, userReq);

            long overTime = System.currentTimeMillis();      //获取结束时间
            System.out.println("用户查询时间为："+(overTime-startTime)+"毫秒");

        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取用户列表失败！");
        }

        return new Results<>(true, "", users);
    }

    //修改用户
    @PostMapping("/edit")
    public Results userEdit(Users users) {

        Results results;
        System.out.println("修改用户" + users.toString());
        try {
            usersMapper.userUpdate(users.getUserid(), users.getName(), users.getPassword(), users.getUsertype(),
                    users.getSex(), users.getAge(), users.getBirth(), users.getEmail());
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "更新失败!");
        }
        return results;
    }

    //添加用户
    @PostMapping("/add")
    public Results userAdd(Users users) {
        System.out.println("添加用户" + users.toString());
        Results results;
        try {
            usersMapper.userInsert(users.getName(), users.getPassword(), users.getRegistertime(), users.getUsertype(),
                    users.getSex(), users.getAge(), users.getBirth(), users.getEmail());
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "插入失败!");
        }

        System.out.println(users.toString());
        return results;
    }

    //删除用户
    @DeleteMapping("/delete")
    public Results userDelete(Integer userid) {
        System.out.println("删除用户" + userid);

        try {

            usersMapper.userDelete(userid);

        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "删除失败！");
        }
//        for (int i = 0; i < 100000; i++) {
//            if (i % 500 == 0) {
//                System.out.println(i);
//            }
//            try {
//                Users users = Generator.userGen();
//                usersMapper.userInsert(users.getName(), users.getPassword(), users.getRegistertime(), users.getUsertype(),
//                        users.getSex(), users.getAge(), users.getBirth(), users.getEmail());
//            } catch (Exception e) {
//                //System.out.println("失败"+i);
//            }
//        }
//        System.out.println("已结束");
        return new Results(true, "");
    }

    //批量删除用户
    @DeleteMapping("/batchDelete")
    public Results userBatchDelete(Integer[] ids) {

        Results results;
        System.out.println("批量删除用户" + Arrays.toString(ids));
        try {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("idList", Arrays.asList(ids));
            usersMapper.deleteBatchIds(params);
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "删除失败！");
        }

        return results;
    }
}
