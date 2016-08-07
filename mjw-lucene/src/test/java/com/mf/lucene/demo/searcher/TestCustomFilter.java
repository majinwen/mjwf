package com.mf.lucene.demo.searcher;

import com.mf.lucene.util.CustomFilter;
import org.junit.Test;

/**
 * Created by user on 2016/7/26.
 */
public class TestCustomFilter {
    @Test
    public  void test01(){
        CustomFilter cf = new CustomFilter();
        cf.searchByCustomFilter();

    }

}
