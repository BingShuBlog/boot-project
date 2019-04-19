package com.nbkj.bootbaseconfig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * PropertiesBean 用来存放配置信息
 */
@Component
@Data
public class PropertiesBean {

    @Value("${bingshu.blog.name}")
    private String name;

    @Value("${bingshu.blog.title}")
    private String title;
}