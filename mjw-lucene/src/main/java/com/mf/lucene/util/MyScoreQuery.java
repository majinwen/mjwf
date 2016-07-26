package com.mf.lucene.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.ValueSourceQuery;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/26.
 */
public class MyScoreQuery {

    public void searchByScoreQuery(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexUtils.getDirectory()));
            Query q = new TermQuery(new Term("content","java"));
            //创建一个评分域
            FieldScoreQuery fd = new FieldScoreQuery("score", FieldScoreQuery.Type.INT);
            //根据评分域和原有的Query创建自定义的Query对象
            MyCustomScoreQuery query = new MyCustomScoreQuery(q,fd);
            TopDocs tds = null;
            tds = searcher.search(query,3);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchByFilenameScoreQuery(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexUtils.getDirectory()));
            Query q = new TermQuery(new Term("content","java"));

            //根据评分域和原有的Query创建自定义的Query对象
            FilenameScoreQuery query = new FilenameScoreQuery(q);
            TopDocs tds = null;
            tds = searcher.search(query,3);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class FilenameScoreQuery extends CustomScoreQuery{

        public FilenameScoreQuery(Query subQuery) {
            super(subQuery);
        }

        @Override
        protected CustomScoreProvider getCustomScoreProvider(IndexReader reader) throws IOException {
            return  new FilenameScoreProvider(reader);
        }
    }




    private class DateScoreProvider extends CustomScoreProvider{
          long[] dates = null;
        public DateScoreProvider(IndexReader reader) {
            super(reader);
            try {
                dates = FieldCache.DEFAULT.getLongs(reader,"date");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public float customScore(int doc, float subQueryScore, float valSrcScore) throws IOException {
            long date = dates[doc];
            long today = new Date().getTime();
            long year = 1000*60*60*24*365;//一年的时间
            if(today-date<= year){
                     //为其加分


            }
            return super.customScore(doc, subQueryScore, valSrcScore);
        }
    }





  private class FilenameScoreProvider extends CustomScoreProvider{
      String[] filenames = null;

      public FilenameScoreProvider(IndexReader reader) {
          super(reader);
          try {
              filenames = FieldCache.DEFAULT.getStrings(reader,"filename");
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

      @Override
      public float customScore(int doc, float subQueryScore, float valSrcScores) throws IOException {

          //如何根据doc获取相应的field的值
          /**
           * 在Reader没有关闭之前，所有的数据会存储在一个域缓存中，可以通过域缓存获取很多有用的信息
           *  filenames = FieldCache.DEFAULT.getStrings(reader,"filename");
           */
          float score = subQueryScore;
          String filename = filenames[doc];
          if(filename.endsWith("1.txt")||filename.endsWith("5.txt")){
              return subQueryScore*1.5f;
          }
          return  subQueryScore/1.5f;
      }
  }




    public class  MyCustomScoreQuery extends CustomScoreQuery {
        public MyCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery) {
            super(subQuery, valSrcQuery);
        }

        @Override
        protected CustomScoreProvider getCustomScoreProvider(IndexReader reader) throws IOException {
            /**
             * 自定评分的步骤
             * 创建一个类继承于CustomScoreProvider
             *
             */
           // return super.getCustomScoreProvider(reader);
          return new MyCustomScoreProvider(reader);

        }
    }

    private  class MyCustomScoreProvider extends CustomScoreProvider{

        public MyCustomScoreProvider(IndexReader reader) {
            super(reader);
        }

        /**
         *
         * @param doc
         * @param subQueryScore 表示默认文档的打分
         * @param valSrcScores 表示评分域的打分
         * @return
         * @throws IOException
         */
        @Override
        public float customScore(int doc, float subQueryScore, float valSrcScores) throws IOException {
            return subQueryScore/valSrcScores;
        }

    }


}
