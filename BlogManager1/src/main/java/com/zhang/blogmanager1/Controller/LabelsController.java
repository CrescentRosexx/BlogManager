package com.zhang.blogmanager1.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.Results;
import com.zhang.blogmanager1.entity.label.Labels;
import com.zhang.blogmanager1.entity.label.LabelsInfo;
import com.zhang.blogmanager1.entity.label.LabelsReq;
import com.zhang.blogmanager1.mapper.LabelsMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/Label")
public class LabelsController {
    @Resource
    LabelsMapper mapper;

    //获取所有标签列表
    @PostMapping("list")
    public Results<Page<LabelsInfo>> findPage(LabelsReq labelsReq) {
        System.out.println(labelsReq.toString());

        Page<LabelsInfo> page, labels;
        try {
            page = new Page<>(labelsReq.getPage(), labelsReq.getLimit());
            long startTime = System.currentTimeMillis();     //获取开始时间
            labels = mapper.selectByPage(page, labelsReq);
            long overTime = System.currentTimeMillis();      //获取结束时间
            System.out.println("标签查询时间为："+(overTime-startTime)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results<>(false, "获取标签列表失败！");
        }

        return new Results<>(true, "", labels);
    }

    //删除标签
    @DeleteMapping("/delete")
    public Results userDelete(Integer labelid) {
        System.out.println("删除标签" + labelid);
        Results results;
        try {
            mapper.labelDelete(labelid);
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "删除失败！");
        }

        return results;
    }

    //批量删除标签
    @DeleteMapping("/batchDelete")
    public Results userBatchDelete(Integer[] ids) {

        Results results;
        System.out.println("批量删除用户" + Arrays.toString(ids));
        try {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("idList", Arrays.asList(ids));
            mapper.labelBatchDelete(params);
            results = new Results(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            results = new Results(false, "删除失败！");
        }

        return results;
    }

    //修改标签名称
    @PostMapping("update")
    public Results updateLabel(Labels labels) {
        System.out.println("修改标签：" + labels.toString());

        try {
            mapper.updateLabel(labels);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "更新失败！");
        }
        return new Results(true, "");
    }
    //添加标签
    @PostMapping("add")
    public Results addLabel(String labelname) {
        System.out.println("添加标签：" + labelname);

        try {
            mapper.addLabel(labelname);
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "添加失败！");
        }
        return new Results(true, "");
    }
}
