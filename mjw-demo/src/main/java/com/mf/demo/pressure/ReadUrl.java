package com.mf.demo.pressure;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/24.
 */
public class ReadUrl implements  Runnable{
    private Storage storage;
    private static final Pattern BLANK = Pattern.compile("[\\s　]+");
    private static final Pattern QUES = Pattern.compile("[?]");
    public static final DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    Runnable runn = null;
    private PressureInfo pressureInfo =null;
    ExecutorService exec;
    List<Log> logList;
    private PrintWriter logfile = null;

    public ReadUrl(Storage storage,String logPath,PressureInfo pressureInfo,ExecutorService exec) {
        this.storage = storage;
        this.pressureInfo = pressureInfo;
        this.exec = exec;
        try {
            logfile = new PrintWriter(new FileWriter(logPath, false), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            while (true) {
                if (!storage.isEmpty()) {
                    System.out.println("执行查询");
                   LogPackge logPackge = storage.getLogPackgeAndRemove();
                    if (logPackge != null) {
                        logList = logPackge.getLogList();
                        long startTime = System.currentTimeMillis();
                        for (Log log : logList
                                ) {
                            String url = log.getContent();

                            System.out.println("读取：" + url);
                            String[] strs = BLANK.split(url);
                            final String urlLink = strs[6];
                            System.out.println("=====urlLink===" + urlLink);
                            String ret = goUrl(urlLink);
                            if (!"0".equals(ret)) {
                                pressureInfo.addSuccessC();
                            }
                        }
                        long endTime = System.currentTimeMillis();
                        logfile.println("一组访问时间：" + (endTime - startTime));
                        logfile.flush();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String goUrl(String urlLink) {
        urlLink = "http://123.103.37.220:9980" + urlLink;
        //System.out.println("访问地址："+ urlLink);
        URL url = null;
        try {
            long startTime = System.currentTimeMillis();
            url = new URL(urlLink);
            long endTime = System.currentTimeMillis();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("访问："+ urlLink);
            BufferedReader r = null;
            String str = null;
            URLConnection con = url.openConnection();
            pressureInfo.addAllCount();

            r = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
           while(null !=(str = r.readLine())){
               System.out.println("返回："+ str);
                if(str.toString().indexOf("<timeUsed>")!=-1){
                    //System.out.println("访问结果：" + str.toString().substring(str.toString().indexOf("<timeUsed>") + 10, str.toString().indexOf("</timeUsed>")));
                   // System.out.println("返回："+ str);
                    if(storage.isEmpty()){
                        printCalculate();
                        System.exit(1);
                    }
                    return str.toString().substring(str.toString().indexOf("<timeUsed>") + 10, str.toString().indexOf("</timeUsed>"));
                }
            }

         // 判断是否执行完
        /* if(storage.isEmpty()){
              printCalculate();
              System.exit(1);
         }*/
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            //e1.printStackTrace();
            System.out.println("URL不可用");
        }
        return "0";
    }

    public void printCalculate() {
        exec.shutdown();
        System.out.println("开始统计结果请稍后！");
        System.out.println("并发测试线程数：" + pressureInfo.getThreadNumber());
        System.out.println("成功访问次数：" + pressureInfo.getSuccessC());
        System.out.println("总访问次数：" + pressureInfo.getAllCount());

    }
}

