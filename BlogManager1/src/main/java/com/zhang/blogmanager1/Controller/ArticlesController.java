package com.zhang.blogmanager1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.article.*;
import com.zhang.blogmanager1.entity.Results;
import com.zhang.blogmanager1.mapper.ArticlesMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Article")
public class ArticlesController {
    @Resource
    private ArticlesMapper mapper;

    //获取文章列表
    @PostMapping("/list")
    public Results<Page<ArticleInfo>> findPage(ArticlesReq formInLine) {
        System.out.println(formInLine.toString());
        Page<ArticleInfo> articles;

        try {
            long startTime = System.currentTimeMillis();     //获取开始时间

            Page<Articles> page = new Page<>(formInLine.getPage(), formInLine.getLimit());
            articles = mapper.selectByPage(page, formInLine);

            long overTime = System.currentTimeMillis();      //获取结束时间
            System.out.println("文章查询时间："+(overTime-startTime)+"毫秒");

//            startTime = System.currentTimeMillis();     //获取开始时间
//            for (int i = 0; i < articles.getRecords().size(); i++) {
//                ArticleInfo item = articles.getRecords().get(i);
//                item.setLikenum(mapper.countLike(item.getArticleid()));
//                item.setCommentnum(mapper.countComment(item.getArticleid()));
//            }
//            overTime = System.currentTimeMillis();      //获取结束时间
//            System.out.println("点赞评论查询时间："+(overTime-startTime)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取数据失败！", null);
        }

        return new Results<>(true, "", articles);
    }

    //获取某文章的标签
    @PostMapping("/labels")
    public Results<List<LabelInfo>> labelList(Integer articleid) {
        System.out.println("获取文章的标签：" + articleid);
        List<LabelInfo> labels;
        try {
            labels = mapper.selectLabelsByArticle(articleid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取标签失败！", null);
        }

        return new Results<>(true, "", labels);
    }

    //修改某文章的标签
    @PostMapping("/labelUpdate")
    public Results submitLabel(LabelReq labelReq) {
        System.out.println("修改标签：" + labelReq);
        int articleid = labelReq.getSelectId();
        List<String> submit = labelReq.getLabels();
        try {
            //1.查看提交的label是否存在，没有则创建新的label,
            for (String label : submit) {
                Integer labelid = mapper.selectLabelIdByName(label);
                if (labelid == null) {
                    mapper.insertLabel(label);
                    labelid = mapper.selectMaxLabelid();
                }
                //查询是否有这个label，没有则插入article_label中
                if (mapper.selectArticleAndLabel(articleid, labelid) == null) {
                    mapper.insertArticleAndLabel(articleid, labelid);
                }
            }
            //2.找出当前文章所有的labelname
            List<LabelInfo> names = mapper.selectLabelsByArticle(articleid);
            //3.submit和names，names有而submit没有的需要删去
            for (LabelInfo label : names) {
                if (!submit.contains(label.getLabelname())) {
                    mapper.deleteArticleAndLabel(labelReq.getSelectId(), label.getLabelname());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "保存失败！");
        }

        return new Results<>(true, "");
    }

    //审核状态修改
    @PostMapping("/audit")
    public Results updateAudit(Integer articleid, Integer audit) {
        System.out.println("修改文章审核状态: " + articleid + " audit:" + audit);
        try {
            mapper.updateArticleAudit(articleid, audit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "保存失败！");
        }
        return new Results<>(true, "");
    }

    //删除文章
    @DeleteMapping("/delete")
    public Results articleDelete(Integer articleid) {
        System.out.println("删除文章：" + articleid);

        try {
            mapper.deleteArticle(articleid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "删除失败！");
        }
        return new Results<>(true, "");
    }

    //批量删除文章
    @DeleteMapping("/batchDelete")
    public Results articleBatchDelete(Integer[] ids) {

        System.out.println("批量删除文章：" + Arrays.toString(ids));
        try {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("idList", Arrays.asList(ids));
            mapper.deleteBatchArticle(params);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "删除失败！");
        }

        return new Results<>(true, "");
    }

    //发布文章
    @PostMapping("/add")
    public Results addArticle(NewArticle article) {
        System.out.println("发布文章："+article.getTitle());

        try {
            //1.插入新文章
            Articles articles = new Articles(0, article.getUserid(),
                    article.getPublishtime(), article.getTitle(), article.getContent(),article.getAudit());
            mapper.addNewArticle(articles);
            Integer articleid = mapper.selectMaxArticleid();
            //2.查看提交的label是否存在，没有则创建新的label,
            List<String> submit = article.getLabels();
            for (String label : submit) {
                Integer labelid = mapper.selectLabelIdByName(label);
                if (labelid == null) {
                    mapper.insertLabel(label);
                    labelid = mapper.selectMaxLabelid();
                }
                //3.把label插入article_label中
                mapper.insertArticleAndLabel(articleid, labelid);
            }


        }catch (Exception e){
            e.printStackTrace();
            return new Results<>(false, "发布失败！");
        }
        return new Results<>(true, "");
    }
}
