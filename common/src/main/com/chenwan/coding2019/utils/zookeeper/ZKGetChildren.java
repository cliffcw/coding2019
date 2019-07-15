package main.com.chenwan.coding2019.utils.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @program: base
 * @description: ZKGetChildren
 * @author: cliffcw
 * @create: 2019-07-15 17:32
 */
public class ZKGetChildren {

    private static ZooKeeper zk;

    private static ZooKeeperConnection conn;

    /**
     * @Description: Method to check existence of znode and its status, if znode is available.
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static Stat znode_exists(String path) throws KeeperException, InterruptedException {

        return zk.exists(path, true);

    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
        String path = "/MyFirstZnode"; // Assign path to the znode

        try {

            conn = new ZooKeeperConnection();

            zk = conn.connect("localhost");

            Stat stat = znode_exists(path); // Stat checks the path

            if (stat != null) {

                // “getChildren" method- get all the children of znode.It has two args, path and watch
                List<String> children = zk.getChildren(path, false);

                for (int i = 0; i < children.size(); i++)

                    System.out.println(children.get(i)); //Print children's

            } else {

                System.out.println("Node does not exists");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
