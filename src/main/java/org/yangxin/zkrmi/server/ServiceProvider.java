package org.yangxin.zkrmi.server;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yangxin.zkrmi.services.Constant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * @author leon on 2018/7/19.
 * @version 1.0
 */
public class ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProvider.class);

    private CountDownLatch latch = new CountDownLatch(1);

    /** 
    * @Description:  发布 RMI 服务并注册 RMI 地址到 ZooKeeper 中
    * @Param: [remote, host, port] 
    * @return: void  
    */ 
    public void publish(Remote remote, String host, int port){
        //发布 RMI 服务并返回 RMI 地址
        String url = publishService(remote, host, port);
        if(url != null){
            ZooKeeper zk = connectServer();
            if(zk != null){
                createNode(zk, url, port);
            }
        }
    }

    /**
     * @Description:  发布 RMI 服务
     * @Param: [remote, host, port]
     * @return: java.lang.String
     */
    private String publishService(Remote remote, String host, int port) {
        String url = null;
        try {
            url = String.format("rmi://%s:%d/%s", host, port, remote.getClass().getName());
            LocateRegistry.createRegistry(port);
            Naming.rebind(url, remote);
            logger.info("publish rmi service (url : {})",url);
        }catch (RemoteException | MalformedURLException e){
            logger.error("",e);
        }
        return url;
    }

    /**
     * @Description:  连接 Zookeeper 服务器
     * @Param: []
     * @return: org.apache.zookeeper.ZooKeeper
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk =null;
        try {
            zk = new ZooKeeper("192.168.0.141:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        }catch (IOException | InterruptedException e){
            logger.error("",e);
        }
        return zk;
    }

    /** 
    * @Description:  创建 ZNode
    * @Param: [zk, url] 
    * @return: void  
    */ 
    private void createNode(ZooKeeper zk, String url, int port) {
        try{
            byte[] data = url.getBytes();
            String path = zk.create(Constant.ZK_PROVIDER_PATH + port, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
            logger.info("create zookeeper node ({} => {})", path, url);
        }catch (KeeperException | InterruptedException e){
            logger.error("",e);
        }
    }

}
