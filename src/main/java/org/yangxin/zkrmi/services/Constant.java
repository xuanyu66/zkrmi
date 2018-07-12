package org.yangxin.zkrmi.services;

/**
 * @author leon on 2018/7/11.
 * @version 1.0
 */
public interface Constant {
    String ZK_CONNECTION_STRING = "192.168.0.167:2181";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
    String LOCALHOST_NAME = "localhost";
    String LOOPBACK_ADDRESS = "127.0.0.1";
    int INPUT_ARGS_LENGTH = 2;
}
