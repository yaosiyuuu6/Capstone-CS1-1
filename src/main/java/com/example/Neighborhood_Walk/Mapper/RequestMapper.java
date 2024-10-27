package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.dto.RequestDto;

import com.example.Neighborhood_Walk.entity.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestMapper extends BaseMapper<Request> {

    @Select("SELECT * FROM WalkRequests WHERE parent_id = #{id} and Status != 'Canceled'")
    List<Request> findById(String id);

    @Update("UPDATE WalkRequests SET child_id=#{childId}, pickup_address_id=#{pickupAddress}, " +
            "dropoff_address_id=#{dropoffAddress}, walk_date=#{walkDate}, walk_time=#{walkTime}, " +
            "recurrence=#{recurrence}, description=#{description} WHERE request_id=#{requestId}")
    void update(Request request);

    @Select("<script>"
            + "SELECT r.request_id AS requestId, r.parent_id AS parentId, r.child_id AS childId, "
            + "r.pickup_address_id AS pickupAddressId, r.dropoff_address_id AS dropoffAddressId, "
            + "r.walk_date AS walkDate, r.walk_time AS walkTime, r.recurrence AS recurrence, "
            + "r.status AS status, r.description AS description, "
            + "ST_Distance_Sphere(POINT(#{longitude}, #{latitude}), POINT(a.longitude, a.latitude)) AS distance "
            + "FROM WalkRequests r "
            + "JOIN Addresses a ON r.pickup_address_id = a.address_id "
            + "WHERE r.pickup_address_id IN "
            + "<foreach item='addressId' collection='addressIds' open='(' separator=',' close=')'>"
            + "#{addressId}"
            + "</foreach> "
            + "</script>")
    List<RequestDto> findRequestsByAddressIds(@Param("addressIds") List<String> addressIds,
                                              @Param("latitude") double latitude,
                                              @Param("longitude") double longitude);


}

