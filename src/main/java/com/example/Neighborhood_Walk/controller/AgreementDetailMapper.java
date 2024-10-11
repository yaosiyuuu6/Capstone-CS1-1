package com.example.Neighborhood_Walk.Mapper;

import com.example.Neighborhood_Walk.entity.AgreementDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AgreementDetailMapper extends BaseMapper<AgreementDetail>{
    @Select("select * from AgreementDetails_View where agreement_id = #{agreement_id}")
    AgreementDetail getAgreementDetail(String agreement_id);
}
