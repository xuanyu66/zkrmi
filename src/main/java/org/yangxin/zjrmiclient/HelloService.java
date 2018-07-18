package org.yangxin.zjrmiclient;

/**
 * @author leon on 2018/7/18.
 * @version 1.0
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {

    String sayHello(String name) throws RemoteException;
}
