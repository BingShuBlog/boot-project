package com.nbkj.bootaop.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * MyLogDo 日志记录实体 2019年5月13日14:25:15
 */
@Data
@ToString
public class MyLogDo {
    private Integer id;
    private String username;
    private String operation;
    private Long time;
    private String method;
    private String params;
    private String ip;
    private Date createTime;
}