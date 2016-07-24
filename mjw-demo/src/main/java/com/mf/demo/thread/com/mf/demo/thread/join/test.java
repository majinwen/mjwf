package com.mf.demo.thread.com.mf.demo.thread.join;

import java.util.Date;

/**
 * Created by user on 2016/7/23.
 */
public class test {
    public static  void  main(String[] args){
        DataSourcesLoader dataSourcesLoader = new DataSourcesLoader();
        Thread thread1 = new Thread(dataSourcesLoader,"DataSourcesLoader");
        NetWorkConnect netWorkConnect = new NetWorkConnect();
        Thread thread2 = new Thread(netWorkConnect,"NetWorkConnect");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //DataSourcesLoader线程运行结束，NetWorkConnect线程也运行结束，主线程才会继续运行并打印出信息
     System.out.printf("Main: Configuration has been loaded : %s\n",new Date());

    }

}
