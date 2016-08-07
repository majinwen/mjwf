package com.mf.lucene.demo.searcher;

import com.mf.lucene.util.CustomParserUtil;
import org.junit.Test;

/**
 * Created by user on 2016/7/26.
 */
public class TestCustomParserUtil {
    @Test
    public void test01(){
        CustomParserUtil ctu = new CustomParserUtil();
       // ctu.searcherByQuery("java~");
        ctu.searcherByQuery("date:[2016-05-20 TO 2016-08-20]");
    }





}
