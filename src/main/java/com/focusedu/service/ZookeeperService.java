package com.focusedu.service;

import com.focusedu.utils.lang.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * ZookeeperService
 *
 * @author liuruichao
 * @date 15/10/23 下午1:47
 */
public class ZookeeperService {
    private static ZooKeeper zk;

    static {
        Properties props = new Properties();
        try {
            props.load(ZookeeperService.class.getClassLoader().getResourceAsStream("zoo.properties"));
            String zkConnStr = props.getProperty("zoo.url");
            zk = new ZooKeeper(zkConnStr, 10000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getChildren(String path) throws KeeperException, InterruptedException {
        if (StringUtils.isEmpty(path)) {
            throw new NullPointerException("path is null!!!");
        }
        return zk.getChildren(path, false);
    }

    public static String getValue(String path) throws KeeperException, InterruptedException {
        if (StringUtils.isEmpty(path)) {
            throw new NullPointerException("path is null!!!");
        }
        byte[] data = zk.getData(path, false, new Stat());
        return new String(data, 0, data.length);
    }
}
