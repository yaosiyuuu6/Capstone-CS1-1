package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.UserVerification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVerificationMapper extends BaseMapper<UserVerification> {
    // 这里可以添加自定义的查询方法
}
