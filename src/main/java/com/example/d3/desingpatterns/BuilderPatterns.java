package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description: 建造者模式
 *
 *
 * houseDirector:普通房子和高楼，对于建造者是不了的，只指挥方法。
 * CommonHouse\HighBuilding :具体房子去实现相关方法
 * HouseBuilder:抽象类集合了基类house和方法。
 * House:基类
 * @date 2023/8/23 13:49
 * @vVersion 1.0
 */
public class BuilderPatterns {
    @Data
    static class House{
        /**
         * 基本属性的高度
         */
        private int baise;
        private int wall;
        private int roofed;
    }
    @Data
    static abstract class HouseBuilder{
        protected House house = new House();

        //将建造的流程写好, 抽象的方法
        public abstract void buildBasic();
        public abstract void buildWalls();
        public abstract void roofed();

        //建造房子好， 将产品(房子) 返回
        public House buildHouse() {
            return house;
        }
    }
    @Data
    static class HouseDirector{
        private HouseBuilder houseBuilder;

        public HouseDirector(HouseBuilder houseBuilder) {
            this.houseBuilder = houseBuilder;
        }

        public void setHouseBuilder(HouseBuilder houseBuilder) {
            this.houseBuilder = houseBuilder;
        }
        public House generateHouse(){
            houseBuilder.buildBasic();
            houseBuilder.buildWalls();
            houseBuilder.roofed();
            return houseBuilder.buildHouse();
        }
    }
    @Data
    static class CommonHouse extends HouseBuilder{
        @Override
        public void buildBasic() {
            System.out.println("普通房子 打地基");
            getHouse().setBaise(getHouse().getBaise()+10);
        }

        @Override
        public void buildWalls() {
            System.out.println("普通房子 buildWalls");
            getHouse().setWall(getHouse().getWall()+20);
        }

        @Override
        public void roofed() {
            System.out.println("普通房子 roofed");
            getHouse().setRoofed(getHouse().getRoofed()+40);
        }
    }
    @Data
    static class HighBuilding extends HouseBuilder{
        @Override
        public void buildBasic() {
            System.out.println("HighBuilding 打地基");
            getHouse().setBaise(getHouse().getBaise()+100);
        }

        @Override
        public void buildWalls() {
            System.out.println("HighBuilding buildWalls");
            getHouse().setWall(getHouse().getWall()+200);
        }

        @Override
        public void roofed() {
            System.out.println("HighBuilding roofed");
            getHouse().setRoofed(getHouse().getRoofed()+400);
        }
    }

    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();
        HouseDirector houseDirector = new HouseDirector(commonHouse);
        House house=houseDirector.generateHouse();

        System.out.println("房子产生第一次："+house.toString());
        house=houseDirector.generateHouse();

        System.out.println("房子生成之后第二次状态："+house.toString())    ;
        HighBuilding highBuilding = new HighBuilding();
        houseDirector.setHouseBuilder(highBuilding);
        House h2=houseDirector.generateHouse();
        System.out.println("高楼第一次产生："+h2.toString());
        System.out.println("高楼第二次状态："+houseDirector.generateHouse().toString());

        /**
         * 这里普通类自己也可以调用，不需要指挥者，指挥者能集合做一堆方法。
         */
        /*House h4=commonHouse.getHouse();
        System.out.println(h4.toString());
        commonHouse.buildBasic();
        House h5=commonHouse.getHouse();
        System.out.println(h4.toString());

        System.out.println(h4==h5);*/

        /**
         * jdk使用案例
         */
        StringBuilder stringBuilder = new StringBuilder("hello,world");
        System.out.println(stringBuilder);
        StringBuffer StringBuffer = new StringBuffer("hello,world2");
        System.out.println(StringBuffer);
    }
}
