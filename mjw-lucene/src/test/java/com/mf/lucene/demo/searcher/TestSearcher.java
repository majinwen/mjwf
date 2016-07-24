package com.mf.lucene.demo.searcher;

import com.mf.lucene.demo.searcher.SearcherUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 2016/7/24.
 */
public class TestSearcher {
    private SearcherUtil su;
   @Before
    public void init(){
        su = new SearcherUtil();
    }

  @Test
    public void  searcherByTerm(){
        su.searchByTerm("name","john",3);
    }
    @Test
    public void searcherByTermRange(){
        su.searchByTermRange("id","1","3",10);
    }
    @Test
    public void searcherByNumRange(){
        su.searchByNumricRange("attach",2,10,5);
    }
}
