package com.nbkj.bootjdbctemplate.service;

import com.nbkj.bootjdbctemplate.entity.User;

/**
 * UserService
 * 2019年5月7日14:45:123
 */
public interface UserService {
      User findById(Long id);

      int deleteId(Long id);

      User addUser(User user);

      int updateUser(User user);
}