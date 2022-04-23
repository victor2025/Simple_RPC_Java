package com.victor2022.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: victor2022
 * @date: 2022/4/23 上午11:57
 * @description:
 */
public interface DemoService extends Remote {
    String demo(String demo) throws RemoteException;
}
