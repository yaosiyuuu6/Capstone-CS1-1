package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.WalkAgreement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalkAgreementMapper extends BaseMapper<WalkAgreement> {
    // 这里可以添加自定义的查询方法
}
