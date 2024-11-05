package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.dto.UserDto;
import com.example.Neighborhood_Walk.entity.Address;
import com.example.Neighborhood_Walk.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM Users WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM Users WHERE phone_number = #{phone}")
    User findByPhone(String phone_number);

    @Select("SELECT * FROM Users WHERE address_id = #{addressId}")
    User findByAddressId(String addressId);

    @Select("SELECT code FROM verification_codes WHERE email = #{email}")
    String getVerificationCodeByEmail(@Param("email") String email);

    @Update("UPDATE users SET status = #{status} WHERE email = #{email}")
    void updateUserStatus(@Param("email") String email, @Param("status") boolean status);

    //get all users which are walkers
    @Select("SELECT * FROM Users WHERE user_type IN ('Walker', 'Both')")
    List<User> getAllWalkers();


    //get all users which are parent
    @Select("SELECT * FROM Users WHERE user_type IN ('Parent', 'Both')")
    List<User> getAllParents();


    //get all users which are admin
    @Select("SELECT * FROM Users WHERE user_type = 'Admin'")
    List<User> getAllAdmins();

    //get all users which are both walker and parent
    @Select("SELECT * FROM Users WHERE user_type = 'Both'")
    List<User> getAllBoth();

    @Select("SELECT a.* " +
            "FROM Users u " +
            "JOIN Addresses a ON u.address_id = a.address_id " +
            "WHERE u.user_id = #{userId}")
    Address findAddressByUserId(@Param("userId") String userId);

    @Select("<script>"
            + "SELECT u.user_id AS userId, u.first_name AS name, u.last_name AS lastName, u.description, u.address_id AS addressId, "
            + "ST_Distance_Sphere(POINT(#{longitude}, #{latitude}), POINT(a.longitude, a.latitude)) AS distance, "
            + "TIMESTAMPDIFF(YEAR, u.date_of_birth, CURDATE()) AS age "
            + "FROM Users u "
            + "JOIN Addresses a ON u.address_id = a.address_id "
            + "WHERE u.address_id IN "
            + "<foreach item='addressId' collection='addressIds' open='(' separator=',' close=')'>"
            + "#{addressId}"
            + "</foreach> "
            + "AND u.user_type = 'walker' "
            + "</script>")
    List<UserDto> findWalkersByAddressIds(@Param("addressIds") List<String> addressIds,
                                            @Param("latitude") double latitude,
                                            @Param("longitude") double longitude);


    @Select("<script>"
            + "SELECT u.user_id AS userId, u.first_name AS name, u.last_name AS lastName, u.description, u.address_id AS addressId, "
            + "ST_Distance_Sphere(POINT(#{longitude}, #{latitude}), POINT(a.longitude, a.latitude)) AS distance, "
            + "TIMESTAMPDIFF(YEAR, u.date_of_birth, CURDATE()) AS age "
            + "FROM Users u "
            + "JOIN Addresses a ON u.address_id = a.address_id "
            + "WHERE u.address_id IN "
            + "<foreach item='addressId' collection='addressIds' open='(' separator=',' close=')'>"
            + "#{addressId}"
            + "</foreach> "
            + "AND u.user_type = 'walker' "
            + "</script>")
    List<UserDto> findParentsByAddressIds(@Param("addressIds") List<String> addressIds,
                                            @Param("latitude") double latitude,
                                            @Param("longitude") double longitude);







}
