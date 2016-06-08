package com.mjwframework.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by pony on 2016/5/8.
 */

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @RequestMapping()
    public ModelAndView index() {
        ModelAndView v = new ModelAndView();
        String test = "测试传值！";
        v.setViewName("/demo/show");
        v.addObject("test",test);
        return v;
    }


    @RequestMapping("/show")
    public ModelAndView show() {
        ModelAndView v = new ModelAndView();
        v.setViewName("/demo/show");
        return v;
    }
}
