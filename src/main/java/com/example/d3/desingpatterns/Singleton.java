package com.example.d3.desingpatterns;

import org.springframework.context.annotation.Bean;

/**
 * @author wangchao
 * @Description: 1、单例模式
 * @date 2023/8/22 10:40
 * @vVersion 1.0
 */
public class Singleton {
    /**
     * 静态成员变量
     */
    static class MyFactory{
        private String name="MyFactory";
        private static MyFactory INSTANCE=new MyFactory();

        public static MyFactory getINSTANCE() {
            return INSTANCE;
        }
    }
    /**
     * 枚举
     */
    enum MyEnumFactory{
        INSTANCE;
        public String sayOk(){
            return "ok";
        }

    }

    public static void main(String[] args) {
        MyFactory m=MyFactory.getINSTANCE();
        MyFactory m2=MyFactory.getINSTANCE();

        System.out.println(m==m2);
        System.out.println(m.hashCode());
        System.out.println(m2.hashCode());


        MyEnumFactory m3=MyEnumFactory.INSTANCE;
        MyEnumFactory m4=MyEnumFactory.INSTANCE;

        System.out.println(m3==m4);
        System.out.println(m3.hashCode());
        System.out.println(m4.hashCode());
        System.out.println(m4.sayOk());
    }
}
