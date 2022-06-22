package com.zhang.blogmanager1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.article.ArticleInfo;
import com.zhang.blogmanager1.entity.article.Articles;
import com.zhang.blogmanager1.entity.article.ArticlesReq;
import com.zhang.blogmanager1.entity.comment.Comments;
import com.zhang.blogmanager1.entity.comment.CommentsInfo;
import com.zhang.blogmanager1.entity.comment.CommentsReq;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommentsMapper extends BaseMapper<Comments> {
    //分页按条件查询评论
    Page<CommentsInfo> selectByPage(Page<CommentsInfo> page, @Param("req") CommentsReq commentsReq);
    //更新评论审核状态
    void updateCommentAudit(@Param("commentid")Integer commentid, @Param("audit")Integer audit);

    //删除评论
    void deleteComment(Integer commentid);
    //批量删除评论
    void deleteBatchComment(Map<String, Object> params);
}
