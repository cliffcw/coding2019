package hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @program: base
 * @description:
 * 明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000），
 * 对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。
 * 请你协助明明完成“去重”与“排序”的工作(同一个测试用例里可能会有多组数据，希望大家能正确处理)。
 * Input Param
 *
 * n               输入随机数的个数
 *
 * inputArray      n个随机整数组成的数组
 *
 *
 * Return Value
 *
 * OutputArray    输出处理后的随机整数
 *
 *
 *
 * 注：测试用例保证输入参数的正确性，答题者无需验证。测试用例不止一组
 *
 * @author: cliffcw
 * @create: 2019-08-25 11:52
 */
public class 明明的随机数 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {

            if ( line.trim().equals("")) {continue;}

            /**
             * 需要排序的数字最大个数
             */
            int num = Integer.parseInt(line);

            /**
             * 用treeSet容器本身大自动去重排序大功能
             */
            TreeSet<Integer> array = new TreeSet<Integer>();
            for (int i = 0; i < num; i++) {
                String readLine = bufferedReader.readLine();
                array.add(Integer.parseInt(readLine));
            }

            /**
             * 遍历输出
             */
            if (array != null && array.size() >0 ){
                Iterator<Integer> iterator = array.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            }
        }
    }
}
