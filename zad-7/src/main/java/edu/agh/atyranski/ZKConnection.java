package edu.agh.atyranski;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKConnection {

    private ZooKeeper zooKeeper;
    CountDownLatch connectionLatch = new CountDownLatch(1);

    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, 2000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });

        connectionLatch.await();
        return zooKeeper;
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

}
