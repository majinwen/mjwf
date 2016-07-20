package com.mf.core.exception;

/**
 * 秒杀关闭异常
 * Created by pony on 2016/7/14.
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
