package com.mf.lucene.util;

/**
 * Created by user on 2016/7/27.
 */
public interface FilterAccesser {

    String[] values();

    String getField();

    boolean set();
}
