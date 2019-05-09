package com.nbkj.bootjdbctemplate.service.impl;

import com.nbkj.bootjdbctemplate.dao.UserDao;
import com.nbkj.bootjdbctemplate.entity.User;
import com.nbkj.bootjdbctemplate.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl 2019年5月7日14:45:51
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        return userDao.queryUserById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteId(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public User addUser(User user) {
        userDao.add(user);
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public int updateUser(User user) {
        return userDao.update(user);
    }

}