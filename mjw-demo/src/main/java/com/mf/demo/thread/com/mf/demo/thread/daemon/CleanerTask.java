package com.mf.demo.thread.com.mf.demo.thread.daemon;

import java.util.Date;
import java.util.Deque;

/**
 * Created by user on 2016/7/23.
 */
public class CleanerTask extends  Thread{
    private Deque<Event> deque;
    public CleanerTask(Deque<Event> deque){
        this.deque = deque;
        setDaemon(true);
    }

    public void run(){
        while(true){
            Date date = new Date();
            clean(date);
        }



    }
     public void clean(Date date){
     long difference;
         boolean delete;
         if(deque.size() == 0){
             return;
         }

     }

}
