package com.nbkj.mybatis.bootmybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbkj.mybatis.bootmybatis.entity.User;
import com.nbkj.mybatis.bootmybatis.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int i = userService.deleteUser(id);
            result.put("code", 200);
            if (i > 0) {
                result.put("message", "删除成功！");
            } else {
                result.put("message", "删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "服务器异常");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            User newUser = userService.addUser(user);
            result.put("code", 200);
            result.put("message", "添加成功！");
            result.put("data", newUser);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "服务器异常");
        }
        return result;
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@RequestParam("nickName") String nickName, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            User updateUser = userService.updateUserNickName(nickName, id);
            result.put("code", 200);
            result.put("message", "修改成功！");
            result.put("data", updateUser);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "服务器异常");
        }
        return result;
    }

    @GetMapping("/selectByUserName")
    public Map<String, Object> update(@RequestParam("userName") String userName) {
        Map<String, Object> result = new HashMap<>();
        try {
            User selectUser= userService.selectUserByUserName(userName);
            result.put("code", 200);
            result.put("message", "查询成功！");
            result.put("data", selectUser);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "服务器异常");
        }
        return result;
    }
}