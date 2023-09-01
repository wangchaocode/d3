package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description:策略
 * @date 2023/9/1 15:33
 * @vVersion 1.0
 */
public class StrategyClass {
    public static void main(String[] args) {
        GoodFlyBehavior goodFlyBehavior = new GoodFlyBehavior();
        BadFlyBehavior badFlyBehavior = new BadFlyBehavior();
        QuackBehavior quackBehavior = new RadiantQuackBehavior();

        Duck wildDock=new WildDuck();
        OperDuckClass od=new OperDuckClass(wildDock,badFlyBehavior,quackBehavior);
        od.operMethod();

        od.setDuck(new PekingDuck());
        od.operMethod();
    }
}
interface FlyBehavior {

    void fly(); // 子类具体实现
}
interface QuackBehavior {
    void quack();//子类实现
}

class GoodFlyBehavior implements FlyBehavior {

    @Override
    public void fly() {
        // TODO Auto-generated method stub
        System.out.println(" 飞翔技术高超 ~~~");
    }

}
class NoFlyBehavior implements FlyBehavior{

    @Override
    public void fly() {
        // TODO Auto-generated method stub
        System.out.println(" 不会飞翔  ");
    }

}
class BadFlyBehavior implements FlyBehavior {

    @Override
    public void fly() {
        // TODO Auto-generated method stub
        System.out.println(" 飞翔技术一般 ");
    }

}
class NormalQuackBehavior implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("鸭子嘎嘎叫~~");
    }
}
class RadiantQuackBehavior implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("受辐射鸭子鸽鸽叫~~");
    }
}
@Data
class OperDuckClass{
    Duck duck;
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public OperDuckClass(Duck duck, FlyBehavior flyBehavior, QuackBehavior quackBehavior) {
        this.duck = duck;
        this.flyBehavior = flyBehavior;
        this.quackBehavior = quackBehavior;
    }
    void operMethod(){
        String name=this.duck.getClass().getName();
        System.out.println("name:"+name);
        String[] dArr=name.split("\\.");
        System.out.println("当前鸭子："+dArr[dArr.length-1]);
        this.duck.fly();
        if (null !=flyBehavior){
            System.out.println("飞翔行为改变后：");
            this.duck.setFlyBehavior(flyBehavior);
            this.duck.fly();
        }

        this.duck.quack();
        if (null != quackBehavior){
            System.out.println("叫声改变之后：");
            this.duck.setQuackBehavior(quackBehavior);
            this.duck.quack();
        }
    }
}
abstract class Duck {

    //属性, 策略接口
    FlyBehavior flyBehavior;
    //其它属性<->策略接口
    QuackBehavior quackBehavior;

    public Duck() {
        quackBehavior=new NormalQuackBehavior();
    }

    public abstract void display();//显示鸭子信息

    public void quack() {
        if (quackBehavior !=null){
            quackBehavior.quack();
        }
    }

    public void swim() {
        System.out.println("鸭子会游泳~~");
    }

    public void fly() {

        //改进
        if(flyBehavior != null) {
            flyBehavior.fly();
        }
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }


    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

}
class ToyDuck extends Duck{


    public ToyDuck() {
        // TODO Auto-generated constructor stub
        flyBehavior = new NoFlyBehavior();
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        System.out.println("玩具鸭");
    }

    //需要重写父类的所有方法

    public void quack() {
        System.out.println("玩具鸭不能叫~~");
    }

    public void swim() {
        System.out.println("玩具鸭不会游泳~~");
    }


}

class PekingDuck extends Duck {


    //假如北京鸭可以飞翔，但是飞翔技术一般
    public PekingDuck() {
        // TODO Auto-generated constructor stub
        flyBehavior = new BadFlyBehavior();

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        System.out.println("~~北京鸭~~~");
    }

}
class WildDuck extends Duck {


    //构造器，传入FlyBehavor 的对象
    public  WildDuck() {
        // TODO Auto-generated constructor stub
        flyBehavior = new GoodFlyBehavior();
    }


    @Override
    public void display() {
        // TODO Auto-generated method stub
        System.out.println(" 这是野鸭 ");
    }

}
