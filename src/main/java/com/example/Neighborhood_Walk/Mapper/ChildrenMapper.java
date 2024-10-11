package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Children;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChildrenMapper extends BaseMapper<Children> {
    // 可以根据需要添加额外的自定义查询方法
    // get children by parent id
    @Select("SELECT * FROM Children WHERE parents_id = #{parentId}")
    List<Children> findChildrenByParentId(String parentId);


}

