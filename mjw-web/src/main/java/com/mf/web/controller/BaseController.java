package com.mf.web.controller;

import com.mf.core.exception.BusinessException;
import com.mf.common.constants.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pony on 2016/5/8.
 */

@Controller
@RequestMapping
public class BaseController {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    protected HttpServletRequest request;

    /**
     *
     */
    public BaseController() {
    }


  /*protected String getContextPath() {
        return (String) request.getServletContext().getAttribute("contextPath");
    }
*/
    /**
     * 获取所有错误提示信息。
     *
     * @param result
     * @return
     */
    protected List<String> getAllErrorMsg(BindingResult result) {
        List<String> messages = new ArrayList<>();
        for (ObjectError oe : result.getAllErrors()) {
            messages.add(oe.getDefaultMessage());
        }
        return messages;
    }

    /**
     * 创建操作创建提示结果
     *
     * @return
     */
    protected Map<String, Object> createSuccess() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ResultCode.SUCCESS);
        result.put("msg", "操作成功");
        return result;
    }

    /**
     * 创建操作成功提示结果
     *
     * @param message 成功提示语
     * @return
     */
    protected Map<String, Object> createSuccess(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ResultCode.SUCCESS);
        if (StringUtils.isNotBlank(message)) {
            result.put("msg", message);
        } else {
            result.put("msg", "操作成功");
        }
        return result;
    }

    /**
     * 创建操作失败提示结果
     *
     * @param code
     * @param msg
     * @return
     */
    protected Map<String, Object> createError(String code, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    /**
     * 创建系统异常操作结果
     *
     * @return
     */
    protected Map<String, Object> createSystemError() {
        return createError(ResultCode.SYSTEM_ERROR, "操作失败");
    }

    /**
     * 创建参数错误操作结果
     *
     * @param msg
     * @return
     */
    protected Map<String, Object> createParamError(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = "参数错误";
        }
        return createError(ResultCode.PARAM_ERROR, msg);
    }

    protected interface InvokerCallback<T> {
        T invoker(T v);
    }
    protected <T extends ModelAndView> T doInvokerForView(T v, InvokerCallback<T> call) {
        try {
            return call.invoker(v);
        } catch (BusinessException e) {
            logger.error(this.getClass().getSimpleName() +" business error,code:" + e.getCode() + ",msg:" + e.getMessage());
            v.addObject("result", e.getMessage());
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " system error,code:" + ResultCode.SYSTEM_ERROR + ",msg:" + exception2String(e));
            logger.error(this.getClass().getSimpleName() + " system error,code:" + ResultCode.SYSTEM_ERROR + ",msg:" + e.getMessage());
            v.addObject("result", exception2String(e));
        }
        v.setViewName("/demo/show");
        return v;
    }

    protected Map<String, Object> doInvokerForJson(Map<String, Object> map, InvokerCallback<Map<String, Object>> call) {
        try {
            return call.invoker(map);
        } catch (BusinessException e) {
            logger.error(this.getClass().getSimpleName() + " business error,code:" + e.getCode() + ",msg:" + e.getMessage());
            return createError(ResultCode.BUSINESS_ERROR, e.getMessage());
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " system error,code:" + ResultCode.SYSTEM_ERROR + ",msg:" + exception2String(e));
            logger.error(this.getClass().getSimpleName() + " system error,code:" + ResultCode.SYSTEM_ERROR + ",msg:" + e.getMessage());
            return createError(ResultCode.SYSTEM_ERROR, exception2String(e));
        }
    }

    /**
     * 临时方法， 用于打印异常栈, 正式环境去除
     * @param e
     * @return
     */
    private String exception2String(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
