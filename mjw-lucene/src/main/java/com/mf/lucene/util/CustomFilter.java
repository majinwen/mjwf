package com.mf.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.function.FieldScoreQuery;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/26.
 */
public class CustomFilter {

    public void searchByCustomFilter(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexUtils.getDirectory()));
            Query q = new TermQuery(new Term("content","java"));
            TopDocs tds = null;
            tds = searcher.search(q,new MyIdFilter(new FilterAccesser() {
                @Override
                public String[] values() {
                   // return new String[0];
                    return  new String[]{"1","5"};
                }

                @Override
                public String getField() {
                    return "id";
                }

                @Override
                public boolean set() {
                    return true;
                }
            }),3);
            for (ScoreDoc sd : tds.scoreDocs
                    ) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc
                        + ":(" + sd.score
                        + ")["
                        + d.get("filename")
                        + "【" + d.get("path")
                        + "】----" +d.get("score")+"----"+d.get("size")+"------------->"+ d.get("id") + "]");
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
