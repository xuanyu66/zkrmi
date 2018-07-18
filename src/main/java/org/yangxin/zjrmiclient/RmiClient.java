package org.yangxin.zjrmiclient;

/**
 * @author leon on 2018/7/18.
 * @version 1.0
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yangxin.HashService;
import org.yangxin.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * @author leon on 2018/7/17.
 * @version 1.0
 */
public class RmiClient {

    private static final Logger logger = LoggerFactory.getLogger(org.yangxin.zjrmiclient.RmiClient.class);

    public static void main(String[] args) {
        String url = "rmi://localhost:1011/org.yangxin.zkmi.HelloServiceImpl";
        try {
            sayHello(url);
            getHash();
        } catch (NotBoundException e) {
            e.printStackTrace();
            logger.error("bind have a NotBoundException");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("bind have a MalformedURLException");
        } catch (RemoteException e) {
            e.printStackTrace();
            logger.error("bind have a RemoteException");
        }
    }

    static void sayHello (String url) throws RemoteException, NotBoundException, MalformedURLException{
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayHello("jack");
        logger.info(result);
        logger.info("Current Time: {}", System.currentTimeMillis());
        System.out.println(result);
    }

    static void getHash () throws RemoteException, NotBoundException, MalformedURLException{
        String url = "rmi://localhost:1012/org.yangxin.zkmi.HashServiceImpl";
        HashService helloService = (HashService) Naming.lookup(url);
        HashMap result = (HashMap<Integer,String>) helloService.getHash("jack");
        logger.info("Hashmap code is {}",result.hashCode());
        logger.info("Current Time: {}", System.currentTimeMillis());
        System.out.println(result);
    }
}
