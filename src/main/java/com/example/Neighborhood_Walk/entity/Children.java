package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Children")
public class Children {

    @TableId(type = IdType.ASSIGN_UUID)
    private String childrenId;

    private String parentsId;

    private String name;

    private String gender;

    private Integer age;

    private String description;
}

