package com.nbkj.bootjdbctemplate.dao.impl;

import java.util.List;
import java.util.Map;

import com.nbkj.bootjdbctemplate.dao.UserDao;
import com.nbkj.bootjdbctemplate.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserDaoImpl 2019年5月7日10:26:40
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public int add(User user) {
        StringBuffer sql = new StringBuffer("insert into t_user(username,password,age) ");
        sql.append("values(:username,:password,:sex) ");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                JdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.update(sql.toString(), new BeanPropertySqlParameterSource(user));

    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryUserByListMap() {
        return null;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

}