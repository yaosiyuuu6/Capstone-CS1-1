package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM Users WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM Users WHERE address_id = #{addressId}")
    User findByAddressId(String addressId);

    @Select("SELECT code FROM verification_codes WHERE email = #{email}")
    String getVerificationCodeByEmail(@Param("email") String email);

    @Update("UPDATE users SET status = #{status} WHERE email = #{email}")
    void updateUserStatus(@Param("email") String email, @Param("status") boolean status);


}
