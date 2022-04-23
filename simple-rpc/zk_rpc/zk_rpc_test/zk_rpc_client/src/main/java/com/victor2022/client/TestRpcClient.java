package com.victor2022.client;

import com.victor2022.api.service.OrderService;
import com.victor2022.api.service.UserService;
import com.victor2022.commons.RpcFactory;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午3:52
 * @description:
 */
public class TestRpcClient {
    public static void main(String[] args) {
        try {
            // userService
            UserService userService = RpcFactory.getServiceProxy(UserService.class);
            String result = userService.getUser("管理员");
            System.out.println(UserService.class.getName()+"远程调用返回结果:\n"+result);
            // orderService
            OrderService orderService = RpcFactory.getServiceProxy(OrderService.class);
            System.out.println(OrderService.class.getName()+"远程调用结果:\n"+orderService.getOrder("100"));
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }
}
