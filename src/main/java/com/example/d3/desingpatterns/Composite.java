package com.example.d3.desingpatterns;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchao
 * @Description:组合模式
 * @date 2023/8/24 8:41
 * @vVersion 1.0
 */
public class Composite {
    @Data
    @AllArgsConstructor
    static abstract class OrgComponent{
        /**
         *
         */
        private String name;
        /**
         *
         */
        private String des;
        protected void add(OrgComponent orgComponent){
            throw new UnsupportedOperationException();
        }
        protected void remove(OrgComponent orgComponent){
            throw new UnsupportedOperationException();
        }
        protected void printContent(){
            System.out.println("--------------------"+getName()+"------------");
        }
    }
    static class Univercity extends OrgComponent{
        List<OrgComponent> list=new ArrayList<OrgComponent>();

        public Univercity(String name, String des) {
            super(name, des);
        }

        @Override
        protected void add(OrgComponent orgComponent) {
            list.add(orgComponent);
        }

        @Override
        protected void remove(OrgComponent orgComponent) {
            list.remove(orgComponent);
        }

        @Override
        protected void printContent() {
            super.printContent();
            for (OrgComponent org:list){
                org.printContent();
            }
        }
    }
    static class College extends OrgComponent{
        List<OrgComponent> list=new ArrayList<OrgComponent>();

        public College(String name, String des) {
            super(name, des);
        }

        @Override
        protected void add(OrgComponent orgComponent) {
            list.add(orgComponent);
        }

        @Override
        protected void remove(OrgComponent orgComponent) {
            list.remove(orgComponent);
        }

        @Override
        protected void printContent() {
            super.printContent();
            for (OrgComponent org:list){
                org.printContent();
            }
        }
    }
    static class Department extends OrgComponent{
        List<OrgComponent> list=new ArrayList<OrgComponent>();

        public Department(String name, String des) {
            super(name, des);
        }

        @Override
        protected void add(OrgComponent orgComponent) {
            list.add(orgComponent);
        }

        @Override
        protected void remove(OrgComponent orgComponent) {
            list.remove(orgComponent);
        }

        @Override
        protected void printContent() {
            super.printContent();
            for (OrgComponent org:list){
                org.printContent();
            }
        }
    }

    public static void main(String[] args) {
        OrgComponent org1=new Univercity("清华大学","非常好");
        //创建 学院
        OrgComponent computerCollege = new College("计算机学院", " 计算机学院 ");
        OrgComponent infoEngineercollege = new College("信息工程学院", " 信息工程学院 ");



        computerCollege.add(new Department("软件工程", " 软件工程不错 "));
        computerCollege.add(new Department("网络工程", " 网络工程不错 "));
        computerCollege.add(new Department("计算机科学与技术", " 计算机科学与技术是老牌的专业 "));



        //
        infoEngineercollege.add(new Department("通信工程", " 通信工程不好学 "));
        infoEngineercollege.add(new Department("信息工程", " 信息工程好学 "));


        org1.add(computerCollege);
        org1.add(infoEngineercollege);

        org1.printContent();
    }
}
