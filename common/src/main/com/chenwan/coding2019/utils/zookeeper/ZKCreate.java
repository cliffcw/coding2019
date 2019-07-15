package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @program: base
 * @description: ZKCreate
 * @author: cliffcw
 * @create: 2019-07-15 14:14
 */
public class ZKCreate {

    private static ZooKeeper zk;

    private static ZooKeeperConnection conn;

    private static String host = "localhost";

    /**
     * @Description: 创建一个znode
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    private static void create(String path, byte[] data) throws KeeperException, InterruptedException {

        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        //Znode路径
        String path = "/MyFirstZnode";

        byte[] data = "My first zookeeper app".getBytes();

        try {
            conn = new ZooKeeperConnection();

            zk = conn.connect(host);

            create(path, data); //Create the data to the specified path

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
