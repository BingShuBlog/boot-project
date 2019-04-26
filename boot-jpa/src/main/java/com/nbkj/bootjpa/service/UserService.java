package com.nbkj.bootjpa.service;

import java.util.List;
import java.util.Map;

import com.nbkj.bootjpa.entity.User;

/**
 * UserService
 */
public interface UserService {
    User findByUserName(String username);

    User save(User user);

    List<User> findAll();

    void delete(Long id);

    void delete(User user);

    List<Object[]> selectUserMessage();

    List<Map<String,Object>> selectUserMessageForMap();
}