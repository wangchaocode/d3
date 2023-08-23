package com.example.d3.desingpatterns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;

/**
 * @author wangchao
 * @Description: 原型 这一个是分别掉自己方法，重复，PrototypePatterns2 把方法抽出，把公共属性抽出到父类。
 * 1、super.clone浅拷贝，那么每个类都实现，即可深拷贝
 * 2、构造实现深拷贝：对象都是new的，保证新鲜
 * 3、apache common lang 包实现
 *  引包： <dependency>
 *             <groupId>org.apache.commons</groupId>
 *             <artifactId>commons-lang3</artifactId>
 *         </dependency>
 * @date 2023/8/23 8:38
 * @vVersion 1.0
 */
public class PrototypePatterns {
    @AllArgsConstructor
    static class Addr implements Cloneable,Serializable{
        /**
         *
         */
        private String name;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return (Addr)super.clone();
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    static class Sheep implements Cloneable {
        /**
        *
        */
        private String name;
        private String sex;
        private int age;
        private Addr addr;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Addr getAddr() {
            return addr;
        }

        public void setAddr(Addr addr) {
            this.addr = addr;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Sheep sheep=null;
            sheep= (Sheep)super.clone();
            // 这里都的每个引用类型都去实现clone方法。这里继续掉类的clone
            sheep.setAddr((Addr)sheep.getAddr().clone());
            return sheep;

        }
        void logInfo() throws Exception{
            Sheep sheep=this;
            System.out.println("sheep.toString():"+sheep.toString());

            /**
             * PrototypePatterns.Sheep(name=jack, sex=公羊, age=3, addr=PrototypePatterns.Addr(name=羊舍1号))
             * -499418609,1004693796
             * -499418609,1004693796
             *
             * hash code 一样的
             */
            Sheep sheep1 =copySheep(this);

            System.out.println("sheep-hashCode"+sheep.hashCode()+","+sheep.getAddr().hashCode());

            System.out.println("sheep1-hashCode"+sheep1.hashCode()+","+sheep1.getAddr().hashCode());
            /**
             * PrototypePatterns.Sheep(name=jack, sex=公羊, age=3, addr=PrototypePatterns.Addr(name=2号))
             * PrototypePatterns.Sheep(name=jack, sex=公羊, age=3, addr=PrototypePatterns.Addr(name=2号))
             * 引用类型被改变。浅拷贝
             */
           /* sheep.setName("sheep-tom");
            sheep.getAddr().setName("2号");
            s3.setName("s3");*/

            System.out.println("sheep== sheep1:"+ (sheep== sheep1));
        }

        public  Sheep copySheep(Sheep sheep) throws CloneNotSupportedException {
            Sheep clone = (Sheep) sheep.clone();
            return clone;
        }
    }
//-----------------apache序列号实现---------------------------------------------------------
@AllArgsConstructor
    static class AddrSer  implements Serializable {
        /**
        *
        */
        private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
    @NoArgsConstructor
    @AllArgsConstructor
    static class SheepSer  implements Serializable {
        /**
         *
         */
        private String name;
        private String sex;
        private int age;
        private AddrSer addr;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public AddrSer getAddr() {
            return addr;
        }

        public void setAddr(AddrSer addr) {
            this.addr = addr;
        }

        void logInfo() throws Exception{
            SheepSer sheep=this;
            System.out.println(sheep.toString());

            SheepSer sheep1 =copySheep(this);

            System.out.println("sheep-hashCode"+sheep.hashCode()+","+sheep.getAddr().hashCode());

            System.out.println("sheep1-hashCode"+sheep1.hashCode()+","+sheep1.getAddr().hashCode());
            System.out.println("sheep== sheep1:"+ (sheep== sheep1));
        }

        public  SheepSer copySheep(SheepSer sheep) throws CloneNotSupportedException {
            System.out.println("SerializationUtils.clone");
            SheepSer clone = SerializationUtils.clone(sheep);
            System.out.println(clone.hashCode());
            return clone;
        }
    }
    //----------------------end ser----------------------------------------------------

    public static void main(String[] args) throws Exception{
        Sheep sheep = new Sheep("jack","公羊",3,new Addr("羊舍1号"));
        sheep.logInfo();
        System.out.println("-----------------apache序列号实现---------------------------------------------------------");
        //-----------------apache序列号实现---------------------------------------------------------
        SheepSer sheepSer = new SheepSer("jack2","公羊",4,new AddrSer("羊舍10号"));
        sheepSer.logInfo();
    }
}
