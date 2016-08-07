package com.mf.core.service.impl;

import com.mf.common.dto.Exposer;
import com.mf.common.dto.SeckillExecution;
import com.mf.common.entity.Seckill;
import com.mf.common.entity.SuccessKilled;
import com.mf.common.enums.SeckillStateEnum;
import com.mf.core.dao.SeckillDao;
import com.mf.core.dao.SuccessKilledDao;
import com.mf.core.dao.redis.RedisDao;
import com.mf.core.exception.RepeatKillException;
import com.mf.core.exception.SeckillCloseException;
import com.mf.core.exception.SeckillException;
import com.mf.core.service.SeckillService;

import org.apache.commons.collections.MapUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pony on 2016/7/14.
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    private final static Logger LOG = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

   // @Autowired
    private RedisDao redisDao;

    //md5盐值字符串,用于混淆md5
    private final String slat = "JHJÓGJ$%^&*(wew34567eIHFv456789KBMB#$%^&*";

    public  List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化到缓存 先查缓存，然后缓存到redis.

        /**
         * get from redis
         * if null
         * get db
         * else
         *   put redis
         *
         */
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false, seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        Date nowTime = new Date();

        if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }


    /**
     * 使用注解控制事务的优点:
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格.
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者玻璃到事务方法外部.
     * 3.不是所有的方法都需要事务.如一些查询的service.只有一条修改操作的service.
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (StringUtils.isEmpty(md5) || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑:1.减库存.2.记录购买行为
        Date nowTime = new Date();

        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

            if (updateCount <= 0) {
                throw new SeckillCloseException("seckill is closed!");
            } else {
                //记录购买行为
                int inserCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

                if (inserCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated!");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            //所有的编译期异常转化为运行期异常,spring的声明式事务做rollback
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
    }

    /**
     * 获取MD5值
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStateEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        //存储过程执行完之后result被赋值
        try {
            seckillDao.killByProcedure(map);
            //获取result
            int result = MapUtils.getInteger(map, "result", -2);
            if (result==1) {
                SuccessKilled sk = successKilledDao.
                        queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
        }
    }}
