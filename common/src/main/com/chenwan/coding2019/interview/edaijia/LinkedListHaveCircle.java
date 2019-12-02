package main.com.chenwan.coding2019.interview.edaijia;

/**
 * @program: base 判断链表是否有环
 * @description:
 * @author: cliffcw
 * @create: 2019-10-22 10:23
 */
public class LinkedListHaveCircle {

    public static void main(String[] args) {

        Node linkedList = generateLinkdeList();

        //判断单链表linkedList里面是否有环，有的话找出环入口
        boolean haveCircle = isHaveCircle(linkedList);
        if (haveCircle) {
            Node circleCrossNode = getCircleCross(linkedList);
            System.out.println("此单向链表有环，且环入口节点为：" + circleCrossNode.toString());
            System.out.println("此单向链表有环，且环入口节点值为：" + circleCrossNode.getData());
        } else {
            System.out.println("此单向链表无环");
        }

    }

    private static Node getCircleCross(Node linkedList) {
        return null;
    }

    private static boolean isHaveCircle(Node linkedList) {
        Node A = linkedList;
        Node B = linkedList;

        return false;
    }

    private static Node generateLinkdeList() {

        Node node = new LinkedListHaveCircle().new Node();
        node.setData("1");
        node.setNext(generateLinkdeList());

        return null;
    }

    class Node {
        private String data;
        private Node next;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
