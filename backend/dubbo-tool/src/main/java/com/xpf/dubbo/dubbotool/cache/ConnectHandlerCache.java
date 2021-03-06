package com.xpf.dubbo.dubbotool.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Constants;
import com.xpf.dubbo.dubbotool.dto.ZookeeperDTO;
import com.xpf.dubbo.dubbotool.handler.ConnectHandler;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
@Component
public class ConnectHandlerCache {

    @Value("${zk.connect.timeout}")
    private String zkConnectTimeOut;

    private final Map<String, ConnectHandler> map = new ConcurrentHashMap<>();

    public ConnectHandler getConnectHandler(ZookeeperDTO dto)
        throws NoSuchFieldException, IllegalAccessException {
        //不加分组是为了第一次连接zk后查询有哪些节点
        //选择分组后，是为了lookup查找对应节点下接口提供者url信息，不然在默认dubbo分组下查不到提供者url信息
        ConnectHandler connectHandler = map.get(dto.toString());
        if (connectHandler == null) {
            Map<String, String> params = new HashMap<>();
            params.put(Constants.GROUP_KEY, dto.getGroup());
            params.put(Constants.TIMEOUT_KEY, zkConnectTimeOut);
            connectHandler = new ConnectHandler(dto.getHost(), dto.getPort(), params);
            connectHandler.doConnect();
            map.put(dto.toString(), connectHandler);
        }
        return connectHandler;
    }
}
