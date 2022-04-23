package com.victor2022.server.impl;

import com.victor2022.api.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午2:57
 * @description: UserService的实现类
 */

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
    }

    @Override
    public String getUser(String name) throws RemoteException {
        System.out.println("要查询的用户是："+name);
        return "{\n" +
                "        \"id\":\"1\",\n" +
                "        \"username\":\""+
                name+
                "\",\n" +
                "        \"password\":\"123123\"\n" +
                "}";
    }
}
