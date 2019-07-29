package main.com.chenwan.coding2019.interview;

import java.util.Scanner;

/**
 * @program: base
 * @description: 一个整数的二进制中1出现的次数
 * @author: cliffcw
 * @create: 2019-07-23 19:04
 */
public class NumberOf1 {

    /**
     * 如果一个整数是2的整数次方，那么他的二进制表示中有且只有一位是1
     *
     * 一个整数的二进制位要改变多少位才能成为另外一个整数，可以用两个数做异或，再计算有多少个1
     */

    public static void main(String[] args) {

        while (true) {

            Scanner scanner = new Scanner(System.in);

            int i = numberOf1(scanner.nextInt());

//            int i = numberOfs1(scanner.nextInt());

            System.out.println(" ");
            System.out.println("count:" + i);
        }

    }

    /**
     * @Description: 输入一个整数判断这个整数的二进制表示里面有多少个1
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static int numberOf1(int n) {

        int count = 0;

        int flag = 1;

        while (flag > 0) {

            int i = n & flag;

            if (i > 0) {
                System.out.print(1 + ",");
                count++;
            } else {
                System.out.print(i + ",");

            }

            flag = flag << 1;

        }

        return count;
    }

    /**
     * @Description: 输入一个整数判断这个整数的二进制表示里面有多少个1
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static int numberOfs1(int n) {

        int count = 0;


        while (n != 0) {

            n = (n - 1) & n;
            count++;


        }

        return count;
    }

    public static void printNumberByte(int n) {

        while (n != 0) {


        }
    }
}
