package com.nbkj.bootjdbctemplate.controller;

import java.util.HashMap;
import java.util.Map;

import com.nbkj.bootjdbctemplate.entity.User;
import com.nbkj.bootjdbctemplate.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController 2019年5月7日15:06:20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public Map<String, Object> addUser() {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = new User();
            user.setAge(20);
            user.setUsername("lyb");
            user.setPassword("hsj");
            userService.addUser(user);
            result.put("code", 200);
            result.put("message", "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "添加失败!");
        }
        return result;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.deleteId(id);
            result.put("code", 200);
            result.put("message", "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "删除失败!");
        }
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, Object> queryById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.findById(id);
            result.put("code", 200);
            result.put("data", user);
            result.put("message", "查询成功!");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "查询失败!");
        }
        return result;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = new User();
            user.setAge(20);
            user.setPassword("zs");
            user.setUsername("lyb");
            user.setId(id);
            userService.updateUser(user);
            result.put("code", 200);
            result.put("message", "更新成功!");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "更新失败!");
        }
        return result;
    }
}