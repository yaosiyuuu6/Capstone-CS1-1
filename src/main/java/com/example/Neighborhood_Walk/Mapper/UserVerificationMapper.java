package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.UserVerification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserVerificationMapper extends BaseMapper<UserVerification> {
    // Find the verification record by userId and verificationType
    @Select("SELECT * FROM UserVerification WHERE user_id = #{userId} AND verification_type = #{verificationType}")
    UserVerification findByUserIdAndType(@Param("userId") String userId, @Param("verificationType") String verificationType);
}
