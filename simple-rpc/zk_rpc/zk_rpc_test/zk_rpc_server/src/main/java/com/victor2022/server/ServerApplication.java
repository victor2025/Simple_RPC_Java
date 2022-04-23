package com.victor2022.server;

import com.victor2022.api.service.UserService;
import com.victor2022.commons.RpcFactory;
import com.victor2022.server.impl.UserServiceImpl;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午3:01
 * @description: 服务端启动
 */
public class ServerApplication {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, ClassNotFoundException {
        Class.forName("com.victor2022.commons.RpcFactory");
//        // 创建服务对象
//        UserServiceImpl userService = new UserServiceImpl();
//        // 注册
//        RpcFactory.registerService(UserService.class,userService);
    }
}
