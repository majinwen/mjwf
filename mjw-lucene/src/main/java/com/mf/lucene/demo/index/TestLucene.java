package com.mf.lucene.demo.index;

import com.mf.lucene.demo.HelloLucene;

/**
 * Created by user on 2016/7/21.
 */
public class TestLucene {
     private  void  TestLucene(){
         HelloLucene h = new HelloLucene();
         h.index();
     }

     public  static void main(String[] agrs){
       //  HelloLucene h = new HelloLucene();
        //测试创建索引
       //  h.index();
         //测试搜索
       //  h.searcher();

         //程序创建索引
        IndexUtil iu = new IndexUtil();
        // iu.index();
      iu.search02();


     }

}
