package com.mf.lucene.util;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

import java.io.IOException;

/**
 * Created by user on 2016/7/26.
 */
public class MyIdFilter extends Filter{

    //private String[] delIds = {"1","5"};
    private  FilterAccesser accesser;

    public MyIdFilter(FilterAccesser accesser) {
        this.accesser = accesser;
    }

    @Override
    public DocIdSet getDocIdSet(IndexReader indexReader) throws IOException {
        OpenBitSet obs = new OpenBitSet(indexReader.maxDoc());
        if (accesser.set()) {
            set(indexReader, obs);
        } else {
            clear(indexReader, obs);
        }
        return obs;
    }


    private  void  set(IndexReader reader,OpenBitSet obs){
        try {

            int[] docs = new int[1];
            int[] freqs = new int[1];
            for (String delid : accesser.values()
                    ) {
                TermDocs tds = reader.termDocs(new Term(accesser.getField(), delid));
                int count = tds.read(docs, freqs);
                if (count == 1) {
                    obs.set(docs[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clear(IndexReader reader, OpenBitSet obs) {
        try {
            obs.set(0, reader.maxDoc() - 1);
            int[] docs = new int[1];
            int[] freqs = new int[1];
            for (String delid : accesser.values()
                    ) {
                TermDocs tds = reader.termDocs(new Term(accesser.getField(), delid));
                int count = tds.read(docs, freqs);
                if (count == 1) {
                    obs.clear(docs[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
