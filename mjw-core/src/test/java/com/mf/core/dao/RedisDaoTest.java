package com.mf.core.dao;

import com.mf.common.entity.Seckill;
import com.mf.core.dao.redis.RedisDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pony on 2016/8/7.
 */

@ContextConfiguration("classpath:xf.core.redis.xml")
public class RedisDaoTest  extends TestCase{
   /* private long id = 1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
 private  SeckillDao seckillDao;
    @Test
    public void testSeckill() throws Exception {
        //get and put
        Seckill seckill = redisDao.getSeckill(id);
        if(seckill == null){
            seckill= seckillDao.queryById(id);
            if(seckill != null){
             String result =   redisDao.putSeckill(seckill);
               System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }*/
}
