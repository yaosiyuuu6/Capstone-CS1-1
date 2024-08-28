package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Application;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
    // 你可以在这里添加自定义查询方法
}
