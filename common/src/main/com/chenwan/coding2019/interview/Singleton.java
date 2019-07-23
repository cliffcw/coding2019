package main.com.chenwan.coding2019.interview;

/**
 *
 * package-info 实现单列模式
 *
 */

/**
 * @program: base
 * @description: 实现Singleton模式 -- 饿汉模式，先生成放起，用就直接加载。
 * @author: cliffcw
 * @create: 2019-07-21 09:40
 */
public class Singleton {

    public Singleton () {}

    private static final Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        Singleton2 instance = Singleton2.getInstance();

        System.out.println(instance);
    }
}

/** * @Description:  双重锁校验
 * @Param:
 * @return:
 * @Author: cliffcw
 * @Date:
 */
class Singleton2 {

    /**volatile防止指令重排*/
    private static volatile Singleton2 singleton;

    private Singleton2() {
    }

    /**只是在实例为空时才进行同步创建
     * 为什么做了2次判断？
     * A线程和B线程同时进入同步方法0
     * 然后都在1位置处判断了实例为null
     * 然后都进入了同步块2中
     * 然后A线程优先进入了同步代码块2中（B线程也进入了），然后创建了实例
     * 此时，如果没有3处的判断，那么A线程创建实例同时，B线程也会创建一个实例
     * 所以，还需要做2次判断
     * */
    public static Singleton2 getInstance(){//0
        if(singleton == null){//1
            synchronized (Singleton2.class){//2
                if(singleton == null){//3
                    singleton = new Singleton2();//4
                }
            }
        }
        return singleton;
    }
}

/** * @Description:  懒汉式，用的时候才去生成
 * @Param:
 * @return:
 * @Author: cliffcw
 * @Date:
 */
class Singleton3 {

    private static Singleton3 instance;

    private Singleton3() {
    }

    /**
     * 整个方法锁住了，效率较低
     * @return
     */
    public synchronized static Singleton3 getbSingleton(){
        if(instance == null){
            instance = new Singleton3();
        }
        return instance;
    }
}

/** * @Description:  静态内部类方式，使得INSTANCE只会在SingletonHolder加载的时候初始化一次，从而保证不会有多线程初始化的情况，因此也是线程安全的。
 * @Param:
 * @return:
 * @Author: cliffcw
 * @Date:
 */
class Singleton4 {

    private static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton4 (){}

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
