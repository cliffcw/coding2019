package main.com.chenwan.coding2019.interview.bianlifeng;

import java.util.Random;
import java.util.Stack;

/**
 * @program: base
 * @description: 两个栈实现一个队列
 * @author: cliffcw
 * @create: 2019-12-02 21:59
 */
public class QueueWithTwoStack<E> {

    private Stack<E> stack1 = new Stack<E>();

    private Stack<E> stack2 = new Stack<E>();

    /**
     * 入队
     *
     * @param value
     */
    public void offer(E value) {
        stack1.push(value);
    }

    /**
     * 出队
     */
    public E poll() {

        while (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }

        E val = stack2.pop();

        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }

        return val;
    }

    /**
     * 查看队头元素
     *
     * @return
     */
    public E peek() {
        while (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }

        E value = stack2.peek();

        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }

        return value;
    }

    /**
     * 查看队是否为空
     *
     * @return
     */
    public boolean empty() {
        return stack1.empty();
    }

    public static void main(String[] args) {
        QueueWithTwoStack<Integer> queue = new QueueWithTwoStack<Integer>();
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int data = rand.nextInt(100);
            System.out.print(data + " ");
            queue.offer(data);
        }
        System.out.println();
        System.out.println("出队：");
        while (!queue.empty()) {
            System.out.print(queue.poll()+" ");
        }
    }

}
