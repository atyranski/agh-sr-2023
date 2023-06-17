package edu.agh.atyranski.zookeeper.watcher.monitor;

import java.util.List;

public interface DataMonitorListener {

    void exists(byte[] data);

    void closing(int rc);

    void updateChildrenAmount(List<String> children);
}
