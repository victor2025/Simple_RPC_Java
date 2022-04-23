package com.victor2022.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午3:07
 * @description:
 */
public class ZkTest {

    @Test
    public void test(){
        try {
            ZooKeeper zookeeper = new
                    ZooKeeper("192.168.1.7:2181", 10000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("获取连接");
                }
            });
            String content = zookeeper.create("/test",
                    "content".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT_SEQUENTIAL);
            System.out.println("content"+content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
