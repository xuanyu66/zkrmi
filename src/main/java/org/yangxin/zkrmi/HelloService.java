package org.yangxin.zkrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public interface HelloService extends Remote {

    String sayHello(String name) throws RemoteException;
}
