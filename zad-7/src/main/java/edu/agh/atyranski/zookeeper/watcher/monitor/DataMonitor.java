package edu.agh.atyranski.zookeeper.watcher.monitor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

@Getter
@Slf4j
public class DataMonitor implements Watcher, StatCallback, ChildrenCallback {

    private final ZooKeeper zookeeper;
    private final String ZNodeName;
    private final DataMonitorListener listener;

    boolean alive = true;

    byte[] previousData;

    public DataMonitor(
            ZooKeeper zookeeper,
            String ZNodeName,
            DataMonitorListener listener
    ) {
        this.zookeeper = zookeeper;
        this.ZNodeName = ZNodeName;
        this.listener = listener;
        zookeeper.exists(ZNodeName, true, this, null);
        zookeeper.getChildren(ZNodeName, true, this, null);
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
                    alive = false;
                    listener.closing(KeeperException.Code.SESSIONEXPIRED.intValue());
                    break;
            }
        } else {
            if (path != null && path.equals(ZNodeName)) {
                zookeeper.exists(ZNodeName, true, this, null);
                zookeeper.getChildren(ZNodeName, true, this, null);
            }
        }
    }

    @Override
    public void processResult(
            int reasonCode,
            String path,
            Object context,
            Stat stat
    ) {
        boolean exists;
        switch (KeeperException.Code.get(reasonCode)) {
            case OK: {
                exists = true;
                break;
            }
            case NONODE: {
                exists = false;
                break;
            }
            case SESSIONEXPIRED: {
                alive = false;
                listener.closing(reasonCode);
                return;
            }
            default: {
                zookeeper.exists(ZNodeName, true, this, null);
                return;
            }
        }

        byte[] bytes = null;
        if (exists) {
            try {
                bytes = zookeeper.getData(ZNodeName, false, null);
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

    @Override
    public void processResult(
            int reasonCode,
            String path,
            Object context,
            List<String> children
    ) {
        boolean exists;
        switch (KeeperException.Code.get(reasonCode)) {
            case OK: {
                exists = true;
                break;
            }
            case NONODE: {
                exists = false;
                break;
            }
            case SESSIONEXPIRED: {
                alive = false;
                listener.closing(reasonCode);
                return;
            }
            default: {
                zookeeper.exists(ZNodeName, true, this, null);
                return;
            }
        }

        if (exists) {
            listener.updateChildrenAmount(children);
        } else {
            listener.updateChildrenAmount(null);
        }
    }
}
