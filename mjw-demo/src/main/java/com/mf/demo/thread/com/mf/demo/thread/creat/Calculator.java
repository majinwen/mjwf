package com.mf.demo.thread.com.mf.demo.thread.creat;

/**
 * Created by user on 2016/7/23.
 */
public class Calculator implements Runnable{
    private  int number;
    public Calculator(int number){
      this.number = number;
    }
    @Override
    public void run(){
        for(int i=1;i<10;i++){
            System.out.printf("%s : %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
        }

    }

}
