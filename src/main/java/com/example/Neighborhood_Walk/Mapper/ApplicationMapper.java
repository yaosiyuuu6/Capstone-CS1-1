package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Application;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
    // 你可以在这里添加自定义查询方法
    @Select("SELECT * FROM WalkerApplications WHERE walker_id = #{walker_id}")
    Application findByWalker(String walker_id);

    @Select("SELECT * FROM WalkerApplications WHERE request_id = #{request_id}")
    Application findByRequest(String request_id);
}
