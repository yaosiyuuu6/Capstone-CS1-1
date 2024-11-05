package com.example.Neighborhood_Walk.Mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Neighborhood_Walk.dto.ShareRequestWithDescription;
import com.example.Neighborhood_Walk.entity.ShareRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShareRequestMapper extends BaseMapper<ShareRequest> {

    @Select("SELECT sr.*, wr.description " +
            "FROM ShareRequest sr " +
            "JOIN WalkRequest wr ON sr.requestId = wr.id " +
            "WHERE sr.walkerId = #{walkerId} order by sr.updatedAt desc")
    List<ShareRequestWithDescription> getRequestsByWalkerIdWithDescription(@Param("walkerId") String walkerId);
}
