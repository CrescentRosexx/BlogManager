package com.zhang.blogmanager1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.Results;
import com.zhang.blogmanager1.entity.like.LikeReq;
import com.zhang.blogmanager1.entity.like.Likes;
import com.zhang.blogmanager1.mapper.LikesMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/Like")
public class LikesController {
    @Resource
    private LikesMapper mapper;

    //获取点赞列表
    @PostMapping("/list")
    public Results<Page<Likes>> findPage(LikeReq likeReq) {
        System.out.println(likeReq);

        Page<Likes> page, likes;
        try {
            page = new Page<>(likeReq.getPage(), likeReq.getLimit());
            long startTime = System.currentTimeMillis();     //获取开始时间
            likes = mapper.selectByPage(page, likeReq);
            long overTime = System.currentTimeMillis();      //获取结束时间
            System.out.println("点赞查询时间为："+(overTime-startTime)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取点赞列表失败！");
        }
        return new Results<>(true, "", likes);
    }

    //删除点赞记录
    @DeleteMapping("/delete")
    public Results userDelete(Integer likeid) {

        Results results;
        System.out.println("删除点赞记录：" + likeid);
        try {
            mapper.likeDelete(likeid);
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "删除失败！");
        }

        return results;
    }

    //批量删除点赞记录
    @DeleteMapping("/batchDelete")
    public Results userBatchDelete(Integer[] ids) {

        Results results;
        System.out.println("批量删除点赞：" + Arrays.toString(ids));
        try {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("idList", Arrays.asList(ids));
            mapper.batchDelete(params);
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "删除失败！");
        }

        return results;
    }
}
