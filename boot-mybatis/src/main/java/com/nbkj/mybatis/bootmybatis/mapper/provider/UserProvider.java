package com.nbkj.mybatis.bootmybatis.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * UserProvider 通过Provider写复杂动态sql
 */
public class UserProvider {

    public String selectAll() {
        return new SQL() {
            {
                SELECT(" * ");
                FROM("t_user");
            }
        }.toString();
    }
}