package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.WalkAgreement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WalkAgreementMapper extends BaseMapper<WalkAgreement> {
    @Select("SELECT * FROM WalkAgreements WHERE walker_id = #{walker_id}")
    List<WalkAgreement> findByWalker(String walker_id);

}
