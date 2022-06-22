package com.zhang.blogmanager1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.article.ArticleInfo;
import com.zhang.blogmanager1.entity.article.Articles;
import com.zhang.blogmanager1.entity.article.ArticlesReq;
import com.zhang.blogmanager1.entity.article.LabelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticlesMapper extends BaseMapper<Articles> {
    //分页按条件查询文章
    Page<ArticleInfo> selectByPage(Page<Articles> page, @Param("req") ArticlesReq articlesReq);
    //查询点赞数
    int countLike(@Param("id")int articleid);
    //查询评论数
    int countComment(@Param("id")int articleid);

    //查询某文章的标签
    List<LabelInfo> selectLabelsByArticle(Integer id);

    //按labelname查询labelid
    Integer selectLabelIdByName(String labelname);
    //插入新label
    void insertLabel(String labelname);
    //获取新插入标签的labelid
    Integer selectMaxLabelid();
    //查询某文章是否含有某标签
    Integer selectArticleAndLabel(@Param("articleid")Integer articleid, @Param("labelid")Integer labelid);
    //插入新的article——label关系
    void insertArticleAndLabel(@Param("articleid")Integer articleid, @Param("labelid")Integer labelid);
    //删除article_label中的一个
    void deleteArticleAndLabel(@Param("articleid")Integer articleid, @Param("labelname")String labelname);

    //更新文章审核状态
    void updateArticleAudit(@Param("articleid")Integer articleid, @Param("audit")Integer audit);

    //删除文章
    void deleteArticle(Integer articleid);
    //批量删除文章
    void deleteBatchArticle(Map<String, Object> params);

    //添加新文章
    void addNewArticle(@Param("info") Articles article);
    //获取新插入文章的id
    Integer selectMaxArticleid();
}
