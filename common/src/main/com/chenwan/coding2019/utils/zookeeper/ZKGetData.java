package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * @program: base
 * @description: ZKGetData
 * @author: cliffcw
 * @create: 2019-07-15 14:41
 */
public class ZKGetData {

    private static ZooKeeper zk;

    private static ZooKeeperConnection conn;

    private static String host = "localhost";

    /**
     * @Description: znode是否存在
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static Stat znode_exists(String path) throws KeeperException, InterruptedException {

        return zk.exists(path, true);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/MyFirstZnode";

        final CountDownLatch connectedSignal = new CountDownLatch(1);

        try {
            conn = new ZooKeeperConnection();

            zk = conn.connect(host);

            Stat stat = znode_exists(path);

            if (stat != null) {

                byte[] b = zk.getData(path, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {

                        if (event.getType() == Event.EventType.None) {

                            switch (event.getState()) {
                                case Expired:
                                    connectedSignal.countDown();
                                    break;
                            }
                        } else {

                            String path = "/MyFirstZnode";

                            try {

                                byte[] bn = zk.getData(path, false, null);

                                String data = new String(bn, "UTF-8");

                                System.out.println(data);

                                connectedSignal.countDown();

                            } catch (KeeperException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, null);

                String data = new String(b, "UTF-8");

                System.out.println(data);

                connectedSignal.await();

            } else {

                System.out.println("Node does not exists");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
