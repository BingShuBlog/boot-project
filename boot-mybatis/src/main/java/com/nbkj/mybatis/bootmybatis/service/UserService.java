package com.nbkj.mybatis.bootmybatis.service;

import java.util.List;

import com.nbkj.mybatis.bootmybatis.entity.User;

/**
 * UserService User业务逻辑接口
 */
public interface UserService {

  /**
   * 查询所有User
   * 
   * @return
   */
  List<User> findAll();

  /**
   * 添加User
   * 
   * @param user
   * @return
   */
  User addUser(User user);

  /**
   * 删除User
   * 
   * @param id
   * @return
   */
  int deleteUser(Long id);

  /**
   * 修改用户用户名
   * 
   * @param nickName
   * @param id
   * @return
   */
  User updateUserNickName(String nickName, Long id);

 
  /**
   * 通过UserName查询User
   * @param userName
   * @return
   */
  User selectUserByUserName(String userName);
}