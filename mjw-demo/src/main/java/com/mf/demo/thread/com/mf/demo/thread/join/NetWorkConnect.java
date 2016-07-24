package com.mf.demo.thread.com.mf.demo.thread.join;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2016/7/23.
 */
public class NetWorkConnect implements Runnable{
    @Override
    public void run() {
        System.out.printf("NetWorkConnect start: %s\n ", new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf(" NetWorkConnect end : %s\n ", new Date());

    }
}
