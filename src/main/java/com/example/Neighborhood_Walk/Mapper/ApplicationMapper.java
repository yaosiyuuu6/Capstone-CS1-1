package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
    @Select("SELECT * FROM WalkerApplications WHERE walker_id = #{walker_id}")
    List<Application> findByWalker(String walker_id);

    @Select("SELECT * FROM WalkerApplications WHERE request_id = #{request_id}")
    List<Application> findByRequest(String request_id);
}
