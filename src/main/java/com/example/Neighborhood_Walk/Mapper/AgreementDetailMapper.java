package com.example.Neighborhood_Walk.Mapper;

import com.example.Neighborhood_Walk.entity.AgreementDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.WalkerRating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AgreementDetailMapper extends BaseMapper<AgreementDetail>{
    @Select("select * from AgreementDetails_View where agreement_id = #{agreement_id}")
    AgreementDetail getAgreementDetail(String agreement_id);

    @Select("select * from AgreementDetails_View where walker_id = #{walker_id}")
    List<AgreementDetail> getComments(String walker_id);
    @Select("select * from AgreementDetails_View where parent_id = #{parent_id}")
    List<AgreementDetail> getAgreements(String parent_id);
    @Select("select * from WalkerRating_View where walker_id = #{walker_id}")
    WalkerRating getRating(String walker_id);
    @Select("select * from AgreementDetails_View")
    List<AgreementDetail> getAllComments();
}
