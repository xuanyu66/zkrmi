package org.yangxin.zkrmi.server;

import org.yangxin.zkrmi.services.Constant;
import org.yangxin.zkrmi.services.HashService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * @author leon on 2018/7/19.
 * @version 1.0
 */
public class Server {

    public static void main(String[] args) {
        if(args.length != Constant.INPUT_ARGS_LENGTH){
            System.err.println("please using demand: java Server <rmi_pore> <rmi_port>");
            System.exit(-1);
        }
        String host = args[0];
        if(Constant.LOCALHOST_NAME.equalsIgnoreCase(host) || Constant.LOOPBACK_ADDRESS.equalsIgnoreCase(host)){
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                host = inetAddress.toString().split("\\/")[1];
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        int port = Integer.parseInt(args[1]);

        ServiceProvider provider = new ServiceProvider();

        try {
            HashService hashService = new HashServiceImpl();
            provider.publish(hashService, host, port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
