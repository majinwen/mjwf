package com.mf.lucene.demo.searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;

import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by user on 2016/7/24.
 */
public class SearcherUtil {
    private Directory directory;
    private IndexReader reader;
    private String[] ids = {"1", "2", "3", "4", "5", "6"};
    private String[] emails = {"aa@itat.org", "bb@itat.org", "cc@sina.com", "dd@qq.com", "ee@dd.com", "ff@sina.com"};
    private String[] contents = {"welcome to visit my space", "hello boy", "my name is cc", "i like football",
            "i like coffe", "i like movie"};
    private Date[] dates = null;
    private int[] attachs = {2, 3, 1, 4, 5, 5};
    private String[] names = {"zhangsan", "lisi", "wangwu", "john", "jetty", "jack"};


    private Map<String,Float> scores = new HashMap<String, Float>();

    public SearcherUtil() {
        directory =  new RAMDirectory();
        setDate();
        index();
    }

    public void  index(){
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
            Document doc = null;
            for (int i = 0; i < ids.length; i++) {
                doc = new Document();
                doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email", emails[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("content", contents[i], Field.Store.NO, Field.Index.ANALYZED));
                doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new NumericField("attach", Field.Store.YES, true).setIntValue(attachs[i]));
                doc.add(new NumericField("date", Field.Store.YES, true).setLongValue(dates[i].getTime()));
                String et = emails[i].substring(emails[i].lastIndexOf("@") + 1);
                System.out.println(et);
                if (scores.containsKey(et)) {
                    doc.setBoost(scores.get(et));
                } else {
                    doc.setBoost(0.5f);
                }
                writer.addDocument(doc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer !=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }




    public void  setDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dates = new Date[ids.length];
            dates[0] =  sdf.parse("2010-02-19");
            dates[1] =  sdf.parse("2011-02-19");
            dates[2] = sdf.parse("2012-02-19");
            dates[3] = sdf.parse("2013-02-19");
            dates[4] =  sdf.parse("2014-02-19");
            dates[5] =  sdf.parse("2015-02-19");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public IndexSearcher getSearcher() {
        try {
            if (reader == null) {
                reader = IndexReader.open(directory);
            } else {
                IndexReader tr = IndexReader.openIfChanged(reader);
                if (tr != null) {
                    reader.close();
                    reader = tr;
                }

            }
            return new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void searchByTerm(String field, String name, int num) {
        try {
            IndexSearcher searcher = getSearcher();
            Query query = new TermQuery(new Term(field, name));
            TopDocs tds = searcher.search(query, num);
            System.out.println("一共查询了：" + tds.totalHits);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                System.out.println("(" + sd.doc + ")" + doc.get("name") + "[" + doc.get("email") + "] ----->" + doc.get("id"));
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchByTermRange(String field,String start,String end,int num){
        try {
            IndexSearcher searcher = getSearcher();
            Query query = new TermRangeQuery(field,start,end,true,true);
            TopDocs tds = searcher.search(query, num);
            System.out.println("一共查询了：" + tds.totalHits);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                System.out.println("(" + sd.doc + ")" + doc.get("name") + "[" + doc.get("email") + "] ----->" + doc.get("id"));
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  public void  searchByNumricRange(String field,int start,int end,int num){
      try {
          IndexSearcher searcher = getSearcher();
          Query query =  NumericRangeQuery.newIntRange(field,start,end,true,true);
          TopDocs tds = searcher.search(query, num);
          System.out.println("一共查询了：" + tds.totalHits);
          for (ScoreDoc sd : tds.scoreDocs) {
              Document doc = searcher.doc(sd.doc);
              System.out.println("(" + sd.doc + ")" + doc.get("name") + "[" + doc.get("email") + "] ----->" + doc.get("id"));
          }
          searcher.close();
      } catch (IOException e) {
          e.printStackTrace();
      }


  }

    public void  searchByQueryParse(Query query,int num){
        try {
            IndexSearcher searcher = getSearcher();
           // Query query =  NumericRangeQuery.newIntRange(field,start,end,true,true);
            TopDocs tds = searcher.search(query, num);
            System.out.println("一共查询了：" + tds.totalHits);
            for (ScoreDoc sd : tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                System.out.println("(" + sd.doc + ")" + doc.get("name") + "[" + doc.get("email") + "] ----->" + doc.get("id"));
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}



