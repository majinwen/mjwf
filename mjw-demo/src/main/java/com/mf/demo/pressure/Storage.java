package com.mf.demo.pressure;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by user on 2016/7/24.
 */
public class Storage {

    BlockingQueue<LogPackge> queues = new LinkedBlockingQueue<LogPackge>();

    /**
     * 存储log
     * @param logPackge
     * @throws InterruptedException
     */
    public void saveLogPackge(LogPackge logPackge) throws InterruptedException {
        queues.put(logPackge);
    }

    /**
     * 读取log
     * @return
     * @throws InterruptedException
     */

    public LogPackge getLogPackgeAndRemove() throws InterruptedException {
        return queues.poll();
    }
    public LogPackge getLogPackge() {
        return  queues.peek();
    }

    public Boolean isEmpty() {
        return queues.isEmpty();
    }


    public int size(){
        return  queues.size();
    }

}
