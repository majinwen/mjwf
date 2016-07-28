package com.mf.lucene.demo.analyzer;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by user on 2016/7/28.
 */
public class AnalyzerUtil {

    public static void displayToken(String str, Analyzer a){

        try {
            TokenStream stream = a.tokenStream("content",new StringReader(str));
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
            while (stream.incrementToken()){
              System.out.println("["+cta+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
