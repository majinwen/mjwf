package com.mf.lucene.demo.index;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016/7/21.
 */
public class IndexUtil {
    private String[] ids = {"1", "2", "3", "4", "5", "6"};
    private String[] emails = {"aa@itat.org", "bb@itat.org", "cc@sina.com", "dd@qq.com", "ee@dd.com", "ff@sina.com"};
    private String[] contents = {"welcome to visit my space", "hello boy", "my name is cc", "i like football",
            "i like coffe", "i like movie"};
    private javax.xml.crypto.Data[] dates = null;
    private int[] attachs = {2, 3, 1, 4, 5, 5};
    private String[] names = {"zhangsan", "lisi", "wangwu", "john", "jetty", "jack"};
    private Directory directory = null;

    private Map<String,Float> scores = new HashMap<String, Float>();
    private static  IndexReader reader = null;


    public  IndexUtil(){
        try {
            scores.put("itat.org", 2.0f);
            scores.put("sina.com", 1.5f);
            //setDate();
            directory = FSDirectory.open(new File("D:/tmp/indextest/index02"));
            reader = IndexReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void  setDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dates = new javax.xml.crypto.Data[ids.length];
            dates[0] = (Data) sdf.parse("2010-02-19");
            dates[1] = (Data) sdf.parse("2011-02-19");
            dates[2] = (Data) sdf.parse("2012-02-19");
            dates[3] = (Data) sdf.parse("2013-02-19");
            dates[4] = (Data) sdf.parse("2014-02-19");
            dates[5] = (Data) sdf.parse("2015-02-19");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }*/


    public IndexSearcher getSearcher(){
        try {
            if(reader == null){
                reader = IndexReader.open(directory);
            }else {

               IndexReader tr = IndexReader.openIfChanged(reader);
                if(tr !=null){
                    reader.close();
                    reader = tr;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(reader);
    }



    public void  merge(){
     IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
            //会将索引合并为2段，这两段中被删除的数据会被清空
            //特别注意：此处Lucene在3.5 后不建议使用，应为会消耗大量的开销，Lucene会根据情况自己处理的
            writer.forceMerge(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //恢复
    public void undelete(){
     //使用IndexReader进行恢复
        try {
            IndexReader reader = IndexReader.open(directory,false);
            reader.undeleteAll();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void  delete(){
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
           //参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            //此时删除的文档并不会被完全删除，而是存储在一个回收站中的，可以恢复
            writer.deleteDocuments(new Term("id","1"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             try {
                 if(writer != null)
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void  delete02(){
        IndexWriter writer = null;
        try {
           /* writer = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
            //参数是一个选项，可以是一个Query，也可以是一个term，term是一个精确查找的值
            //此时删除的文档并不会被完全删除，而是存储在一个回收站中的，可以恢复
            writer.deleteDocuments(new Term("id","1"));*/
           reader.deleteDocuments(new Term("id","1"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void update() {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
            Document doc = new Document();
            doc.add(new Field("id", ids[11], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("email", emails[0], Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("content", contents[0], Field.Store.NO, Field.Index.ANALYZED));
            doc.add(new Field("name", names[0], Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            writer.updateDocument(new Term("id", "1"), doc);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void  query(){
        try {
            IndexReader reader = IndexReader.open(directory);
            //通过Reader可以获取文档的数量
            System.out.println("numDocs:"+ reader.numDocs());
            System.out.println("maxDocs:" + reader.maxDoc());
            System.out.println("deleteDocs:"+ reader.numDeletedDocs());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //创建索引
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
               // doc.add(new NumericField("date", Field.Store.YES, true).setLongValue(dates[i]));
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


    public void search01(){
        try {
            IndexReader reader = IndexReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("content","like"));
            TopDocs tds = searcher.search(query,10);
            for (ScoreDoc sd: tds.scoreDocs
                 ) {
                Document doc =  searcher.doc(sd.doc);
                System.out.println("("+ sd.doc + ")"+ doc.get("name")+ "[" + doc.get("email") + "] ----->"+ doc.get("id"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search02(){
        try {

            IndexSearcher searcher =getSearcher();
            TermQuery query = new TermQuery(new Term("content","like"));
            TopDocs tds = searcher.search(query,10);
            for (ScoreDoc sd: tds.scoreDocs
                    ) {
                Document doc =  searcher.doc(sd.doc);
                System.out.println("("+ sd.doc + ")"+ doc.get("name")+ "[" + doc.get("email") + "] ----->"+ doc.get("id"));
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
