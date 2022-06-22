package com.zhang.blogmanager1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.Results;
import com.zhang.blogmanager1.entity.comment.Comments;
import com.zhang.blogmanager1.entity.comment.CommentsInfo;
import com.zhang.blogmanager1.entity.comment.CommentsReq;
import com.zhang.blogmanager1.entity.like.LikeReq;
import com.zhang.blogmanager1.entity.like.Likes;
import com.zhang.blogmanager1.mapper.CommentsMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/Comment")
public class CommentsController {
    @Resource
    CommentsMapper mapper;

    //获取评论列表
    @PostMapping("/list")
    public Results<Page<CommentsInfo>> findPage(CommentsReq commentsReq) {
        System.out.println(commentsReq);

        Page<CommentsInfo> page, comments;
        try {
            page = new Page<>(commentsReq.getPage(), commentsReq.getLimit());
            long startTime = System.currentTimeMillis();     //获取开始时间
            comments = mapper.selectByPage(page, commentsReq);
            long overTime = System.currentTimeMillis();      //获取结束时间
            System.out.println("评论查询时间为："+(overTime-startTime)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取点赞列表失败！");
        }
        return new Results<>(true, "", comments);
    }
    //审核状态修改
    @PostMapping("/audit")
    public Results updateAudit(Integer commentid, Integer audit) {
        System.out.println("修改评论审核状态: " + commentid + " audit:" + audit);
        try {
            mapper.updateCommentAudit(commentid, audit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "保存失败！");
        }
        return new Results<>(true, "");
    }

    //删除评论
    @DeleteMapping("/delete")
    public Results commentDelete(Integer commentid) {
        System.out.println("删除评论：" + commentid);

        try {
            mapper.deleteComment(commentid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "删除失败！");
        }
        return new Results<>(true, "");
    }

    //批量删除评论
    @DeleteMapping("/batchDelete")
    public Results commentBatchDelete(Integer[] ids) {

        System.out.println("批量删除评论：" + Arrays.toString(ids));
        try {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("idList", Arrays.asList(ids));
            mapper.deleteBatchComment(params);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "删除失败！");
        }

        return new Results<>(true, "");
    }
}
