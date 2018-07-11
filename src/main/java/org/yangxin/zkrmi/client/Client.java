package org.yangxin.zkrmi.client;

import org.yangxin.zkrmi.services.HashService;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * @author leon on 2018/7/11.
 * @version 1.0
 */
public class Client {

    public static void main(String[] args) {
        ServiceConsumer consumer = new ServiceConsumer();
        while (true){
            HashService hashService = consumer.lookup();
            try {
                HashMap<Integer, String> res = hashService.getHash("jack");
                System.out.println(res);
                Thread.sleep(3000);
            } catch (RemoteException |InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
