package com.mf.lucene.demo.searcher;

import com.mf.lucene.demo.advance_searcher.AdvanceSearch;
import com.mf.lucene.util.FileIndexUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 2016/7/26.
 */
public class TestAdvanceSearch {
     private AdvanceSearch st;

    @Before
    public void  init(){
        st = new AdvanceSearch();
    }

    @Test
    public  void  index(){
        FileIndexUtils.index(true);
    }

    @Test
    public void test01(){
        //以序号排序
        //st.searcher("java", Sort.INDEXORDER);
        //通过域来排序
        //st.searcher("java",new Sort(new SortField("filename",SortField.STRING)));
        st.searcherBySort("java",new Sort(new SortField("size",SortField.INT),new SortField("filename",SortField.STRING)));
    }
   @Test
    public void test02(){
       Filter tr = new TermRangeFilter("filename","test1.txt","test3.txt",true,true);
       tr = NumericRangeFilter.newIntRange("size",10,20,true,true);
       tr = new QueryWrapperFilter(new WildcardQuery(new Term("filename","*3.txt")));
       st.searcherByFilter("java",tr);
    }


    @Test
    public void test03(){
        Query q  = new WildcardQuery(new Term("filename","*3.txt"));
        st.searcherByQuery(q);
    }

}
