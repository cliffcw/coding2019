package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: base
 * @description: ZooKeeperConnection zookeeper 链接
 * @author: cliffcw
 * @create: 2019-07-15 09:36
 */
public class ZooKeeperConnection {

    private ZooKeeper zooKeeper;

    final CountDownLatch connectedSignal = new CountDownLatch(1);

    /** * @Description: 链接zookeeper server
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public ZooKeeper connect (String host) throws InterruptedException, IOException {

        zooKeeper = new ZooKeeper(host, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            }
        });

        connectedSignal.await();

        return zooKeeper;
    }

    /**
     * @Description:  和zookeeper服务断开链接
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public void close() throws InterruptedException {
        zooKeeper.close();
    }

}
