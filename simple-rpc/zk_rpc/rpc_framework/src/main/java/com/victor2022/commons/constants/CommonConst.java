package com.victor2022.commons.constants;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午1:49
 * @description: 常量
 */
public class CommonConst {

    // rmi的uri起始字符串
    public static final String RMI_START = "rmi://";
    // zk根目录
    public static final String ZK_ROOT = "/victor2022/rpc";
    // 配置文件名称
    public static final String PROPERTIES_FILENAME = "rpc.properties";
    public static final String SERVICE_PROPERTIES_FILENAME = "rpc-service.properties";
    // 配置文件字段
    public static final String REG_IP_SOURCE = "registry.ip";
    public static final String REG_PORT_SOURCE = "registry.port";
    public static final String ZK_SERVER_SOURCE = "zk.server";
    public static final String ZK_TIMEOUT_SOURCE = "zk.sessionTimeout";
    // 默认配置
    public static final String REG_IP_DEFAULT = "127.0.0.1";
    public static final Integer REG_PORT_DEFAULT = 9090;
    public static final String ZK_SERVER_DEFAULT = "127.0.0.1:2181";
    public static final Integer ZK_TIMEOUT_DEFAULT = 10000;
}
