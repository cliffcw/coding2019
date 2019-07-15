package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * @program: base
 * @description: ZKSetData
 * @author: cliffcw
 * @create: 2019-07-15 15:40
 */
public class ZKSetData {

    private static ZooKeeper zk;

    private static ZooKeeperConnection conn;

    /** * @Description:   Method to update the data in a znode. Similar to getData but without watcher.
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void update (String path, byte[] data) throws KeeperException, InterruptedException {

        zk.setData(path, data, zk.exists(path, true).getVersion());

    }

    public static void main(String[] args) {

        String path = "/MyFirstZnode";

        byte[] data = "SUCCESS".getBytes();

        try {
            conn = new ZooKeeperConnection();

            zk = conn.connect("localhost");

            update(path, data); // Update znode data to the specified path
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
