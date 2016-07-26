package com.mf.lucene.util;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 2016/7/26.
 */
public class CustomParserUtil {
    public void searcherByQuery(String querystr) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            IndexSearcher searcher = new IndexSearcher(FileIndexUtils.getDirectory());
            CustomParser customParser = new CustomParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
            Query query = customParser.parse(querystr);
            TopDocs tds = null;
            tds = searcher.search(query, 3);
            for (ScoreDoc sd : tds.scoreDocs
                    ) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ")[" + d.get("filename") + "【" + d.get("path") + "】" + "]");
            }
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

    }


}
