package com.example.d3.desingpatterns;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchao
 * @Description:享元模式
 * 重复利用之前生成的类，注意内存浪费。
 * Integer -128 到127的使用
 * @date 2023/8/24 9:50
 * @vVersion 1.0
 */
public class Flyweight {
    @Data
    static abstract class WebSite{
        abstract protected void use();
    }
    static class ConcreteWebSite extends WebSite {

        //共享的部分，内部状态
        private String type = ""; //网站发布的形式(类型)
        //构造器
        public ConcreteWebSite(String type) {
            this.type = type;
        }

        @Override
        public void use() {
            // TODO Auto-generated method stub
            System.out.println("网站的发布形式为:" + type + " 在使用中 .. ");
        }
    }
    static class WebFactory{
        private Map<String,WebSite> map=new HashMap<>();

        public WebSite createWeb(String name){
            if (map.containsKey(name)){
                return map.get(name);
            }
            WebSite wb= new ConcreteWebSite(name);
            map.put(name,wb);
            return wb;
        }
        public void getSize(){
            System.out.println("当前池中有"+ map.size()+"个网站：");;
        }

    }


    public static void main(String[] args) {
        String name="博客";
        WebSite webSite = new ConcreteWebSite(name);
        webSite.use();
        WebFactory webFactory = new WebFactory();
        name="论坛";
        WebSite wb2=webFactory.createWeb(name);
        wb2.use();
        webFactory.getSize();

        name="博客";
        WebSite wb3=webFactory.createWeb(name);
        wb3.use();
        webFactory.getSize();

    }
}
