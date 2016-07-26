package com.mf.lucene.util;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;

import java.io.IOException;

/**
 * Created by user on 2016/7/26.
 */
public class MyIdFilter extends Filter{

    @Override
    public DocIdSet getDocIdSet(IndexReader indexReader) throws IOException {


        return null;
    }
}
