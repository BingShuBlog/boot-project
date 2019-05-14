package com.nbkj.bootbaseconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * ConfigBean 属性比较多的时候封装类
 */
@Data
@ConfigurationProperties(prefix="bingshu.blog")
@Component
public class ConfigBean {
    private String name;
    private String title;
}