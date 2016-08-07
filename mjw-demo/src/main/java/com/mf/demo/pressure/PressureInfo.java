package com.mf.demo.pressure;

/**
 * Created by user on 2016/7/25.
 */
public class PressureInfo {

    private  int allCount = 0;
    private  int successC = 0;
    private  int threadNumber = 0;


    public int getAllCount() {
        return allCount;
    }

    public int getSuccessC() {
        return successC;
    }

    public synchronized void addAllCount(){
        allCount ++;
    }

    public synchronized void addSuccessC(){
        successC ++;
    }


    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

}
