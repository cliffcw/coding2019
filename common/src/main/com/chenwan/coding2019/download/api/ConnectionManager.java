package main.com.chenwan.coding2019.download.api;

public interface ConnectionManager {

    /**
     * @Description: 给定一个url , 打开一个连接
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public Connection open(String url) throws ConnectionException;
}
