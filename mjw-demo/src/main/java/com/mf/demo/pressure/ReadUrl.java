package com.mf.demo.pressure;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/24.
 */
public class ReadUrl implements  Runnable{
    private Storage storage;

    private static final Pattern BLANK = Pattern.compile("[\\s　]+");
    private static final Pattern QUES = Pattern.compile("[?]");
    private PrintWriter logfile = null;
    Runnable runn = null;
    private static ExecutorService exec = null;
   private static List<Record> recordList = new ArrayList();
    private String urlPaths = null;
    private int allCount = 0;
    private int threadNumber = 1;
    private int urlNumber = 2147483647;
    private int seconds = 86400;
    private static boolean timingAction = true;
    private static int zeroNum = 0;
    private static int actionTime = 10;
    int maxMethodsTime = -2147483648;
    int minMethodsTime = 2147483647;
    int allMethodsTime = 0;
    public static final DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    private static int i = 0;

    public ReadUrl(Storage storage) {
        this.storage = storage;
    }
    @Override
    public void run(){
        try {
            int urlCount = 0;
            timing(this.seconds);
            boolean threadCount = false;
            String url = storage.getLog().getContent();
            runn = createBean(url);
            if(null != runn) {
                exec.execute(runn);
                ++this.allCount;
                ++urlCount;
            }
           // put(long startTime, long endTime, int methodsTime, int resultsNum) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }









    public Runnable createBean(String url) {
        //System.out.println("====url====:"+ url);
        String[] strs = BLANK.split(url);
        if (url.indexOf("reload") != -1) {
            return null;
        }
        if (url.indexOf("\"POST") != -1) {
            return null;
        }

        if (null != strs && strs.length > 6) {
            String[] para = QUES.split(strs[6]);
            if (para.length < 2) {
                return null;
            }
            final String urlLink = strs[6];
            return new Thread() {
                public void run() {
                    long startTime = System.currentTimeMillis();
                    String ret = goUrl(urlLink);
                    long endTime = System.currentTimeMillis();
                    logfile.println(format2.format(new Date()) + "__" + Thread.currentThread().getName() + "__" + "000" + "__" + ret + "__" + (endTime - startTime));
                    logfile.flush();
                    put(startTime, endTime, Integer.parseInt(ret), -1);
                }
            };
        }
        return null;
    }

    public String goUrl(String urlLink) {
        urlLink = "http://123.103.37.220:9980" + urlLink;
        // System.out.println("访问地址："+ urlLink);
        URL url = null;
        try {
            url = new URL(urlLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader r = null;
        String str = null;
        try {
            URLConnection con = url.openConnection();
            r = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
            while(null !=(str = r.readLine())){
                if(str.toString().indexOf("<timeUsed>")!=-1){
                    return str.toString().substring(str.toString().indexOf("<timeUsed>") + 10, str.toString().indexOf("</timeUsed>"));
                }
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "0";
    }



    public void put(long startTime, long endTime, int methodsTime, int resultsNum) {
        synchronized(this) {
            ++i;
            recordList.add(new Record(format2.format(new java.sql.Date(startTime)) + i, format2.format(new java.sql.Date(endTime)) + i, methodsTime, resultsNum));
            if(methodsTime > this.maxMethodsTime) {
                this.maxMethodsTime = methodsTime;
            }

            if(methodsTime < this.minMethodsTime) {
                this.minMethodsTime = methodsTime;
            }

            this.allMethodsTime += methodsTime;
            actionTime = methodsTime;
        }
    }



    public static void timing(int seconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------执行时间到！--------");
                timingAction = false;
            }
        }, (long)(seconds * 1000));
    }


}

