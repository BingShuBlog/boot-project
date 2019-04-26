package com.nbkj.bootjpa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nbkj.bootjpa.entity.User;
import com.nbkj.bootjpa.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * 
 * @date 2019年4月8日14:27:25
 * @author BingShu
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findByUserName/{username}")
    public Map<String, Object> findUserByUserName(@PathVariable("username") String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.findByUserName(username);
        result.put("data", user);
        result.put("code", 200);
        result.put("message", "查询成功！");
        return result;
    }

    /**
     * 查询用户信息
     * 
     * @return
     */
    @GetMapping("/getAllUserMessage")
    public Map<String, Object> findAllUserMessage() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Object[]> selectUserMessage = userService.selectUserMessage();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", selectUserMessage);
        } catch (Exception e) {
            result.put("code", 500);
            e.printStackTrace();
            result.put("message", "服务器异常");
        }
        return result;
    }

    /**
     * 查询用户信息返回map
     * 
     * @return
     */
    @GetMapping("/findAllUserMessageForMap")
    public Map<String, Object> findAllUserMessageForMap() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> maps=    userService.selectUserMessageForMap();
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", maps);
        } catch (Exception e) {
            result.put("code", 500);
            e.printStackTrace();
            result.put("message", "服务器异常");
        }
        return result;
    }
}