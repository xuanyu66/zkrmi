package org.yangxin.zkrmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public class RmiClient {

    private static final Logger logger = LoggerFactory.getLogger(org.yangxin.zkrmi.RmiServer.class);

    public static void main(String[] args) {
        String url = "rmi://localhost:1011/org.yangxin.zkmi.HellpServiceImpl";
        try {
            HelloService helloService = (HelloService) Naming.lookup(url);
            String result = helloService.sayHello("jack");
            logger.info(result);
            logger.info("Current Time: {}", System.currentTimeMillis());
            System.out.println(result);
        } catch (NotBoundException e) {
            e.printStackTrace();
            logger.error("bind have a NotBoundException");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("bind have a NotBoundException");
        } catch (RemoteException e) {
            e.printStackTrace();
            logger.error("bind have a NotBoundException");
        }
    }
}
