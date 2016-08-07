package com.mf.demo.thread.com.mf.demo.thread.join;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2016/7/23.
 */
public class DataSourcesLoader  implements  Runnable{
    @Override
    public void run() {
        System.out.printf("DataSourcesLoader start: %s\n ", new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("DataSourcesLoader end: %s\n ", new Date());

    }


}
