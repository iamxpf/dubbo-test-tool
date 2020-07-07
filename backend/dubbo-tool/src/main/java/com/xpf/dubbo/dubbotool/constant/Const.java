package com.xpf.dubbo.dubbotool.constant;

/**
 * @author: xia.pengfei
 * @date: 2020/6/29
 */
public class Const {

    /**
     * 注册zk的redis中key
     */
    public static final String REGISTRY_KEY = "registry:zookeeper";

    public static final String CONNECT_KEY = "dubbo:listGroup";

    /**
     *registry default protocol
     */
    public static final String REGISTRY_PROTOCOL = "zookeeper";

    /**
     * registry default group
     */
    public static final String DEFAULT_GROUP = "dubbo";

    /**
     * ZookeeperRegistry源码中zkClient字段
     */
    public static final String ZOOKEEPERREGISTRY_ZKCLIENT = "zkClient";

    /**
     * zookeeper根节点
     */
    public static final String ZOOKEEPER_ROOT = "/";


    public static final String APPLICATION_NAME = "dubbo-tool";



}
