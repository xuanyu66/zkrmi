package org.yangxin.zkrmi.client;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yangxin.zkrmi.services.Constant;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author leon on 2018/7/11.
 * @version 1.0
 */
public class ServiceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConsumer.class);

    //定义一个 volatile 成员变量，用于保存最新的 RMI 地址（考虑到该变量或许会被其它线程所修改，一旦修改后，该变量的值会影响到所有线程）
    private volatile List<String> urlList = new ArrayList();

    private CountDownLatch latch = new CountDownLatch(1);

    /**
    * @Description:  构造器
    * @Param: []
    * @return:
    */
    public ServiceConsumer(){
        ZooKeeper zk = connectServer();
        if(zk != null){
            watchNode(zk);
        }
    }

    /**
    * @Description: 查找RMi服务
    * @Param: []
    * @return: T
    */
    public <T extends Remote> T lookup(){
        T service = null;
        int size = urlList.size();
        if(size > 0){
            String url = null;
            if(size == 1){
                url = urlList.get(0);
                LOGGER.debug("using only url: {}", url);
            }else{
                url = urlList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random url: {}", url);
            }
            service = lookupService(url);
        }
        return service;
    }

    /**
    * @Description: 连接zookeeper服务器
    * @Param: []
    * @return: org.apache.zookeeper.ZooKeeper
    */
    private ZooKeeper connectServer(){
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(Constant.ZK_CONNECTION_STRING, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        latch.countDown();
                    }
                }
            });
            latch.await();
        }catch (IOException | InterruptedException e){
            LOGGER.error("",e);
        }
        return zk;
    }

    /**
    * @Description: 观察 /registy 节点下所有子节点是否有变化
    * @Param: [zk]
    * @return: void
    */
    private void watchNode(final ZooKeeper zk){
        try{
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<>();
            for(String node : nodeList){
                byte[] data = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(data));
                urlList = dataList;
            }
        }catch (KeeperException | InterruptedException e){
            LOGGER.error("", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T lookupService(String url){
        T remote = null;
        try {
            remote = (T) Naming.lookup(url);
            LOGGER.info("get data from url:{}", url);
        }catch (NotBoundException | MalformedURLException | RemoteException e){
            if(e instanceof ConnectException){
                //若连接中断，则使用 urlList 中第一个 RMI 地址来查找（这是一种简单的重试方式，确保不会抛出异常）
                LOGGER.error("ConnectionException -> url: {}", url);
                if(urlList.size() != 0){
                    url =urlList.get(0);
                    return lookupService(url);
                }
            }
            LOGGER.error("", e);
        }
        return remote;
    }
}
