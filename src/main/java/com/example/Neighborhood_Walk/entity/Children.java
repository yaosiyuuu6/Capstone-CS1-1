package com.example.Neighborhood_Walk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Children")
public class Children {

    @TableId(type = IdType.ASSIGN_UUID)
    private String childrenId;  // 主键ID

    private String parentsId;   // 父母ID

    private String name;        // 孩子名字

    private String gender;      // 性别 ('male', 'female', 'prefer not to say')

    private Integer age;        // 年龄

    private String description; // 描述
}

