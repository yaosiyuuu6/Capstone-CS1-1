package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    @Select("SELECT * FROM Addresses WHERE address_line1 = #{addressLine1} AND address_line2 = #{addressLine2} AND city = #{city} AND state = #{state} AND postcode = #{postcode} AND country = #{country}")
    Address findByDetails(String addressLine1, String addressLine2, String city, String state, String postcode, String country);
}
