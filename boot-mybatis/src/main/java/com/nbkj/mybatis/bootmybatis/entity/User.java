package com.nbkj.mybatis.bootmybatis.entity;

import java.sql.Timestamp;

import lombok.Data;

/**
 * User 实体类
 */
@Data
public class User {
    private Long id;
    private String userName;
    private String passWord;
    private String email;
    private String nickName;
    private Timestamp regTime;
    private Integer age;
}