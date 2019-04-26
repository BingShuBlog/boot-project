package com.nbkj.bootjpa.service.impl;

import java.util.List;
import java.util.Map;

import com.nbkj.bootjpa.entity.User;
import com.nbkj.bootjpa.repository.UserRepository;
import com.nbkj.bootjpa.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl
 * 
 * @date 2019年4月8日14:00:03
 * @author BingShu
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过用户名查询用户
     */
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 保存信息
     */
    @Override
    @Transactional(readOnly = false)
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * 查询信息返回Object[]
     */
    @Override
    public List<Object[]> selectUserMessage() {
        return userRepository.selectAllUserAndAddress();
    }
   
    /**
     * 查询信息返回map
     */
    @Override
    public List<Map<String, Object>> selectUserMessageForMap() {
        return userRepository.selectAllUserForMap();
    }

}