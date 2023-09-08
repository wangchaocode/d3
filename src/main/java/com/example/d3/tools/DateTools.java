package com.example.d3.tools;

import java.time.Instant;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/5 11:03
 * @vVersion 1.0
 */
public class DateTools {
    static Instant now=Instant.now();
    public static  void getIntervalTime(String i ){
        System.out.println( i+" 间隔时间： "+(Instant.now().toEpochMilli()-now.toEpochMilli()));
    }
}
