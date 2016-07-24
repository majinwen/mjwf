package com.mf.demo.pressure;

/**
 * Created by user on 2016/7/24.
 */
public class Record {
    String startTime = null;
    String endTime = null;
    int methodsTime = 0;
    int resultsNum = 0;

    public Record(String startTime, String endTime, int methodsTime, int resultsNum) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.methodsTime = methodsTime;
        this.resultsNum = resultsNum;
    }

}
