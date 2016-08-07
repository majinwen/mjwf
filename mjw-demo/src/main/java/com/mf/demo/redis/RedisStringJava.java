package com.mf.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by pony on 2016/8/7.
 */
public class RedisStringJava {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //设置 redis 字符串数据
        jedis.set("w3ckey", "Redis tutorial");
        // 获取存储的数据并输出
        System.out.println("Stored string in redis:: "+ jedis.get("w3ckey"));
    }

}
