package com.zhang.blogmanager1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.like.LikeReq;
import com.zhang.blogmanager1.entity.like.Likes;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface LikesMapper extends BaseMapper<Likes> {

    //点赞按条件分页查询
    Page<Likes> selectByPage(Page<Likes> page, @Param("req") LikeReq likeReq);

    //删除点赞记录
    void likeDelete(Integer likeid);

    //批量删除点赞记录
    void batchDelete(Map<String, Object> params);
}
