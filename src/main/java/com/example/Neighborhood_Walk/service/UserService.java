package com.example.Neighborhood_Walk.service;

import com.example.Neighborhood_Walk.Mapper.AddressMapper;

import com.example.Neighborhood_Walk.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AddressMapper addressMapper;

    // Get the user's address information, including latitude and longitude, based on userId
    public Address getUserAddressByUserId(String userId) {
        return addressMapper.findAddressByUserId(userId);
    }
}
