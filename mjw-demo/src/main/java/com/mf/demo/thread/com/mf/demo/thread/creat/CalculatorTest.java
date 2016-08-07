package com.mf.demo.thread.com.mf.demo.thread.creat;

import com.mf.demo.thread.com.mf.demo.thread.creat.Calculator;

/**
 * Created by user on 2016/7/23.
 */
public class CalculatorTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }

    }

}
