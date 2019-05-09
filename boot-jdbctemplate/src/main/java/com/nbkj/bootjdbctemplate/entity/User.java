package com.nbkj.bootjdbctemplate.entity;

import lombok.Data;

/**
 * User用户类
 * 2019年5月7日10:20:34
 */
@Data
public class User {
   
    private Long id;

    private String username;

    private String password;

    private Integer age;
    
}