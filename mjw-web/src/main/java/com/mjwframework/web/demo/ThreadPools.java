package com.mjwframework.web.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pony on 2016/5/10.
 */
public class ThreadPools {
        public static final Executor remindPool = Executors.newFixedThreadPool(2);
}
