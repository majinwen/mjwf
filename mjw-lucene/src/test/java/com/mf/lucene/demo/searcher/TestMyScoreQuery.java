package com.mf.lucene.demo.searcher;

import com.mf.lucene.util.MyScoreQuery;
import org.junit.Test;

/**
 * Created by user on 2016/7/26.
 */


public class TestMyScoreQuery {

    @Test
    public void test01(){
        MyScoreQuery msq = new MyScoreQuery();
        msq.searchByScoreQuery();

    }


    @Test
    public void test02(){
        MyScoreQuery msq = new MyScoreQuery();
        msq.searchByFilenameScoreQuery();
    }
}
