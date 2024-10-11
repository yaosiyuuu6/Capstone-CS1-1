package com.example.Neighborhood_Walk.controller;

import com.example.Neighborhood_Walk.entity.Children;
import com.example.Neighborhood_Walk.Mapper.ChildrenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/children")
public class ChildrenController {

    @Autowired
    private ChildrenMapper childrenMapper;

    /**
     * 获取所有孩子记录
     * Get a list of all children records
     */
    @GetMapping("/list")
    public List<Children> list() {
        return childrenMapper.selectList(null);
    }

    /**
     * 根据ID获取孩子信息
     * Get a child's information by ID
     * @param id 孩子的ID / Child's ID
     * @return 孩子的详细信息 / Child details
     */
    @GetMapping("/{id}")
    public Children getChildById(@PathVariable("id") String id) {
        return childrenMapper.selectById(id);
    }

    /**
     * 添加新孩子，生成随机UUID作为children_id
     * Add a new child record with a randomly generated UUID for children_id
     * @param children 孩子对象 / Child object
     * @return 操作结果 / Operation result
     */
    @PostMapping("/add")
    public String addChild(@RequestBody Children children) {
        // 生成随机的UUID作为children_id
        children.setChildrenId(UUID.randomUUID().toString());

        int result = childrenMapper.insert(children);
        return result > 0 ? "Child added successfully" : "Failed to add child";
    }

    /**
     * 更新孩子信息
     * Update an existing child's information
     * @param children 孩子对象 / Child object
     * @return 操作结果 / Operation result
     */
    @PutMapping("/update")
    public String updateChild(@RequestBody Children children) {
        int result = childrenMapper.updateById(children);
        return result > 0 ? "Child updated successfully" : "Failed to update child";
    }

    /**
     * 根据ID删除孩子记录
     * Delete a child record by ID
     * @param id 孩子的ID / Child's ID
     * @return 操作结果 / Operation result
     */
    @DeleteMapping("/delete/{id}")
    public String deleteChild(@PathVariable("id") String id) {
        int result = childrenMapper.deleteById(id);
        return result > 0 ? "Child deleted successfully" : "Failed to delete child";
    }

    /**
     * 根据家长ID获取孩子信息
     * Get a child's information by parent ID
     * @param parentId 家长的ID / Parent's ID
     * @return 孩子的详细信息 / Child details
     */
    @GetMapping("/parent/{parentId}")
    public List<Children> getChildrenByParentId(@PathVariable("parentId") String parentId) {
        return childrenMapper.findChildrenByParentId(parentId);
    }
}