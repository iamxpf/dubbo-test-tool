package com.xpf.dubbo.dubbotool.dto;

import lombok.Data;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 * @taskId:
 */
@Data
public class ZookeeperDTO {

    private String host;
    private int port;
    private String group;

    public ZookeeperDTO(String host, int port) {
        this(host, port, null);
    }

    public ZookeeperDTO(String host, int port, String group) {
        this.host = host;
        this.port = port;
        this.group = group;
    }
}
