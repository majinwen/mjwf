package com.mf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pony on 2016/5/8.
 */

@Controller
public class IndexController extends  BaseController{


    /**
     *
     */
    public IndexController() {
    }

    /**
     * 进去登录页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logon")
    public String initLogin(HttpServletRequest request,
                            HttpServletResponse response) {
        logger.info("init logon.do logon_url");


        return "logon";
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        logger.debug("访问index页面");

    /* String user ="admin";
        String password = "manager";
        String smartbiURL = "http://localhost:28080/spreadsheet/vision/";
        ClientConnector conn = new ClientConnector(smartbiURL);
        // 建立此连接时，就对smartbi进行了登录
        boolean ret = conn.open(user, password);

        if (!ret) {
            System.out.println("=======================");
            System.out.println("登录报表成功！");

        }*/

        System.out.print("========================================");

        return "index";
    }


    @RequestMapping(value= "/forbidden")
    public String forbidden() {
        return "403Forbidden";
    }

    @RequestMapping(value= "/notFound")
    public String notFound() {
        return "404NotFound";
    }

    @RequestMapping(value= "/internalServerError")
    public String internalServerError() {
        return "500InternalServerError";
    }


}
