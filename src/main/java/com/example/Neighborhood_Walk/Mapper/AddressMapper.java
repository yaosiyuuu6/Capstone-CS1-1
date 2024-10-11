package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    @Select("SELECT * FROM Addresses WHERE address_line1 = #{addressLine1} AND address_line2 = #{addressLine2} AND city = #{city} AND state = #{state} AND postcode = #{postcode} AND country = #{country}")
    Address findByDetails(String addressLine1, String addressLine2, String city, String state, String postcode, String country);

    @Select("SELECT a.* \n" +
            "FROM Users u \n" +
            "JOIN Addresses a ON u.address_id = a.address_id \n" +
            "WHERE u.user_id = #{userId}\n")
    Address findAddressByUserId(@Param("userId") String userId);

    @Select("SELECT address_id FROM Addresses " +
            "WHERE ST_Distance_Sphere(POINT(#{longitude}, #{latitude}), POINT(longitude, latitude)) <= #{rangeInKm} * 1000")
    List<String> findNearbyAddressIds(@Param("latitude") BigDecimal latitude,
                                      @Param("longitude") BigDecimal longitude,
                                      @Param("rangeInKm") double rangeInKm);

    // 通过 address_id 查找地址
    @Select("SELECT * FROM Addresses WHERE address_id = #{addressId}")
    Address findAddressById(@Param("addressId") String addressId);
}

