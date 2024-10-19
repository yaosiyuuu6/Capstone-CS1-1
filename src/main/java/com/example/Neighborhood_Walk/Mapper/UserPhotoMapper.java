package com.example.Neighborhood_Walk.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.entity.UserPhoto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserPhotoMapper extends BaseMapper<UserPhoto> {

    @Insert("INSERT INTO UserPhotos (photo_id, user_id, photo_url) VALUES (#{photoId}, #{userId}, #{photoUrl})")
    void insertUserPhoto(UserPhoto userPhoto);

    @Select("SELECT * FROM UserPhotos WHERE user_id = #{userId}")
    List<UserPhoto> findPhotosByUserId(String userId);

    @Delete("DELETE FROM UserPhotos WHERE user_id = #{userId}")
    void deleteByUserId(String userId);
}

