package org.yangxin.zkrmi.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * @author leon on 2018/7/18.
 * @version 1.0
 */
public interface HashService extends Remote {

    HashMap<Integer, String> getHash(String name) throws RemoteException;
}