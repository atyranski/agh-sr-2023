package edu.agh.atyranski;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BasicZKManager implements ZKManager {
    private static ZooKeeper zooKeeper;
    private static ZKConnection zkConnection;

    public BasicZKManager() throws IOException, InterruptedException {
        initialize();
    }

    private void initialize() throws IOException, InterruptedException {
        zkConnection = new ZKConnection();
        zooKeeper = zkConnection.connect("localhost");
    }

    public void closeConnection() throws InterruptedException {
        zkConnection.close();
    }

    @Override
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Override
    public Object getZNodeData(String path, boolean watchFlag) throws InterruptedException, KeeperException {
        byte[] bytes = zooKeeper.getData(path, null, null);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public void update(String path, byte[] data) throws KeeperException, InterruptedException {
        int version = zooKeeper.exists(path, true).getVersion();
        zooKeeper.setData(path, data, version);
    }
}
