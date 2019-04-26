package com.nbkj.mybatis.bootmybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbkj.mybatis.bootmybatis.entity.User;
import com.nbkj.mybatis.bootmybatis.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController User交互层
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<User> findAll = userService.findAll();
            result.put("code", 200);
            result.put("message", "查询成功！");
            result.put("data", findAll);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "服务器异常");
        }
        return result;
    }
}