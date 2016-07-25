package com.mf.demo.pressure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * Created by user on 2016/7/24.
 */
public class SaveLog implements Runnable{

     private  Storage storage;
     private  String urlPaths;
    private PressureInfo pressureInfo;
    private  boolean listFlag = false;
    ExecutorService exec;
    BufferedReader reader = null;
    String line = null;
    List<Log> logList = null;

    public SaveLog(Storage storage ,String urlPaths ,PressureInfo pressureInfo,ExecutorService exec) {
        this.storage = storage;
        this.urlPaths = urlPaths;
        this.pressureInfo =  pressureInfo;
        this.exec = exec;
    }

    @Override
    public void run() {

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(urlPaths), "GBK"));
            logList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                System.out.println("=====line=====:" + line.toString());
                Log log = new Log();
                log.setContent(line);
                if(listFlag){
                    logList = new ArrayList<>();
                    listFlag = false;
                }
                String time = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                if(logList.isEmpty()){
                    logList.add(log);
                }else{
                    Log firstlog = logList.get(0);
                    String first = firstlog.getContent();
                    if (time.equals(first.substring(first.indexOf("[") + 1, first.indexOf("]")))) {
                        logList.add(log);
                    } else {
                        LogPackge logPackge = new LogPackge();
                        logPackge.setLogList(logList);
                        storage.saveLogPackge(logPackge);
                        System.out.println("storage==="+ storage.size());
                        listFlag = true;

                    }

                }

            }

           // this.printCalculate();
            //System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void printCalculate() {
        //exec.shutdown();
        System.out.println("开始统计结果请稍后！");
        System.out.println("成功访问次数：" + pressureInfo.getSuccessC());
        System.out.println("总访问次数：" + pressureInfo.getAllCount());
    }



}


