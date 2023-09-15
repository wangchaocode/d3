package com.example.d3.tools;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/15 14:06
 * @vVersion 1.0
 */
public class SleepUtils {

    /**
     *
     * @param seconds 秒
     */
   public static void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
