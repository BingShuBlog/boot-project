package com.nbkj.bootjpa.repository;

import java.util.List;
import java.util.Map;

import com.nbkj.bootjpa.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * UserRepository User持久层
 * 
 * @author : BingShu
 * @date : 2019年4月8日13:48:29
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 通过用户名查询用户
     * 
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询所有用户的用户信息和地址 返回类型为数组方式查询但是sql中不能有重复的列
     * 
     * @return
     */
    @Query(value = "SELECT tu.* ,ta.province,ta.city,ta.street from t_user tu LEFT OUTER JOIN t_address ta on tu.id =ta.user_id", nativeQuery = true)
    List<Object[]> selectAllUserAndAddress();
    
    /**
     * 返回类型为map查询所有数据
     * @return
     */
    @Query(value = "select new map(tu,ta) from User tu LEFT OUTER JOIN Address ta on tu.id =ta.userId")
    List<Map<String, Object>> selectAllUserForMap();
}