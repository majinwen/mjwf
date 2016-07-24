package com.mf.demo.pressure;

/**
 * Created by user on 2016/7/24.
 */
public class PressureTest {

    public static void main(String[] args) {
        String urlPath =  "D:/tmp/zonglong.txt";
        String logPath = "E:/Test/jjr/wjjr_d.log";
        String type = "0";
        int threadNumber = 1;
        int urlNumber = 2147483647;
        int seconds = 86400;

        Storage storage = new Storage();
        SaveLog saveLog = new SaveLog(storage, urlPath);
        Thread thread1 = new Thread(saveLog);
        ReadUrl readUrl = new ReadUrl(storage);
        Thread thread2 = new Thread(readUrl);
        thread1.start();
        thread2.start();

    }



}
