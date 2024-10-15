package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.WalkAgreement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WalkAgreementMapper extends BaseMapper<WalkAgreement> {
    // 这里可以添加自定义的查询方法
    @Select("SELECT * FROM WalkAgreements WHERE walker_id = #{walker_id}")
    List<WalkAgreement> findByWalker(String walker_id);

}
