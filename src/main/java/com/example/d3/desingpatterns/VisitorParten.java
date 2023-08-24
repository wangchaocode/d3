package com.example.d3.desingpatterns;

import java.util.*;

/**
 * @author wangchao
 * @Description:访问者
 * @date 2023/8/24 15:21
 * @vVersion 1.0
 */
public class VisitorParten {

    static abstract class Person {

        //提供一个方法，让访问者可以访问
        public abstract void accept(Action action);

    }

    static abstract class Action {

        //得到男性 的测评
        public abstract void getManResult(Man man);

        //得到女的 测评
        public abstract void getWomanResult(Woman woman);
    }

    static class Man extends Person {

        @Override
        public void accept(Action action) {
            action.getManResult(this);
        }
    }

    static class Woman extends Person {

        @Override
        public void accept(Action action) {
            action.getWomanResult(this);
        }

    }

    static class Success extends Action {

        @Override
        public void getManResult(Man man) {
            // TODO Auto-generated method stub
            System.out.println(" 男人给的评价该歌手很成功 !");
        }

        @Override
        public void getWomanResult(Woman woman) {
            // TODO Auto-generated method stub
            System.out.println(" 女人给的评价该歌手很成功 !");
        }
    }

    static class Fail extends Action {

        @Override
        public void getManResult(Man man) {
            // TODO Auto-generated method stub
            System.out.println(" 男人给的评价该歌手失败 !");
        }

        @Override
        public void getWomanResult(Woman woman) {
            // TODO Auto-generated method stub
            System.out.println(" 女人给的评价该歌手失败 !");
        }

    }

    static class ObjectStructure {

        //维护了一个集合
        private List<Person> persons = new LinkedList<>();

        //增加到list
        public void attach(Person p) {
            persons.add(p);
        }

        //移除
        public void detach(Person p) {
            persons.remove(p);
        }

        //显示测评情况
        public void display(Action action) {
            for (Person p : persons) {
                p.accept(action);
            }
        }
    }

    public static void main(String[] args) {
        //创建ObjectStructure
        ObjectStructure objectStructure = new ObjectStructure();

        objectStructure.attach(new Man());
//        objectStructure.attach(new Woman());

//成功
        Success success = new Success();
        objectStructure.display(success);

        objectStructure.attach(new Woman());
        Fail fail = new Fail();
        objectStructure.display(fail);


    }
}
