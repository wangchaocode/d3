package com.example.d3.desingpatterns;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wangchao
 * @Description:访问者
 * 改进：1、已经评价过的不允许再评论；
 *      2、将评价存储到对象上，能够打印评价。
 * @date 2023/8/24 15:21
 * @vVersion 1.0
 */
public class VisitorParten2 {

    static abstract class Person {
        /**
         * cop原则
         */
        private String content;
        private boolean voted;
        public void setVoted(){
            this.voted=true;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isVoted() {
            return voted;
        }

        public void setVoted(boolean voted) {
            this.voted = voted;
        }

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
            man.setVoted();
            man.setContent(" 男人给的评价该歌手很成功 !");
        }

        @Override
        public void getWomanResult(Woman woman) {
            woman.setVoted();
            woman.setContent(" 女人给的评价该歌手很成功 !");
        }
    }

    static class Fail extends Action {

        @Override
        public void getManResult(Man man) {
            man.setVoted();
            man.setContent(" 男人给的评价该歌手失败 !");
        }

        @Override
        public void getWomanResult(Woman woman) {
            woman.setVoted();
            woman.setContent(" 女人给的评价该歌手失败 !");
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

        //评价
        public void accept(Action action) {
            for (Person p : persons) {
                if(!p.voted)p.accept(action);
            }
        }
        public void showComments(){
            for (Person p : persons) {
                System.out.println(p.getContent());
            }
        }
    }

    public static void main(String[] args) {
        //创建ObjectStructure
        ObjectStructure objectStructure = new ObjectStructure();

        objectStructure.attach(new Man());
        objectStructure.attach(new Woman());

//成功
        Success success = new Success();
        objectStructure.accept(success);

        objectStructure.attach(new Woman());
        Fail fail = new Fail();
        objectStructure.accept(fail);

        System.out.println("显示所有...................");
        objectStructure.showComments();
    }
}
