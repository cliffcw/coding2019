import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: base
 * @description:
 * @author: cliffcw
 * @create: 2019-08-25 13:17
 */
public class ArrayListTest {

    @Test
    public void arrayListTest() throws CloneNotSupportedException {
        List<TestArr>  list = new ArrayList<TestArr>();

        TestArr test = new TestArr();
        test.setId("1");
        test.setName("cw");

        list.add(test);

        System.out.println("list:" + list);
        List<TestArr> listNew = new ArrayList<TestArr>();
        listNew.addAll(list);
        System.out.println("listNew:" + listNew);

        System.out.println("====================");
        test.setName("cwNew");
        System.out.println("list:" + list);
        System.out.println("listNew:" + listNew);
        System.out.println("======================");

        List<TestArr> listOld = new ArrayList<TestArr>();
        Iterator<TestArr> iterator = list.iterator();
        while (iterator.hasNext()) {
            TestArr next = iterator.next();
            TestArr testArr = new TestArr();
            testArr = (TestArr)next.clone();
            testArr.setName("aaa");
            listOld.add(testArr);
        }
        System.out.println("listOld:"  + listOld);
        System.out.println("--------------");

        test.setName("oldCw");
        System.out.println("list:" + list);
        System.out.println("listOld2:" + listOld);


    }
}
