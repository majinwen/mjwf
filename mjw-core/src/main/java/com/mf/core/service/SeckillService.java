package com.mf.core.service;

import com.mf.common.dto.Exposer;
import com.mf.common.dto.SeckillExecution;
import com.mf.common.entity.Seckill;
import com.mf.core.exception.RepeatKillException;
import com.mf.core.exception.SeckillCloseException;
import com.mf.core.exception.SeckillException;

import java.util.List;


/**
 * 业务接口:站在"使用者"的角度设计接口
 * 1.方法的定义的粒度.2.参数.3.返回类型(return /异常)
 * Created by pony on 2016/7/14.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();


    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     * @return
     */
   // Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException
            , RepeatKillException, SeckillCloseException;

    /**
     * 存储过程执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * SeckillExecution
     *
     */
    SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5);
}
