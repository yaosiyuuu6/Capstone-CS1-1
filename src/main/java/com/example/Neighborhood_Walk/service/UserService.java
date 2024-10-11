package com.example.Neighborhood_Walk.service;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;

import com.example.Neighborhood_Walk.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AddressMapper addressMapper;  // UserMapper用于执行数据库操作

    // 根据userId获取用户的地址信息，包括经纬度
    public Address getUserAddressByUserId(String userId) {
        return addressMapper.findAddressByUserId(userId);
    }
}
