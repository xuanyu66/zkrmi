package org.yangxin.zkrmi;

import org.yangxin.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    public  HelloServiceImpl() throws RemoteException{
    }

    public String sayHello(String name) {
        return String.format("Hello %s",name);
    }
}
