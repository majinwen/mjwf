package com.mf.web.demo;

import java.util.Scanner;

/**
 * Created by pony on 2016/7/7.
 *
 * 写一个程序：输入0-9之间的数，实现输出
   n*(n-1)*(n-2)*....*1;
   (n-1)*(n-2)*....*1;
   (n-2)*....*1;
    1;
 *
 */
public class jiechengshuchu {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int n = keyboard.nextInt();
        shuchu(n);
    }

    private static void shuchu(int n) {
        for (int i = n; i > 0; i--) {
            System.out.println(jiecheng(i));
        }
    }


    private static int jiecheng(int i) {
        if (i == 1) {
            return 1;
        } else {
            return i * jiecheng(i - 1);
        }
    }


}
