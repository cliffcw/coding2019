package test;

/**
 * @program: base
 * @description: 测试对象头信息
 * @author: cliffcw
 * @create: 2019-07-16 10:23
 */
public class TestObjectHeader {

    public static void main(String[] args) {
        TestA testA = new TestA();
        testA.say();
    }

}

class TestA {

    int a = 0;

    int b = 0;

    public void say() {
        System.out.println("say something!");
    }
}
