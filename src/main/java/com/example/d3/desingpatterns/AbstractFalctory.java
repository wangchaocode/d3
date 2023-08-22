package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description: 简单工厂 抽象
 * @date 2023/8/22 11:20
 * @vVersion 1.0
 */
public class AbstractFalctory {
    /**
     * 定义抽象类
     */
    @Data
    static  abstract class Pizza{
        protected  String name;
        public void bake() {
            System.out.println(name + " baking;");
        }

        public void cut() {
            System.out.println(name + " cutting;");
        }

        //打包
        public void box() {
            System.out.println(name + " boxing;");
        }
    }
    /**
     * 具体实现类
     */
    static class Pizza1 extends Pizza{
        public Pizza1() {
            super.setName("pizza1");
            this.bake();
            this.cut();
            this.box();
        }
    }
    static class Pizza2 extends Pizza{
        public Pizza2() {
            super.setName("pizza2");
            this.bake();
            this.cut();
            this.box();
        }
    }

    static class Pizza3 extends Pizza{
        public Pizza3() {
            super.setName("pizza3");
            this.bake();
            this.cut();
            this.box();

        }
    }

    /**
     * 工厂接口
     */
    interface AbsFactory{
        Pizza prepare(String type);
    }
    /**
     * 工厂实现1
     */
    static class PizzaLess3Factory implements AbsFactory {

        @Override
        public Pizza prepare(String type) {
            switch (type){
                case "1":
                    return new Pizza1();
                case "2":
                    return new Pizza2();
                default:
                    System.out.println("error.未知："+type);
            }
            return null;
        }
    }
    /**
     * 工厂实现2
     */
    static class Pizza3Factory implements AbsFactory {

        @Override
        public Pizza prepare(String type) {
            switch (type){
                case "3":
                    return new Pizza3();
                default:
                    System.out.println("error.未知："+type);
            }
            return null;
        }
    }
    /**
     * 主方法测试一下
     */
    public static void main(String[] args) {
        Pizza3Factory pizza3Factory = new Pizza3Factory();
        pizza3Factory.prepare("3");

        System.out.println("================分割线===============================");
        PizzaLess3Factory pizza12Factory = new PizzaLess3Factory();
        pizza12Factory.prepare("1");

        pizza12Factory.prepare("2");
        pizza12Factory.prepare("3");
    }

}
