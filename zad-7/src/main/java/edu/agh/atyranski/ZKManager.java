package edu.agh.atyranski;

import org.apache.zookeeper.KeeperException;

public interface ZKManager {

    void create(String path, byte[] data) throws KeeperException, InterruptedException;

    Object getZNodeData(String path, boolean watchFlag) throws InterruptedException, KeeperException;

    void update(String path, byte[] data) throws KeeperException, InterruptedException;
}
