package com.nbkj.mybatis.bootmybatis.service.Impl;

import java.util.List;

import com.nbkj.mybatis.bootmybatis.entity.User;
import com.nbkj.mybatis.bootmybatis.mapper.UserMapper;
import com.nbkj.mybatis.bootmybatis.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl
 */
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectAllProvider();
    }

    @Override
    @Transactional(readOnly = false)
    public User addUser(User user) {
        userMapper.addUser(user);
        return userMapper.selectUserById(user.getId());

    }

    @Transactional(readOnly = false)
    @Override
    public int deleteUser(Long id) {
        return userMapper.deleteById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public User updateUserNickName(String nickName, Long id) {
        userMapper.updateNickName(nickName, id);
        return userMapper.selectUserById(id);
    }

    @Override
    public User selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

}