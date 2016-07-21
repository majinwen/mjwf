package com.mf.lucene.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by user on 2016/7/21.
 */
public class HelloLucene {
    public void index() {
        IndexWriter writer = null;
        try {
            //1.创建Directory
            // Directory directory = new RAMDirectory();
            Directory directory = FSDirectory.open(new File("D:/tmp/indextest"));
            // FSDirectory.open(Paths.get("D:/tmp/indextest")); // 5.2.1版本
            //2.创建IndexWriter
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
            Document doc = null;
            writer = new IndexWriter(directory, iwc);
            File f = new File("D:/tmp/resource");

            for (File file : f.listFiles()
                    ) {
                //3.创建Ducument对象
                doc = new Document();
                //4.为Ducument对象添加Feild
                doc.add(new Field("content", new FileReader(file)));
                doc.add(new Field("filename", file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                doc.add(new Field("path", file.getAbsolutePath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                //5.通过indexWriter添加到索引中
                writer.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }


public void searcher(){

    try {
        //1.创建Directory
        Directory directory = FSDirectory.open(new File("D:/tmp/indextest"));
        //2.创建IndexReader
        IndexReader indexReader = IndexReader.open(directory);
        //3.根据IndexReader创建IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建搜索的Query
        //创建parser来确定要搜索文件的内容，第二个参数表示搜索的域
        QueryParser queryParser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
       // 创建query ，表示搜索域为content中包含Java的文档
        Query query = queryParser.parse("java");
        //5.根据searcher搜索并且返回TopDocs
        TopDocs topDocs = indexSearcher.search(query,10);
        //6.根据TopDocs获取ScoreDoc对象
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc sd:scoreDocs
             ) {
            //7.根据searcher和ScoredDoc对象获取具体的Document对象
            Document d = indexSearcher.doc(sd.doc);
            //8.根据Document 对象获取需要的值
            System.out.println(d.get("filename")+"["+d.get("path")+"]");
        }

        //9.关闭indexreader

    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }


}




}
