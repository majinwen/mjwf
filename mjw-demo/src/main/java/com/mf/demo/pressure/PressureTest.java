package com.mf.demo.pressure;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by user on 2016/7/24.
 */
public class PressureTest {

    public static void main(String[] args) {
         // 日志地址
        String urlPath = "D:/tmp/zonglong.txt";
         // 打印日志地址
        String logPath = "D:/tmp/wjjr_d.log";
        //线程数
        int threadNumber = 5;

        ExecutorService exec;

        exec = Executors.newFixedThreadPool(threadNumber);

        Storage storage = new Storage();
        PressureInfo pressureInfo = new PressureInfo();
        pressureInfo.setThreadNumber(threadNumber);
        SaveLog saveLog = new SaveLog(storage,urlPath,pressureInfo,exec);
        Thread thread1 = new Thread(saveLog);
        ReadUrl readUrl = new ReadUrl(storage,logPath,pressureInfo,exec);
        Thread thread2 = new Thread(readUrl);
        thread1.start();
        exec.execute(thread2);
       // thread2.start();
    }



}
