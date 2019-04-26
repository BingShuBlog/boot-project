package com.nbkj.bootjpa.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * User 用户
 */
@Entity
@Table(name = "t_user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "pass_word")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "reg_time")
    private Timestamp createTime;

    @Column(name = "email")
    private String email;

    @Column(name = "nick_name")
    private String nickName;
}