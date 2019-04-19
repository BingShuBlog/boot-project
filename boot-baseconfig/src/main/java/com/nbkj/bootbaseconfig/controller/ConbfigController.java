package com.nbkj.bootbaseconfig.controller;

import com.nbkj.bootbaseconfig.config.ConfigBean;
import com.nbkj.bootbaseconfig.config.PropertiesBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConbfigController 用于显示配置文件测试
 */
@RestController
@RequestMapping("/config")
public class ConbfigController {

    @Autowired
    private PropertiesBean propertiesBean;

    @Autowired
    private ConfigBean configBean;

    @GetMapping("/showConfig")
    public String showConfig() {
        return propertiesBean.getName() + "-------" + propertiesBean.getTitle();
    }
   
    @GetMapping("/showConfig2")
    public String showConfig2() {
        return configBean.getName() + "-------" + configBean.getTitle();
    }
}