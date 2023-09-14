package com.example.d3.exercise.other;

import lombok.Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.Arrays;

/**
 * @author wangchao
 * @date 2023/8/15 10:06:53
 * 回调
 */
@Data
public class CallBackTest {
    private String name;

    public CallBackTest(String name) {
        this.name = name;
    }

    static class TimePrinter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("now time is :"+Instant.ofEpochMilli(e.getWhen()));
            Toolkit.getDefaultToolkit().beep();
        }
    }
    @Data
    static
    class Pq1 implements Cloneable{
        public String name;
        public CallBackTest bc;

        @Override
        public Pq1 clone() throws CloneNotSupportedException{
            return (Pq1)super.clone();
        }
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        /*System.out.println("starting...");
        TimePrinter timePrinter = new TimePrinter();
        new Timer(1000,timePrinter).start();
        JOptionPane.showMessageDialog(null,
                "Quit ?");
        System.exit(0);*/
        // 深拷贝
        /*Pq1 pq=new Pq1();
        pq.setName("PeiQi");
        pq.setBc(new CallBackTest("innerClass"));
        System.out.println(pq);
        Pq1 pq1=pq.clone();
        System.out.println("打印佩奇1~："+pq1);
        */
        // 函数式接口
        String[] m={"asdfasdf1werwer","asdfsaddfasdf2","3wer2","asdfasf"};
        Arrays.sort(m,(first,second)->first.length()-second.length());
        for(String s:m){
            System.out.println( s );
        }
    }
}
