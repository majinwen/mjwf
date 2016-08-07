package com.mf.lucene.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/7/26.
 */
public class CustomParser extends QueryParser {
    public CustomParser(Version matchVersion, String f, Analyzer a) {
        super(matchVersion, f, a);
    }

    @Override
    protected Query getWildcardQuery(String field, String termStr) throws ParseException {
        throw new ParseException("由于性能原因，已经禁用了通配符查询，请输入更精确地信息查询");
    }

    @Override
    protected Query getFuzzyQuery(String field, String termStr, float minSimilarity) throws ParseException {
        throw new ParseException("由于性能原因，已经禁用了模糊查询，请输入更精确地信息查询");
    }

    @Override
    protected Query getRangeQuery(String field, String part1, String part2, boolean inclusive) throws ParseException {
        if (field.equals("size")) {
            return NumericRangeQuery.newIntRange(field, Integer.parseInt(part1), Integer.parseInt(part2), inclusive, inclusive);
        }else if(field.equals("date")){
            String datetype = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datetype);
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            if(pattern.matcher(part1).matches()&&pattern.matcher(part2).matches()){
                try {
                    long start = simpleDateFormat.parse(part1).getTime();
                    long end = simpleDateFormat.parse(part2).getTime();
                    return NumericRangeQuery.newLongRange(field,start,end,inclusive,inclusive);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }else {

                new ParseException("要检索的格式不对");
            }

        }


        return super.newRangeQuery(field, part1, part2, inclusive);
    }



}