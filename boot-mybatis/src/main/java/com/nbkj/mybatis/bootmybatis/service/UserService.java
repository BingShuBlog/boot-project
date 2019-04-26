package com.nbkj.mybatis.bootmybatis.service;

import java.util.List;

import com.nbkj.mybatis.bootmybatis.entity.User;

/**
 * UserService User业务逻辑接口
 */
public interface UserService {
    
    /**
     * 查询所有User
     * @return
     */
    List<User> findAll();
}