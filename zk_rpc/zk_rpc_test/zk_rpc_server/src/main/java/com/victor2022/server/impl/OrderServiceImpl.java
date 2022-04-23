package com.victor2022.server.impl;

import com.victor2022.api.service.OrderService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午4:38
 * @description: OrderService实现类
 */
public class OrderServiceImpl extends UnicastRemoteObject implements OrderService {
    public OrderServiceImpl() throws RemoteException {
    }

    @Override
    public String getOrder(String id) throws RemoteException {
        System.out.println("当前查询的订单id是："+ id);
        return "{\n\"id\":\""+id+"\",\n"+
                "\"date\":\"2022-4-23\"\n"+
                "}";
    }
}
