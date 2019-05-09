package com.nbkj.bootjdbctemplate.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.nbkj.bootjdbctemplate.dao.UserDao;
import com.nbkj.bootjdbctemplate.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        sql.append("values(:username,:password,:age) ");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                JdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.update(sql.toString(), new BeanPropertySqlParameterSource(user));

    }

    @Override
    public int update(User user) {
        String sql = "update t_user set username=?,password=?,age=?";
        Object[] param = { user.getUsername(), user.getPassword(), user.getAge() };
        int[] paramTypes = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        return JdbcTemplate.update(sql, param, paramTypes);

    }

    @Override
    public int deleteById(Long id) {
        String sql = "delete from t_user where id=?";
        Object[] param = { id };
        int[] paramTypes = { Types.BIGINT };
        return JdbcTemplate.update(sql, param, paramTypes);
    }

    @Override
    public List<Map<String, Object>> queryUserByListMap() {
        String sql = "select * from t_user";
        return JdbcTemplate.queryForList(sql);
    }

    @Override
    public User queryUserById(Long id) {
        String sql = "select * from t_user where id=?";
        Object[] param = { id };
        int[] paramTypes = { Types.BIGINT };
        List<User> query = JdbcTemplate.query(sql, param, paramTypes, (RowMapper<User>) (rs, rowNum) -> {
		   User  user=new User();
		   user.setId(rs.getLong("id"));
		   user.setAge(rs.getInt("age"));
		   user.setPassword(rs.getString("password"));
           user.setUsername(rs.getString("username"));
		   return user;
        });
        if(query!=null&&!query.isEmpty() ){
         return query.get(0);
        }else{
            return null;
        }
    }

}