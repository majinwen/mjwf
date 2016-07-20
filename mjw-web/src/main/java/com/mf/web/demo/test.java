package com.mf.web.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pony on 2016/7/7.
 */
public class test {


    public void dd() {

        List<String> strList = new ArrayList<>();
        strList.add("1");

    }


    /**
     * 集合转换为一维数组
     */
    public void listToArray() {
//创建List并添加元素
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("3");
        list.add("4");

//利用froeach语句输出集合元素
        System.out.println("----2----froeach语句输出集合元素");
        for (String x : list) {
            System.out.println(x);
        }


        //将ArrayList转换为数组
        Object s[] = list.toArray();

//利用froeach语句输出集合元素
        System.out.println("----2----froeach语句输出集合转换而来的数组元素");
        for (Object x : s) {
            System.out.println(x.toString()); //逐个输出数组元素的值
        }


    }


    }
