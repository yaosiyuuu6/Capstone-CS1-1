package com.example.Neighborhood_Walk.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.Neighborhood_Walk.entity.Location;

@Mapper
public interface LocationMapper {

    @Insert("INSERT INTO Location (walker_id, latitude, longitude, accuracy, location_time) " +
            "VALUES (#{userId}, #{latitude}, #{longitude}, #{accuracy}, CURRENT_TIMESTAMP)")
    void insertLocation(Location location);
}
