package com.victor2022.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午2:54
 * @description: 继承了Remote的服务接口
 */
public interface UserService extends Remote {

    /**
     * @param name:
     * @return: java.lang.String
     * @author: victor2022
     * @date: 2022/4/23 下午2:55
     * @description: 根据用户名查询用户
     */
    String getUser(String name) throws RemoteException;
}
