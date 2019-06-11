package com.nbkj.bootredis.controller;

import com.nbkj.bootredis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hushaojun
 * @Date :  2019/6/11
 * @Decription :
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/set")
    public Map<String, Object> set(String key, String value) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean data = redisUtils.set(key, value);
            result.put("data", data);
            result.put("code", 200);
            result.put("message", "设置字段设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @GetMapping("/get/{value}")
    public Map<String, Object> get(@PathVariable("value") String value) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object date = redisUtils.get(value);
            result.put("data", date);
            result.put("code", 200);
            result.put("message", "读取成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", e.getMessage());
        }
        return result;
    }

}
