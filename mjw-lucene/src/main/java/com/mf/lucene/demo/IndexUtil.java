package com.mf.lucene.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016/7/21.
 */
public class IndexUtil {
           private String[] ids = {"1","2","3","4","5","6"};
           private String[] emails = {"aa@itat.org","bb@itat.org","cc@sina.com","dd@qq.com","ee@dd.com","ff@sina.com"};
           private String[] contents = {"welcome to visit my space","hello boy","my name is cc","i like football",
           "i like coffe","i like movie"};
           private int[] attachs = { 2,3,1,4,5,5};
           private String[] names = {"zhangsan","lisi","wangwu","john","jetty","jack"};
           private Directory directory= null;

    private Map<String,Float> scores = new HashMap<String, Float>();
    public  IndexUtil(){
        try {
            scores.put("itat.org",2.0f);
            scores.put("sina.com",1.5f);

            directory = FSDirectory.open(new File("D:/tmp/indextest/index02"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            for(int i=0;i < ids.length;i++){
                doc = new Document();
                doc.add(new Field("id",ids[i],Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
                doc.add(new Field("email",emails[i],Field.Store.YES,Field.Index.NOT_ANALYZED));
                doc.add(new Field("content",contents[i],Field.Store.NO,Field.Index.ANALYZED));
                doc.add(new Field("name",names[i],Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
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

}
