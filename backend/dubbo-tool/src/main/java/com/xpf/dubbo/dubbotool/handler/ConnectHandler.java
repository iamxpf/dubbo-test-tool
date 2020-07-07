package com.xpf.dubbo.dubbotool.handler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistry;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.Constants;
import com.xpf.dubbo.dubbotool.constant.Const;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
public class ConnectHandler implements Serializable {
    private static final long serialVersionUID = -8337050479067738941L;

    private String protocol;
    private String host;
    private int port;
    private Map<String, String> parameters;

    private ZookeeperClient zkClient;
    private ZookeeperRegistry registry;

    public ConnectHandler(String host, int port) {
        this(Const.REGISTRY_PROTOCOL, host, port, null);
    }

    public ConnectHandler(String protocol, String host, int port) {
        this(protocol, host, port, null);
    }

    public ConnectHandler(String host, int port, Map<String, String> parameters) {
        this(Const.REGISTRY_PROTOCOL, host, port, parameters);
    }

    public ConnectHandler(String protocol, String host, int port, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.parameters = parameters;
    }

    public void doConnect() throws NoSuchFieldException, IllegalAccessException {
        CuratorZookeeperTransporter zookeeperTransporter = new CuratorZookeeperTransporter();
        URL url = new URL(protocol, host, port, parameters);
        registry = new ZookeeperRegistry(url, zookeeperTransporter);
        Field field = registry.getClass().getDeclaredField(Const.ZOOKEEPERREGISTRY_ZKCLIENT);
        field.setAccessible(true);
        zkClient = (ZookeeperClient) field.get(registry);
    }

    public List<String> getGroup() {
        return zkClient.getChildren(Const.ZOOKEEPER_ROOT).stream().sorted().collect(Collectors.toList());
    }

    public List<String> getInterfaces(String group) {
        return zkClient.getChildren(Const.ZOOKEEPER_ROOT + group).stream().sorted().collect(Collectors.toList());
    }

    public List<String> getMethod(String serviceUrl) {
        Map<String, String> param = new HashMap();
        param.put(Constants.INTERFACE_KEY, serviceUrl);
        URL zookeeper = new URL(protocol, host, port, param);
        List<URL> lookup = registry.lookup(zookeeper);
        Set<String> methodSet = new TreeSet<>();
        lookup.stream().forEach(e -> {
            String methods = e.getParameter("methods");
            List list = CollectionUtils.arrayToList(methods.split(","));
            methodSet.addAll(list);
        });
        List<String> list = new ArrayList<>();
        list.addAll(methodSet);
        return list.stream().sorted().collect(Collectors.toList());
    }



}
