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

        String url = "rmi://localhost:1011/org.yangxin.zkmi.HellpServiceImpl";
        try {
            LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            logger.error("Start server failed");
            System.exit(1);
        }
        try {
            Naming.rebind(url, new HelloServiceImpl()) ;
        } catch (RemoteException e) {
            logger.error("bind have a RemoteEception");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("bind have a MalformedURLException");
        }
    }
}
