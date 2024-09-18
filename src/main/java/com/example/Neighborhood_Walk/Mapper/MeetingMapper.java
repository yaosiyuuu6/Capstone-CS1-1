package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.PreMeeting;
import com.example.Neighborhood_Walk.entity.WalkerProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MeetingMapper extends BaseMapper<PreMeeting> {
    @Select("SELECT * FROM PreMeeting WHERE application_id = #{id}")
    PreMeeting findById(String id);

    @Update("UPDATE PreMeeting SET meeting_date=#{meetingDate}, " +
            "meeting_time=#{meetingTime}, location_id=#{locationId}, " +
            "status=#{status} " +
            "WHERE application_id=#{applicationId}")
    void update(PreMeeting preMeeting);



}
