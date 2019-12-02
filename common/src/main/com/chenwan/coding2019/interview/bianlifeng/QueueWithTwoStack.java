package main.com.chenwan.coding2019.interview.bianlifeng;

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
    public E pop() {

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
        boolean empty = queue.empty();
        System.out.println("1:" + empty);

        queue.offer(1);
        Integer peek = queue.peek();
        System.out.println("2:" + peek);

        Integer pop = queue.pop();
        System.out.println("3:" + pop);

        boolean empty1 = queue.empty();
        System.out.println("4:" + empty1);
    }

}
