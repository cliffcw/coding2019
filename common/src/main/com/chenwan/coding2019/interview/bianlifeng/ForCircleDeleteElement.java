package main.com.chenwan.coding2019.interview.bianlifeng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: base
 * @description:
 * @author: cliffcw
 * @create: 2019-11-03 15:20
 */
public class ForCircleDeleteElement {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        //会出并发问题
        for (String lis: list) {
            if ("b".equals(lis)) {
                list.remove("b");
            }
        }

        //会出并发问题
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if ("b".equals(next)) {
                list.remove("b");
            }
        }

        //不会出并发问题
        Iterator<String> iterator1 = list.iterator();
        while (iterator1.hasNext()) {
            String next = iterator1.next();
            if ("b".equals(next)) {
                iterator1.remove();
            }
        }
    }
}
