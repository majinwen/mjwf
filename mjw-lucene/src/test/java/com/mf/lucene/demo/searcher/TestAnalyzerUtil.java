package com.mf.lucene.demo.searcher;

import com.mf.lucene.demo.analyzer.AnalyzerUtil;
import com.mf.lucene.demo.analyzer.MySameAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * Created by user on 2016/7/28.
 */
public class TestAnalyzerUtil {

    @Test
    public void test01() {
        Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
        String str = "this is my house,I am come from china,football is good,everone is like it";

        AnalyzerUtil.displayToken(str,a1);
        AnalyzerUtil.displayToken(str,a2);
        AnalyzerUtil.displayToken(str,a3);
        AnalyzerUtil.displayToken(str,a4);


    }

@Test
public void test02() {
    Analyzer a2 = new MySameAnalyzer();
    String str = "中国北京天安门上海广州南京北极";
    AnalyzerUtil.displayToken(str, a2);
}

}
