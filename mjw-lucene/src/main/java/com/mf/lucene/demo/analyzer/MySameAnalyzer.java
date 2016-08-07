package com.mf.lucene.demo.analyzer;

import com.chenlb.mmseg4j.MaxWordSeg;
import org.apache.ibatis.javassist.bytecode.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.PagedBytes;
import org.apache.lucene.util.packed.PackedInts;
import org.apache.lucene.store.Directory;
import java.io.Reader;
import java.util.Dictionary;


/**
 * Created by user on 2016/7/28.
 */
public class  MySameAnalyzer extends Analyzer {
    public MySameAnalyzer() {

    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        Dictionary dic = Dictionary("D:\\Workspaces\\03_lucene_analyzer\\mmseg4j-1.8.4\\data");
        return new MySameTokenFilter(new MMSegTokenizer(new MaxWordSeg(dic), reader));
    }

}
