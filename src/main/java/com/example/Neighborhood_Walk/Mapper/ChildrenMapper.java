package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Children;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChildrenMapper extends BaseMapper<Children> {
    // 可以根据需要添加额外的自定义查询方法
}

