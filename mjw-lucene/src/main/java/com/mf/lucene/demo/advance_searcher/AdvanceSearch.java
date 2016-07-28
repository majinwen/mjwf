package com.mf.lucene.demo.advance_searcher;

import com.mf.lucene.util.FileIndexUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/26.
 */
public class AdvanceSearch {
    private static IndexReader reader = null;
    static {
        try {
            reader = IndexReader.open(FileIndexUtils.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexSearcher getSearcher(){
        try {
            if(reader == null){
                reader = IndexReader.open(FileIndexUtils.getDirectory());
            }else{
                IndexReader tr = IndexReader.openIfChanged(reader);
                if(tr != null){
                    reader.close();
                    reader = tr;
                }
            }
            return new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public void searcherBySort(String querystr, Sort sort) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            IndexSearcher searcher = getSearcher();
            QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
            Query query = parser.parse(querystr);
            TopDocs tds = null;
            if (sort != null) {
                tds = searcher.search(query, 3, sort);
            } else {
                tds = searcher.search(query, 3);
            }
            for (ScoreDoc sd : tds.scoreDocs
                    ) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc
                        + ":(" + sd.score
                        + ")["
                        + d.get("filename")
                        + "【" + d.get("path")
                        + "】----" +d.get("score")+"----"+d.get("size")+"----"+ sdf.format(new Date(Long.valueOf(d.get("date")))) + "]");
            }
            searcher.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void searcherByFilter(String querystr,Filter filter) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            IndexSearcher searcher = getSearcher();
            QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
            Query query = parser.parse(querystr);
            TopDocs tds = null;
            if (filter != null) {
                tds = searcher.search(query, filter , 3);
            } else {
                tds = searcher.search(query, 3);
            }
            for (ScoreDoc sd : tds.scoreDocs
                    ) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ")[" + d.get("filename") + "【" + d.get("path") + "】" + "]");
            }
            searcher.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searcherByQuery(Query querystr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            IndexSearcher searcher = getSearcher();
            TopDocs tds = null;
            tds = searcher.search(querystr, 3);
            for (ScoreDoc sd : tds.scoreDocs
                    ) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ")[" + d.get("filename") + "【" + d.get("path") + "】" + "]");
            }
            searcher.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
