package edu.agh.atyranski.example;

import java.util.Arrays;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

public class DataMonitor implements Watcher, StatCallback {

    ZooKeeper zooKeeper;

    String ZNode;

    Watcher chainedWatcher;

    boolean dead;

    DataMonitorListener listener;

    byte[] previousData;

    public DataMonitor(
            ZooKeeper zk,
            String ZNode,
            Watcher chainedWatcher,
            DataMonitorListener listener
    ) {
        this.zooKeeper = zk;
        this.ZNode = ZNode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;
        zk.exists(ZNode, true, this, null);
    }

    public void process(
            WatchedEvent event
    ) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
                    break;
            }
        } else {
            if (path != null && path.equals(ZNode)) {
                zooKeeper.exists(ZNode, true, this, null);
            }
        }
        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
    }

    public void processResult(
            int reasonCode,
            String path,
            Object context,
            Stat stat
    ) {
        boolean exists;
        switch (Code.valueOf(String.valueOf(reasonCode))) {
            case OK -> exists = true;
            case NONODE -> exists = false;
            case SESSIONEXPIRED, NOAUTH -> {
                dead = true;
                listener.closing(reasonCode);
                return;
            }
            default -> {
                zooKeeper.exists(ZNode, true, this, null);
                return;
            }
        }

        byte[] bytes = null;
        if (exists) {
            try {
                bytes = zooKeeper.getData(ZNode, false, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
        if ((bytes == null && bytes != previousData)
                || (bytes != null && !Arrays.equals(previousData, bytes))) {
            listener.exists(bytes);
            previousData = bytes;
        }
    }
}
