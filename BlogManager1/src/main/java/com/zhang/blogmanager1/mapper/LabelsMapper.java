package com.zhang.blogmanager1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.blogmanager1.entity.label.Labels;
import com.zhang.blogmanager1.entity.label.LabelsInfo;
import com.zhang.blogmanager1.entity.label.LabelsReq;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface LabelsMapper extends BaseMapper<Labels> {
    //标签分页按条件查询
    Page<LabelsInfo> selectByPage(Page<LabelsInfo> page, @Param("req") LabelsReq labelsReq);
    //删除标签
    void labelDelete(@Param("labelid") Integer labelid);
    //批量删除标签
    void labelBatchDelete(Map<String, Object> params);
    //更新标签
    void updateLabel(@Param("req") Labels labels);
    //添加新标签
    void addLabel(String labelname);
}
