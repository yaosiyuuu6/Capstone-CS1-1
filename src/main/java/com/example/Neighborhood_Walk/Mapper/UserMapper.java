package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM Users WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM Users WHERE address_id = #{addressId}")
    User findByAddressId(String addressId);

}
