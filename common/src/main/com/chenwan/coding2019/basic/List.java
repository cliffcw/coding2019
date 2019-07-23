package main.com.chenwan.coding2019.basic;

/**
 * @program: base
 * @description: List
 * @author: cliffcw
 * @create: 2019-06-24 08:41
 */
public interface List {

    /**
     * @Description: 增加一个节点
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public void add(Object o);

    /**
     * @Description: 在某处增加一个节点
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public void add(int index, Object o);

    /**
     * @Description: 获取一个节点数据
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public Object get(int index);

    /**
     * @Description: 移除一个节点
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public Object remove(int index);

    /**
     * @Description: 链表长度
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public int size();
}
