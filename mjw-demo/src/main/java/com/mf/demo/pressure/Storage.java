package com.mf.demo.pressure;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by user on 2016/7/24.
 */
public class Storage {

    BlockingQueue<Log> queues = new LinkedBlockingQueue<Log>();

    /**
     * 存储log
     * @param log
     * @throws InterruptedException
     */
    public void saveLog(Log log) throws InterruptedException {
        queues.put(log);
    }

    /**
     * 读取log
     * @return
     * @throws InterruptedException
     */

    public Log getLog() throws InterruptedException {
        return queues.take();
    }


    public Boolean isEmpty() {
        return queues.isEmpty();
    }

}
