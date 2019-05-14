package com.nbkj.bootaop.controller;

import com.nbkj.bootaop.annotation.MyLog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AspectTestController 2019年5月13日15:05:41
 */
@RequestMapping("/aspect")
@RestController
public class AspectTestController {

    @MyLog("执行方法一")
    @GetMapping("/one")
    public void methodOne(String name) {

    }

    @MyLog("执行方法二")
    @GetMapping("/two")
    public void methodTwo() throws InterruptedException {
        Thread.sleep(2000);
    }

    @MyLog("执行方法三")
    @GetMapping("/three")
    public void methodThree(String name, String age) {

    }
}