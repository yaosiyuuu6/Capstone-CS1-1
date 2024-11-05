package com.example.Neighborhood_Walk.service;
import com.example.Neighborhood_Walk.Mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    // Find the address IDs that meet the conditions based on the center latitude and longitude and range
    public List<String> getNearbyAddressIds(BigDecimal latitude, BigDecimal longitude, double rangeInKm) {
        return addressMapper.findNearbyAddressIds(latitude, longitude, rangeInKm);
    }
}

