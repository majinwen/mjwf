package com.mf.lucene.demo.searcher;

import com.mf.lucene.demo.searcher.SearcherUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 2016/7/24.
 */
public class TestSearcher {
    private SearcherUtil su;

    @Before
    public void init() {
        su = new SearcherUtil();
    }

    @Test
    public void searcherByTerm() {
        su.searchByTerm("name", "john", 3);
    }

    @Test
    public void searcherByTermRange() {
        su.searchByTermRange("id", "1", "3", 10);
    }

    @Test
    public void searcherByNumRange() {
        su.searchByNumricRange("attach", 2, 10, 5);
    }

    @Test
    public void searcherByParse() throws ParseException {
        QueryParser queryParser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
        Query query = queryParser.parse("like");
        su.searchByQueryParse(query,10);
    }

}
