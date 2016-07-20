package com.mf.web.controller;

import com.mf.common.model.UserInfo;
import com.mf.core.dao.UserDao;
import com.mf.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by pony on 2016/5/8.
 */

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
    @Autowired
    UserService  userService;

    @RequestMapping("/show")
    public ModelAndView index() {
        ModelAndView v = new ModelAndView();
        String test = "测试传值！";
        List<UserInfo> users = userService.selectUserList();
       // UserInfo user= userService.getUserById("001");
        v.setViewName("/demo/show");
        v.addObject("users",users);
        v.addObject("test",test);
        return v;
    }


  /*  @RequestMapping("/show")
    public ModelAndView show() {
        ModelAndView v = new ModelAndView();
        v.setViewName("/demo/show");
        return v;
    }*/

    public static void main(String args[]) {
        System.out.println("hello world!");
        System.out.println("这是个实验！");

    }


}
