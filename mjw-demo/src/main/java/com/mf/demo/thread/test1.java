package com.mf.demo.thread;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2016/7/27.
 */
public class test1 {
    /*public  static  void  main(String[]  args){
        String test7 = "22/Jul/2016 10:43:58";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date =null;
        try {
          // date = sdf.parse(test7);
            date = dateFormat.parse("05-Sep-2013");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(String.valueOf(date.getTime()));

    }*/


    public static void main(String[] args) {
    /*  //String dStr = "2001.12.12-08:23:21";
        //String test = "22.Jul.2016:10:44:16";
        String test = "16-Nov-2013";
        Date d = null;
      // SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
       // SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy:HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        try {
           // d = sdf.parse(dStr);
            d = sdf.parse(test);
        }catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
       // System.out.println(d);
        System.out.println(d.getTime());*/
        /*Date date1 = new Date("16-Nov-2013");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("date1=" +sdf.format(date1));*/


        try {
            SimpleDateFormat sdf = new
                    SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date a = sdf.parse("22/Jul/2016:10:43:58");
           System.out.println(a.getTime());
           // System.out.println(sdf1.format(a.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }





}
