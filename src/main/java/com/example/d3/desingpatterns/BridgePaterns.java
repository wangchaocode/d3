package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description:桥接模式
 * 定义接口和抽象类
 *  vivo 实现接口
 *  分类手机（如滑盖、折叠）继承抽象的类，重写方法，构造传入vivo,然后调用。
 *
 * @date 2023/8/23 15:46
 * @vVersion 1.0
 */
public class BridgePaterns {
    //接口
    static interface PhoneFunction {
        void open();
        void close();
        void call();
    }
    static abstract class Phone {

        //组合品牌
        private PhoneFunction brand;

        //构造器
        public Phone(PhoneFunction brand) {
            super();
            this.brand = brand;
        }

        protected void open() {
            this.brand.open();
        }
        protected void close() {
            brand.close();
        }
        protected void call() {
            brand.call();
        }
        void operate(){
            this.open();
            this.call();
            this.close();
        }

    }
    static class FoldPhone extends  Phone{
        public FoldPhone(PhoneFunction brand) {
            super(brand);
        }

        @Override
        protected void open() {
            super.open();
            System.out.println("FoldPhone开机");
        }

        @Override
        protected void call() {
            super.call();
            System.out.println("FoldPhone打电话");
        }

        @Override
        protected void close() {
            super.close();
            System.out.println("FoldPhone关机");
        }
    }
    @Data
    static class Vivo implements PhoneFunction {

        @Override
        public void open() {
            System.out.println("Vivo 开机");
        }

        @Override
        public void close() {
            System.out.println("Vivo 关机");
        }

        @Override
        public void call() {
            System.out.println("Vivo 打电话");
        }
    }

    static class UpRightPhone extends Phone {
        public UpRightPhone(PhoneFunction brand) {
            super(brand);
        }

        @Override
        public void open() {
            System.out.println("UpRightPhone 开机");
        }

        @Override
        public void close() {
            System.out.println("UpRightPhone g机");
        }

        @Override
        public void call() {
            System.out.println("UpRightPhone call");
        }
    }

    public static void main(String[] args) {
        Vivo vivo = new Vivo();
        FoldPhone foldPhone = new FoldPhone(vivo);
        foldPhone.operate();
        // 扩展一个分类方便，只需要继承phone就可以，不管是vivo还是苹果都不需要重写再写一遍具体类
        UpRightPhone upRightPhone = new UpRightPhone(vivo);
        upRightPhone.operate();

    }
}
