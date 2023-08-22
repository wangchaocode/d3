package com.example.d3.exercise;

/**
 * @author wangchao
 * @date 2023/8/15 10:06:53
 * 单继承多实现
 */
public class MuchInterface {
    static interface PrintOneWord{
        void sayHi();
        void sayNo();
    }
    interface RunAndFighting extends PrintOneWord {
        void runOneStep();
        // 默认接口方法，调用实现类的方法实现，类似插槽。
        default void fighting(){
            sayHi();
            System.out.println("I am fighting with animals");
            runOneStep();
            sayNo();

        }
    }
    interface RunAndFighting2 extends PrintOneWord {
        void runOneStep();
        // 默认接口方法，调用实现类的方法实现，类似插槽。
        default void fighting(){
            sayHi();
            System.out.println("I am fighting with animals");
            runOneStep();
            sayNo();

        }
    }
    static class PeiQ implements PrintOneWord{

        @Override
        public void sayHi() {
            System.out.println("hi,I am Peiqi");
        }

        @Override
        public void sayNo() {
            System.out.println("no,he said.");
        }

    }
    static class PeiQSon extends PeiQ{

        @Override
        public void sayHi() {
            System.out.println("hi,I am Peiqi'son");
        }

    }
    static class PeiQ2 extends PeiQSon implements RunAndFighting,RunAndFighting2{
        @Override
        public void runOneStep() {
            System.out.println("run ,the first step is .");
        }

        @Override
        public void fighting() {
            System.out.println("fighting");
            RunAndFighting2.super.fighting();
        }
    }
    public static void main(String[] args) {
        PeiQ2 p2=new PeiQ2();
        p2.fighting();
    }
}
