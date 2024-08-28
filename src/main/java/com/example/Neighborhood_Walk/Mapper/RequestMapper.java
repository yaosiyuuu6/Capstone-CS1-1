package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Request;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RequestMapper extends BaseMapper<Request> {

    @Select("SELECT * FROM WalkRequests WHERE parent_id = #{id} and Status != 'Canceled'")
    List<Request> findById(String id);

    @Update("UPDATE WalkRequests SET child_name=#{childName}, pickup_address_id=#{pickupAddress}, " +
            "dropoff_address_id=#{dropoffAddress}, walk_date=#{walkDate}, walk_time=#{walkTime}, " +
            "recurrence=#{recurrence}, status=#{status}, description=#{description} WHERE request_id=#{requestId}")
    void update(Request request);
}

