package com.victor2022.api.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午4:37
 * @description:
 */
public interface OrderService extends Remote {
    String getOrder(String id) throws RemoteException;
}
