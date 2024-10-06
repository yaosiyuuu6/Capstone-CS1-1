package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.ShareLocation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShareLocationMapper extends BaseMapper<ShareLocation> {

    @Update("UPDATE ShareLocation SET tracking_data = #{trackingData}, updated_at = CURRENT_TIMESTAMP WHERE agreement_id = #{agreementId}")
    void updateTrackingData(String agreementId, String trackingData);

    @Insert("INSERT INTO ShareLocation (agreement_id, walker_id, tracking_data, updated_at) VALUES (#{agreementId}, #{walkerId}, #{trackingData}, NOW())")
    void insertLocationData(String agreementId, String walkerId, String trackingData);

}
