package com.example.d3.exercise.other;

import java.util.Stack;

/**
 * @author wangchao
 * @Description: 测试 栈
 * @date 2023/9/1 14:30
 * @vVersion 1.0
 */
public class StackTest {

    public static void main(String[] args) {
        Stack s=new Stack();
        System.out.println(operStack(s,0));
        System.out.println(operStack(s,1));
    }
    static String operStack(Stack s,int popOrPush){
        String s2="";
        for (int i = 0; i < 10; i++) {
            if(popOrPush==0){
                System.out.println("入站，i:"+i);
                s.push(i);
            }else{
                s2+=","+ s.pop();
            }
        }
        return s2;
    }
}
