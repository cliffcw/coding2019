package main.com.chenwan.coding2019.basic;

/**
 * @program: base
 * @description: 链表
 * @author: cliffcw
 * @create: 2019-06-24 08:44
 */
public class LinkedList implements List {

    private Node head;
    private int size;

    private static class Node {
        Object data;
        Node next;

        private Node(Object o) {
            this.data = o;
            this.next = null;
        }
    }

    public LinkedList() {
        this.size = 0;
        this.head = null;
    }

    @Override
    public void add(Object o) {

        Node node = new Node(0);

        if (head == null) {
            head = node;
        } else {
            Node p = head; // p 为游标，从头遍历到尾
            while (p.next != null) {
                p = p.next;
            }
            p.next = node;
        }

        size++;
    }

    @Override
    public void add(int index, Object o) {

        if (head != null) {

            Node p = head;

            int k = 0;

            //扫描单链表，查找第index-1个节点
            while (k < index - 1 && p.next != null) {
                k++;
                p = p.next;
            }

            if (p != null) {

                Node node = new Node(0);

                node.next = p.next;

                p.next = node;

            }

            size++;
        }
    }

    @Override
    public Object get(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException();

        } else {

            Node p = head;

            int k = 0;

            while (k < index && p.next != null) {

                k++;

                p = p.next;
            }

            return p.data;
        }
    }

    @Override
    public Object remove(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException();

        } else {

            if (head == null) {

                return null;
            }

            if (index == 0) {

                head = head.next;

                size--;

                return head.data;
            } else {

                Node p = head;

                int k = 0;

                //p为游标，游标移动到index到上一个位置。
                while (k < index && p.next != null) {

                    k++;

                    p = p.next;
                }

                Node pn = p.next;

                if (pn != null) {

                    p.next = pn.next;

                    size--;

                    return pn.data;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
