package com.nbkj.bootjdbctemplate.dao;

import java.util.List;
import java.util.Map;

import com.nbkj.bootjdbctemplate.entity.User;

/**
 * UserDao
 */
public interface UserDao {
    int add(User user);

    int update(User user);

    int deleteById(String id);

    List<Map<String, Object>> queryUserByListMap();

    User queryUserById(Long id);
}