package com.wj.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 87831 on 2017/10/29/0029.
 */
public class Util {
    public static boolean isEmpty(final String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String getNowYYYYMMDDHHMMSS() {
        String result = "";
        SimpleDateFormat l_sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        result = l_sdf.format(new Date());
        return result;
    }

    public static String getNowYYYYMMDD() {
        String result = "";
        SimpleDateFormat l_sdf = new SimpleDateFormat("yyyyMMdd");
        result = l_sdf.format(new Date());
        return result;
    }

    public static String formartdispdate(String date){
        if(Util.isEmpty(date)){
            return "";
        } else {
            if(date.length() == 14){
                return getDateYYYYMMDDHHMMSS(date);
            } else if(date.length() == 12){
                return Util.getDateYYYYMMDDHHMMSS(date+"00");
            } else if(date.length() == 8){
                return Util.getDateDisp(date);
            } else{
                return "";
            }

        }
    }

    public static String getDateYYYYMMDDHHMMSS(String strdate){
        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        DateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate = "";
        if(strdate!=null&&!strdate.equals("")&&!"null".equals(strdate)){
            try {
                mydate = fmt2.format(fmt.parse((strdate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mydate;
    }

    public static String getDateDisp(String strdate){
        DateFormat fmt=new SimpleDateFormat("yyyyMMdd");
        DateFormat fmt2=new SimpleDateFormat("yyyy-MM-dd");
        String mydate = "";
        if(strdate!=null&&!strdate.equals("")){
            try {
                mydate = fmt2.format(fmt.parse((strdate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mydate;
    }

    public static String getLimitstr(int page, int rows){
        if(page==0){
            page = 1;
        }
        if(rows==0){
            rows = 20;
        }
        int start = (page-1) * rows;
        String limitstr = " limit " + start + "," + rows;
        return limitstr;
    }
}
