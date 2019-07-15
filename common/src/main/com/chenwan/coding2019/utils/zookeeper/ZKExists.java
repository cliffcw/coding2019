package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @program: base
 * @description: ZKExists
 * @author: cliffcw
 * @create: 2019-07-15 14:31
 */
public class ZKExists {

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

        try {
            conn = new ZooKeeperConnection();

            zk = conn.connect(host);

            Stat stat = znode_exists(path);

            if (stat != null) {

                System.out.println("Node exists and the node version is " + stat.getVersion());
            } else {

                System.out.println("Node does not exists");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage()); // Catches error messages
        }

    }
}
