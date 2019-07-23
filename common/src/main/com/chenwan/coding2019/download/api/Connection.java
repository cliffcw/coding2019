package main.com.chenwan.coding2019.download.api;

/**
 * @program: base
 * @description: 链接
 * @author: cliffcw
 * @create: 2019-06-23 12:34
 */
public interface Connection {

    /**
     * @Description: 给定开始和结束位置， 读取数据， 返回值是字节数组
     * @Param: startPos 开始位置， 从0开始
     * @Param: endPos 结束位置
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public byte[] read(int startPos, int endPos) throws Exception;

    /**
     * @Description: 得到数据内容的长度
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public int getContentLength();

    /**
     * @Description: 关闭链接
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public void close();

}
