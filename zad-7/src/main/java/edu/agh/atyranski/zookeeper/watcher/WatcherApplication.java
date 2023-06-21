package edu.agh.atyranski.zookeeper.watcher;

import edu.agh.atyranski.zookeeper.watcher.monitor.DataMonitor;
import edu.agh.atyranski.zookeeper.watcher.monitor.DataMonitorListener;
import edu.agh.atyranski.zookeeper.watcher.util.StreamWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class WatcherApplication implements Watcher, Runnable, DataMonitorListener {

    private final String ZNodeName;
    private final ZooKeeper zookeeper;
    private final DataMonitor dataMonitor;

    private Process child;

    private WatcherApplication(String hostAddress, String ZNodeName) throws IOException {
        this.ZNodeName = ZNodeName;
        this.zookeeper = new ZooKeeper(hostAddress, 3000, this);
        this.dataMonitor = new DataMonitor(zookeeper, ZNodeName, this);
    }

    @Override
    public void process(WatchedEvent event) {
        dataMonitor.process(event);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (dataMonitor.isAlive()) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closing(int reasonCode) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void updateChildrenAmount(List<String> children) {
        if (children != null) {
            log.info("Children: {}", children.size());
        } else {
            log.info("ZNode of name '{}' don't exist", ZNodeName);
        }
    }

    @Override
    public void exists(byte[] data) {
        if (data == null) {
//            log.info("Deleted");
            if (child != null) {
//                log.info("Killing process");
                try {
                    child.destroy();
                    child.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            child = null;
        } else {
//            log.info("Created: {}/{}", new String(data, StandardCharsets.UTF_8), data.length);
            if (child == null && data.length != 0) {
                try {
//                    log.info("Starting child");
                    String program = new String(data, StandardCharsets.UTF_8);
                    child = Runtime.getRuntime().exec(program);

                    new StreamWriter(child.getInputStream(), System.out);
                    new StreamWriter(child.getErrorStream(), System.err);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        final String hostAddress = "localhost:21811";
        final String ZNodeName = "/z";

        try {
            new WatcherApplication(hostAddress, ZNodeName).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
// C:\Program Files\PuTTY\putty.exe