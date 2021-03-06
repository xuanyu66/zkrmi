package org.yangxin.zkrmi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yangxin.zkrmi.services.HashService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * @author leon on 2018/7/18.
 * @version 1.0
 */
public class HashServiceImpl extends UnicastRemoteObject implements HashService {

    private static final Logger logger = LoggerFactory.getLogger(HashServiceImpl.class);

    public HashServiceImpl() throws RemoteException {}

    public HashMap<Integer, String> getHash(String name) {
        HashMap<Integer,String> map = new HashMap<Integer, String>(10);
        map.put(1, name);
        map.put(2, name+"test");
        logger.debug("server hashmap code is {}",map.hashCode());
        return map;
    }
}
