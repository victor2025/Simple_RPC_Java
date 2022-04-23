package com.victor2022.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author: victor2022
 * @date: 2022/4/23 上午11:59
 * @description: rmi服务器主类
 */
public class RmiServer {
    public static void main(String[] args) {
        try {
            DemoServiceImpl demoServer = new DemoServiceImpl();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/demoService",demoServer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
