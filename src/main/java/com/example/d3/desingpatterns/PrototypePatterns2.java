package com.example.d3.desingpatterns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.SerializationUtils;
import java.io.Serializable;

/**
 * @author wangchao
 * @Description: 原型 提出抽象类，接口。
 * 1、super.clone浅拷贝，那么每个类都实现，即可深拷贝
 * 2、构造实现深拷贝：对象都是new的，保证新鲜
 * 3、apache common lang 包实现
 *  引包： <dependency>
 *             <groupId>org.apache.commons</groupId>
 *             <artifactId>commons-lang3</artifactId>
 *         </dependency>
 *     注意：如果想打印hashcode需要把@Data注解去掉，原因：
 *     target文件夹中的Person类才知道，被@Data修饰的类会提供类的get、set、equals、hashCode、toString方法。而重写的hashcode方法是根据类的属性的值来计算hashcode，所以拷贝后两者的hashcode才会一样
 *     https://blog.csdn.net/weixin_47025878/article/details/129101631
 * @date 2023/8/23 8:38
 * @vVersion 1.0
 */
public class PrototypePatterns2 {
   static  class PSheep implements Serializable{
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
    }
    /**
     * 抽类
     */
    interface ISheep{
        default void logInfo(PSheep sheep) throws Exception{
            System.out.println("logInfo:"+sheep.toString());
            PSheep sheep1 =copySheep(sheep);
            System.out.println("sheep.hashCode()"+sheep.hashCode()+",addr-hash:"+sheep.getAddr().hashCode());
            System.out.println("sheep1.hashCode();"+sheep1.hashCode()+",addr-hash:"+sheep1.getAddr().hashCode());
            sheep.setName("sheep-tom");
            sheep.getAddr().setName("2号");
            System.out.println(sheep.toString());
            System.out.println(sheep1.toString());
        }
        PSheep copySheep(PSheep sheep) throws CloneNotSupportedException;
    }
    static class Addr implements Cloneable,Serializable{
        public Addr(String name) {
            this.name = name;
        }

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

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return (Addr)super.clone();
        }
    }
     static class Sheep extends PSheep implements Cloneable,ISheep{
        public Sheep(String s1,String s2,int i3,Addr addr){
            this.setName(s1);
            this.setSex(s2);
            this.setAge(i3);
            this.setAddr(addr);
        }

         public Sheep() {
         }

         @Override
        protected Object clone() throws CloneNotSupportedException {
            Sheep sheep=null;
            sheep= (Sheep)super.clone();
            // 这里都的每个引用类型都去实现clone方法。这里继续掉类的clone
            sheep.setAddr((Addr)sheep.getAddr().clone());
            return sheep;
        }
        @Override
        public PSheep copySheep(PSheep sheep) throws CloneNotSupportedException {
            Sheep s= (Sheep) sheep;
            Sheep sheep1=(Sheep) s.clone();
            System.out.println("copySheep==========copy:"+sheep1.hashCode());
            return sheep1;
        }

        @Override
        public String toString() {
            return "Sheep{" +
                    "name='" + getName() + '\'' +
                    ", sex='" + getSex() + '\'' +
                    ", age=" + getAddr() +
                    ", addr=" + getAddr().getName() +
                    '}';
        }
    }
//-----------------apache序列号实现---------------------------------------------------------
    static class SheepSer  extends  PSheep implements Serializable,ISheep {
    public SheepSer(String s1,String s2,int i3,Addr addr){
        this.setName(s1);
        this.setSex(s2);
        this.setAge(i3);
        this.setAddr(addr);
    }

    public SheepSer() {
    }

    @Override
    public PSheep copySheep(PSheep sheep) throws CloneNotSupportedException {
        SheepSer clone = (SheepSer)SerializationUtils.clone((SheepSer)sheep);
        System.out.println("hashcode:"+clone.hashCode());
        return clone;
    }
    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + getName() + '\'' +
                ", sex='" + getSex() + '\'' +
                ", age=" + getAddr() +
                ", addr=" + getAddr().getName() +
                '}';
    }
}
    //----------------------end ser----------------------------------------------------

    public static void main(String[] args) throws Exception{
        PSheep sheep = new Sheep("jack","公羊",3,new Addr("羊舍1号"));
        ISheep s2=new Sheep();
        s2.logInfo(sheep);
        System.out.println("-----------------apache序列号实现---------------------------------------------------------");
        //-----------------apache序列号实现---------------------------------------------------------
        Addr addr=new Addr("羊舍10号");
        SheepSer sheepSer = new SheepSer("jack2","公羊",4,addr);
        ISheep s3=new SheepSer();
        s3.logInfo(sheepSer);
    }
}
