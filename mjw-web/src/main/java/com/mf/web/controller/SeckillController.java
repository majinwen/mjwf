package com.mf.web.controller;

import com.mf.common.dto.Exposer;
import com.mf.common.dto.SeckillExecution;
import com.mf.common.dto.SeckillResult;
import com.mf.common.entity.Seckill;
import com.mf.common.enums.SeckillStateEnum;
import com.mf.core.exception.RepeatKillException;
import com.mf.core.exception.SeckillCloseException;
import com.mf.core.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by pony on 2016/7/15.
 */

@Controller
@RequestMapping("/seckill")//url:模块/资源/{id}/细分
public class SeckillController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "seckill/list";
    }

    @RequestMapping(value = "{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if (seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "seckill/detail";
    }

  /*  @RequestMapping(value = "{seckillId}/exposer", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }
*/
    /**
     * 秒杀执行方法.
     * @param seckillId 秒杀商品ID
     * @param userPhone 秒杀用户手机
     * @param md5 秒杀Key
     * @return
     */
    @RequestMapping(value = "{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
                                                   @CookieValue(value = "userPhone",required = false)Long userPhone,
                                                   @PathVariable("md5")String md5){
        SeckillResult<SeckillExecution> result;
        SeckillExecution seckillExecution;

        if (userPhone == null){
            result = new SeckillResult<SeckillExecution>(false,"未注册");
        }else {
            try{
                // dao操作 seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                // procedure 操作
                seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
                result =  new SeckillResult<SeckillExecution>(true,seckillExecution);
            }catch (SeckillCloseException e){
                seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
                result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            }catch (RepeatKillException e){
                seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
                result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            }catch (Exception e){
                LOG.error(e.getMessage(),e);
                seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
                result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            }
        }

        return result;
    }

    /**
     * 获取系统时间
     * @param model
     * @return
     */
    @RequestMapping(value = "time/now",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> execute(Model model) {
        return new SeckillResult<Long>(true,new Date().getTime());
    }


}
