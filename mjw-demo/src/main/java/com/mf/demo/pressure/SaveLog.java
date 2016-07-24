package com.mf.demo.pressure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by user on 2016/7/24.
 */
public class SaveLog implements Runnable{

     private  Storage storage;
     private  String urlPaths;

    public SaveLog(Storage storage ,String urlPaths) {
        this.storage = storage;
        this.urlPaths = urlPaths;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(urlPaths), "GBK"));
            String line = reader.readLine();
            while (line != null) {
                Log log = new Log();
                log.setContent(line);
                System.out.println("=====line=====:" + line.toString());
                String time = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                if (storage.isEmpty()) {
                    storage.saveLog(log);
                } else {
                    Log firstlog = storage.getLog();
                    String first = firstlog.getContent();
                    if (time.equals(first.substring(first.indexOf("[") + 1, first.indexOf("]")))) {
                        storage.saveLog(log);
                    } else {
                        try {
                            Thread.sleep(1000);
                            //wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            this.printCalculate();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void printCalculate() {
        exec.shutdown();

        while(!exec.isTerminated()) {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException var18) {
                var18.printStackTrace();
            }
        }

        System.out.println("开始统计结果请稍后！");
        System.out.println("当前线程池数：" + ((ThreadPoolExecutor)exec).getPoolSize());
        HashMap SecondsAge = new HashMap(10);
        Integer c = null;
        String starts = null;
        String ends = null;
        int maxTime = -2147483648;
        int minTime = 2147483647;
        int successC = 0;
        boolean tmpTime = false;
        int allTime = 0;

        for(Iterator maxCount = recordList.iterator(); maxCount.hasNext(); ++successC) {
            Record minCount = (Record)maxCount.next();
            long i$ = Long.parseLong(minCount.startTime.substring(0, 17));
            long endTime = Long.parseLong(minCount.endTime.substring(0, 17));
            int var19 = (int)(endTime - i$);
            if(maxTime < var19) {
                maxTime = var19;
            }

            if(minTime > var19) {
                minTime = var19;
            }

            allTime += var19;
            starts = minCount.startTime.substring(0, 14);
            ends = minCount.endTime.substring(0, 14);
            if(starts.equals(ends)) {
                c = (Integer)SecondsAge.get(starts);
                if(null == c) {
                    c = Integer.valueOf(0);
                }

                c = Integer.valueOf(c.intValue() + 1);
                SecondsAge.put(starts, c);
            }

            if(minCount.resultsNum == 0) {
                ++zeroNum;
            }
        }

        int var20 = -2147483648;
        int var21 = 2147483647;
        Iterator var22 = SecondsAge.entrySet().iterator();

        while(var22.hasNext()) {
            Map.Entry map = (Map.Entry)var22.next();
            if(var20 < ((Integer)map.getValue()).intValue()) {
                var20 = ((Integer)map.getValue()).intValue();
            }

            if(var21 > ((Integer)map.getValue()).intValue()) {
                var21 = ((Integer)map.getValue()).intValue();
            }
        }

        System.out.println("并发测试线程数：" + this.threadNumber);
        System.out.println("每秒平均访问次数：" + successC / SecondsAge.size());
        System.out.println("成功访问次数：" + successC);
        System.out.println("结果为0时次数：" + zeroNum);
        System.out.println("总访问次数：" + this.allCount);
        System.out.println("访问用时：" + SecondsAge.size() + "秒钟");
        System.out.println("每秒最大访问次数：" + var20);
        System.out.println("每秒最小访问次数：" + var21);
        System.out.println("dubbo最大访问时间：" + maxTime);
        System.out.println("dubbo最小访问时间：" + minTime);
        System.out.println("dubbo平均访问时间：" + allTime / SecondsAge.size() / this.threadNumber);
        System.out.println("search最大访问时间：" + this.maxMethodsTime);
        System.out.println("search最小访问时间：" + this.minMethodsTime);
        System.out.println("search平均访问时间：" + this.allMethodsTime / SecondsAge.size() / this.threadNumber);
    }



}


