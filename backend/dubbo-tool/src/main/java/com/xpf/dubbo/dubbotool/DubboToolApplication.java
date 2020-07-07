package com.xpf.dubbo.dubbotool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@SpringBootApplication
@Slf4j
public class DubboToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboToolApplication.class, args);
        log.debug("dubbo-tool application start success");
    }

}
