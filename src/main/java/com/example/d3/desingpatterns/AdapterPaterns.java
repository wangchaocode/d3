package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description: 适配器模式
 * 1、对象、类、接口
 * @date 2023/8/23 14:58
 * @vVersion 1.0
 */
public class AdapterPaterns {
    @Data
    static class Charge220Adapter{
        public int output220(){
            int src=220;
            System.out.println("Charge220Adapter 输出："+src);
            return src;
        }
    }
    /**
     * 5v充电
     */
    static interface IChangeV5{
        int outPut5V();
    }

    static interface IVoltage {
        int out220V();
        int out5V();
    }

    @Data
    static abstract class AbsOutVoltage implements IVoltage{
        @Override
        public int out5V() {
            return 5;
        }

        @Override
        public int out220V() {
            return 220;
        }
    }
    /**
     * 电磁炉，220没问题
     */
    @Data
    static class InductionCooker {
        /**
         *
         */
        private String name;

        public InductionCooker(String name) {
            this.name = name;
        }

        /**
         * useElec
         */
        void useElec(Charge220Adapter charge220Adapter){
            if(charge220Adapter.output220()!=220){
                System.out.println("无法使用，必须使用额定220v电压!");
            }
            System.out.println(this.name+" 做饭中...");
        }
    }


    @Data
    static class Phone {
        /**
         *
         */
        private String name;
        void charge(IChangeV5 changeV5){
            if(changeV5.outPut5V()>5){
                System.out.println("超过5V ，无法充电");
            }else{
                System.out.println(this.name+"充电中...");
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    @Data
    static class Charge5VAdapter implements IChangeV5{
        private Charge220Adapter charge220Adapter;

        public Charge5VAdapter(Charge220Adapter charge220Adapter) {
            this.charge220Adapter = charge220Adapter;
        }

        @Override
        public int outPut5V() {
            int dst=0;
            if (null != this.charge220Adapter){
                System.out.println("开始适配电压...");
                int src=charge220Adapter.output220();
                System.out.println("获取原始电压："+src);
                dst=src/44;
                System.out.println("转换电压："+dst);
            }
            System.out.println("转换后电压："+dst);
            return dst;
        }
    }

    @Data
    static class Out10V implements IChangeV5{
        @Override
        public int outPut5V() {
            return 10;
        }
    }


    @Data
    static class Charge5VAdapter2 extends Charge220Adapter implements IChangeV5{
        @Override
        public int outPut5V() {
            int dst=0;
            System.out.println("开始适配电压...");
            // 调用父类的方法
            int src=output220();
            System.out.println("获取原始电压："+src);
            dst=src/44;
            System.out.println("转换后电压："+dst);
            return dst;
        }
    }

    //-------------------------------------使用新接口的类
    @Data
    static class NewInductionCooker extends AbsOutVoltage{
        /**
         *
         */
        private String name;

        public NewInductionCooker(String name) {
            this.name = name;
        }
        void useElec(){
            System.out.println("使用电压："+out220V()+","+this.name+" 做饭中...");
        }
    }

    @Data
    static class Phone100 extends AbsOutVoltage{
        /**
         *
         */
        private String name;

        public Phone100(String name) {
            this.name = name;
        }
        void charge(){
            System.out.println(name+"：使用电压："+out5V()+"，正在充电...");
        }
    }

    public static void main(String[] args) {
        System.out.println("普通220v电器使用=========================");
        InductionCooker inductionCooker = new InductionCooker("高级进口电磁炉");
        Charge220Adapter charge220Adapter = new Charge220Adapter();
        inductionCooker.useElec(charge220Adapter);

        System.out.println("来一部手机进行充电。=========================");
        // 来一部手机进行充电。
        Phone phone = new Phone();
        phone.setName("apple手机 ");
        phone.charge(new Charge5VAdapter(charge220Adapter));

        System.out.println("=============换继承的第二个适配器=========================");
        phone.charge(new Charge5VAdapter2());

        System.out.println("上10v电压试一下=========================");
        phone.charge(new Out10V());

        System.out.println("==========================更换接口适配器=================");
        NewInductionCooker newInductionCooker = new NewInductionCooker("接口式特级电磁炉");
        newInductionCooker.useElec();

        Phone100 phone100 = new Phone100("爱疯100");
        phone100.charge();
    }
}
