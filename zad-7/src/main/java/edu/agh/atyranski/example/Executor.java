package edu.agh.atyranski.example;

import java.io.FileOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class Executor implements Watcher, Runnable, DataMonitorListener {
    String ZNodeName;

    DataMonitor dataMonitor;

    ZooKeeper zooKeeper;

    String filename;

    String[] executables;

    Process child;

    public Executor(String hostPort, String ZNodeName, String filename, String[] executables)
            throws IOException {

        this.filename = filename;
        this.executables = executables;
        this.ZNodeName = ZNodeName;
        this.zooKeeper = new ZooKeeper(hostPort, 3000, this);
        this.dataMonitor = new DataMonitor(zooKeeper, ZNodeName, null, this);
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("USAGE: Executor hostPort ZNode filename program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String ZNode = args[1];
        String filename = args[2];
        String[] executables = new String[args.length - 3];
        System.arraycopy(args, 3, executables, 0, executables.length);
        try {
            new Executor(hostPort, ZNode, filename, executables).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void process(WatchedEvent event) {
        dataMonitor.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dataMonitor.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            log.warn("Program interrupted", e);
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    log.warn("Program interrupted", e);
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(executables);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
