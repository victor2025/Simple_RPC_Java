package com.victor2022.commons.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午1:35
 * @description: 用来提供zk连接的自定义类型
 */
@Data
@AllArgsConstructor
public class ZkConnection {

    // zk地址，ip:port
    private String zkServer;

    // 超时时间
    private Integer sessionTimeout;

    public ZkConnection(){
        super();
        this.zkServer = "localhost:2181";
        this.sessionTimeout = 10000;
    }

    /**
     * @return: org.apache.zookeeper.ZooKeeper
     * @author: victor2022
     * @date: 2022/4/23 下午1:41
     * @description: 获取zk链接
     */
    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(this.zkServer, this.sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
    }

}
