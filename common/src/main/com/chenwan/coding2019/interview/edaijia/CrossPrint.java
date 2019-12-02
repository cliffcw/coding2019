package main.com.chenwan.coding2019.interview.edaijia;

/**
 * @Description: 交替打印
 * @Param:
 * @return:
 * @Author: cliffcw
 * @Date:
 */
public class CrossPrint {

    // 法一
//    volatile static boolean open = false;
//    volatile static int index = 0;
//    static String s = "adasdfsafwfvgs";
//    static Thread t1 = new Thread(new myRun1(), "线程1");
//    static Thread t2 = new Thread(new myRun2(), "线程2");
//
//    public static void main(String[] args) {
//        t1.start();
//        t2.start();
//    }
//
//    static class myRun1 implements Runnable {
//        @Override
//        public void run() {
//
//            while (index < s.length()) {
//                if (open) {
//                    System.out.println(s.charAt(index++) + Thread.currentThread().getName());
//                    open = false;
//                }
//
//            }
//        }
//
//    }
//
//    static class myRun2 implements Runnable {
//        @Override
//        public void run() {
//
//            while (index < s.length()) {
//                if (!open) {
//                    System.out.println(s.charAt(index++) + Thread.currentThread().getName());
//                    open = true;
//                }
//            }
//        }
//
//    }

    //法二
    static int[] a = {1, 2, 3, 4, 5, 6};
    static int[] b = {6, 5, 4, 3, 2, 1};
    static Object ob = new Object();
    static Thread t1 = new Thread(new myRun1(), "线程1");
    static Thread t2 = new Thread(new myRun2(), "线程2");

    public static void main(String[] args) {
        t1.start();
        t2.start();
    }

    static class myRun1 implements Runnable {
        volatile static int indexa = 0;
        @Override
        public void run() {
            if (indexa < a.length) {
                do {
                    synchronized (ob) {
                        System.out.println(a[indexa++] + Thread.currentThread().getName());
                        ob.notifyAll();
                        try {
                            ob.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (indexa < a.length);
            }
        }

    }

    static class myRun2 implements Runnable {
        volatile static int indexb = 0;
        @Override
        public void run() {
            while (indexb < b.length) {
                synchronized (ob) {
                    System.out.println(b[indexb++] + Thread.currentThread().getName());
                    ob.notifyAll();
                    try {
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}