package com.mf.demo.test;

import sun.nio.cs.ext.GBK;

import java.io.*;
import java.sql.Connection;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/2.
 */
public class test1 {

    static Pattern P_TAB = Pattern.compile("[\\t]");

    //从广告表导出广告到文本（mody by majinwen 20160802）
    private static void getAdvertFromDB() throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("城").append(",").append("廊坊").append(",")
                .append("城标题").append("|").append("城标签").append("|").append("urlval");
        str.append("\r\n");

        str.append("桃源居").append(",").append("上海市").append(",")
                .append("桃源居titleval").append("|").append("lableval").append("|").append("urlval");
        str.append("\r\n");

        str.append("南极居").append(",").append("南京市").append(",")
                .append("titleval").append("|").append("lableval").append("|").append("urlval");
        str.append("\r\n");
        //String testContent = ;
        newFile("D:\\tmp\\advert.txt", str.toString());
    }



    //存广告
    public static void newFile(String filePathAndName, String fileContent) {
        try {
            File myFilePath = new File(filePathAndName.toString());
            if (!myFilePath.exists()) { // 如果该文件不存在,则创建
                myFilePath.createNewFile();
            }
            // FileWriter(myFilePath, true); 实现不覆盖追加到文件里
            //FileWriter(myFilePath); 覆盖掉原来的内容
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            // 给文件里面写内容,原来的会覆盖掉
            myFile.println(fileContent);
            resultFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     //读取广告文件
     public static Map<String, Map<String, String>> readTxtFile(String filePath) {
         //存放内容的map对象
         // Map<Integer,String> filemaps = new HashMap<Integer,String>();
         Map<String, Map<String, String>> advertmaps = new HashMap<String, Map<String, String>>();
         //Map<Integer,List> filemaps = new HashMap<Integer,String>();

         try {
             String encoding = "UTF-8";
             File file = new File(filePath);
             int count = 0;//定义顺序变量
             if (file.isFile() && file.exists()) { // 判断文件是否存在
                 InputStreamReader read = new InputStreamReader(
                         new FileInputStream(file), encoding);// 考虑到编码格式
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String lineTxt = null;
                 while ((lineTxt = bufferedReader.readLine()) != null) {//按行读取
                     System.out.println("lineTxt=" + lineTxt);
                     if (!"".equals(lineTxt)) {
                         String reds = lineTxt.split("\\+")[0];//对行的内容进行分析处理后再放入map里。
                         System.out.println(reds);
                         String[] vals = reds.split(",");
                         Map<String, String> ccmap = new HashMap<>();
                         ccmap.put(vals[1], vals[2]);
                         if (!advertmaps.containsKey(vals[0])) { //不包括这个关键词
                             advertmaps.put(vals[0], ccmap);
                         } else { // 如果包括这个关键词
                             ccmap.putAll(advertmaps.get(vals[0]));
                             advertmaps.put(vals[0], ccmap);
                         }
                     }
                 }
                 read.close();//关闭InputStreamReader
                 bufferedReader.close();//关闭BufferedReader
             } else {
                 System.out.println("找不到指定的文件");
             }
         } catch (Exception e) {
             System.out.println("读取文件内容出错");
             e.printStackTrace();
         }
         return advertmaps;
     }







    public static void  main(String[] args){

        try {
            getAdvertFromDB();
            String filename = "D:\\tmp\\advert.txt";
            Map<String, Map<String, String>> filemaps = readTxtFile(filename);
            int num = filemaps.size();// 行数
            String tes= "城";
            String city = "廊坊";
            String str = filemaps.get(tes).get(city);
            System.out.println("关键字+城市的广告:"+ str);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
