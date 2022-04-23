package com.victor2022.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author: victor2022
 * @date: 2022/4/23 上午11:55
 * @description:
 */
public class DemoServiceImpl extends UnicastRemoteObject implements DemoService {

    protected DemoServiceImpl() throws RemoteException {
    }

    @Override
    public String demo(String demo) throws RemoteException {
        return "Hello! "+demo;
    }
}
