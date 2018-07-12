package org.yangxin.zkrmi.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public interface HelloService extends Remote {

    /**
    * @Description: 远程调用返回一个String字符串
    * @Param: [name]
    * @return: java.lang.String
    */
    String sayHello(String name) throws RemoteException;
}
