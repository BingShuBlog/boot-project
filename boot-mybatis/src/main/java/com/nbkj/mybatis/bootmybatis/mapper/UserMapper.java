package com.nbkj.mybatis.bootmybatis.mapper;

import java.util.List;


import com.nbkj.mybatis.bootmybatis.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper mapper持久层
 */
@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    @Results(id = "user", value = { 
        @Result(property = "id",column = "id"),
        @Result(property = "userName",column = "user_name"),
        @Result(property = "passWord",column = "pass_word"),
        @Result(property = "email",column = "email"),
        @Result(property = "nickName",column = "nick_name"),
        @Result(property = "regTime",column = "reg_time"),
        @Result(property = "age",column = "age")
    })
    List<User>  findAll();
}