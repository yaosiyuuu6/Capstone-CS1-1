package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Request;
import com.example.Neighborhood_Walk.entity.WalkerProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface ProfileMapper extends BaseMapper<WalkerProfile> {
    @Select("SELECT * FROM WalkerProfile WHERE walker_id = #{id}")
    WalkerProfile findById(String id);

    @Update("UPDATE WalkerProfile SET working_with_children_check=#{workingWithChildrenCheck}, " +
            "available_dates_times=#{availableDatesTimes}, skills=#{skills} " +
            "WHERE walker_id=#{walkerId}")
    void update(WalkerProfile walkerProfile);


}
