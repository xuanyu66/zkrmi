package org.yangxin.zkrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public class RmiServer {

    private static final Logger logger = LoggerFactory.getLogger(RmiServer.class);
    public static void main(String[] args) {
        int port = 1011;
        int port2 = 1012;

        String url = "rmi://localhost:1011/org.yangxin.zkmi.HelloServiceImpl";
        String url2 = "rmi://localhost:1012/org.yangxin.zkmi.HashServiceImpl";
        try {
            LocateRegistry.createRegistry(port);
            LocateRegistry.createRegistry(port2);
            logger.info("create registry success");
        } catch (RemoteException e) {
            logger.error("Start server failed");
            System.exit(1);
        }
        try {
            Naming.rebind(url, new HelloServiceImpl()) ;
            Naming.rebind(url2, new HashServiceImpl()) ;
            logger.info("start server success");
        } catch (RemoteException e) {
            logger.error("bind have a RemoteEception");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("bind have a MalformedURLException");
        }
    }
}
