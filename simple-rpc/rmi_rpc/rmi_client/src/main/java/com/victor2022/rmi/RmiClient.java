package com.victor2022.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午12:02
 * @description: rmi
 */
public class RmiClient {

    public static void main(String[] args) {
        try {
            DemoService demoService = (DemoService) Naming.lookup("rmi://localhost:8888/demoService");
            String result = demoService.demo("test rmi service");
            System.out.println(result);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
