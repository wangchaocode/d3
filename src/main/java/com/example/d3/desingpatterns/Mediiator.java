package com.example.d3.desingpatterns;

import java.util.HashMap;

/**
 * @author wangchao
 * @Description: 中介模式  ，如mvc
 * 本例：每个同事类的构造都传入中介，触发自己方法时调用自己中介去处理，中介根据传入类型判断下一步操作。
 *
 * @date 2023/9/1 9:50
 * @vVersion 1.0
 */
public class Mediiator {
    public static void main(String[] args) {
        Mediator mediator=new ConcreteMediator();
//创建Alarm 并且加入到  ConcreteMediator 对象的HashMap
        Alarm alarm = new Alarm(mediator, "alarm");

        //创建了CoffeeMachine 对象，并  且加入到  ConcreteMediator 对象的HashMap
        CoffeeMachine coffeeMachine = new CoffeeMachine(mediator,
                "coffeeMachine");
//创建 Curtains , 并  且加入到  ConcreteMediator 对象的HashMap
        Curtains curtains = new Curtains(mediator, "curtains");
        TV tV = new TV(mediator, "TV");
        alarm.SendMessage(0);
        coffeeMachine.FinishCoffee();
        alarm.SendMessage(1);
    }


}
 abstract class Mediator {
    //将给中介者对象，加入到集合中
    public abstract void register(String colleagueName, Colleague colleague);

    //接收消息, 具体的同事对象发出
    public abstract void getMessage(int stateChange, String colleagueName);

    public abstract void sendMessage();
}
//同事抽象类
 abstract class Colleague {
    private Mediator mediator;
    public String name;

    public Colleague(Mediator mediator, String name) {

        this.mediator = mediator;
        this.name = name;

    }

    public Mediator GetMediator() {
        return this.mediator;
    }

    public abstract void SendMessage(int stateChange);
}

 class Curtains extends Colleague {

    public Curtains(Mediator mediator, String name) {
        super(mediator, name);
        // TODO Auto-generated constructor stub
        mediator.register(name, this);
    }

    @Override
    public void SendMessage(int stateChange) {
        // TODO Auto-generated method stub
        this.GetMediator().getMessage(stateChange, this.name);
    }

    public void UpCurtains() {
        System.out.println("I am holding Up Curtains!");
    }

}
 class Alarm extends Colleague{

    public Alarm(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name,this);
    }

    @Override
    public void SendMessage(int stateChange) {
        this.GetMediator().getMessage(stateChange,this.name);
    }
}
 class CoffeeMachine extends Colleague {

    public CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        // TODO Auto-generated constructor stub
        mediator.register(name, this);
    }

    @Override
    public void SendMessage(int stateChange) {
        // TODO Auto-generated method stub
        this.GetMediator().getMessage(stateChange, this.name);
    }

    public void StartCoffee() {
        System.out.println("It's time to startcoffee!");
    }

    public void FinishCoffee() {

        System.out.println("After 5 minutes!");
        System.out.println("Coffee is ok!");
        SendMessage(0);
    }
}
 class TV extends Colleague {

    public TV(Mediator mediator, String name) {
        super(mediator, name);
        // TODO Auto-generated constructor stub
        mediator.register(name, this);
    }

    @Override
    public void SendMessage(int stateChange) {
        // TODO Auto-generated method stub
        this.GetMediator().getMessage(stateChange, this.name);
    }

    public void StartTv() {
        // TODO Auto-generated method stub
        System.out.println("It's time to StartTv!");
    }

    public void StopTv() {
        // TODO Auto-generated method stub
        System.out.println("StopTv!");
    }
}
//具体的中介者类
 class ConcreteMediator extends Mediator {
    //集合，放入所有的同事对象
    private HashMap<String, Colleague> colleagueMap;
    private HashMap<String, String> interMap;

    public ConcreteMediator() {
        colleagueMap = new HashMap<String, Colleague>();
        interMap = new HashMap<String, String>();
    }

    @Override
    public void register(String colleagueName, Colleague colleague) {
        // TODO Auto-generated method stub
        colleagueMap.put(colleagueName, colleague);

        // TODO Auto-generated method stub

        if (colleague instanceof Alarm) {
            interMap.put("Alarm", colleagueName);
        } else if (colleague instanceof CoffeeMachine) {
            interMap.put("CoffeeMachine", colleagueName);
        } else if (colleague instanceof TV) {
            interMap.put("TV", colleagueName);
        } else if (colleague instanceof Curtains) {
            interMap.put("Curtains", colleagueName);
        }

    }

    //具体中介者的核心方法
    //1. 根据得到消息，完成对应任务
    //2. 中介者在这个方法，协调各个具体的同事对象，完成任务
    @Override
    public void getMessage(int stateChange, String colleagueName) {
        // TODO Auto-generated method stub

        //处理闹钟发出的消息
        if (colleagueMap.get(colleagueName) instanceof Alarm) {
            if (stateChange == 0) {
                ((CoffeeMachine) (colleagueMap.get(interMap
                        .get("CoffeeMachine")))).StartCoffee();
                ((TV) (colleagueMap.get(interMap.get("TV")))).StartTv();
            } else if (stateChange == 1) {
                ((TV) (colleagueMap.get(interMap.get("TV")))).StopTv();
            }

        } else if (colleagueMap.get(colleagueName) instanceof CoffeeMachine) {
            ((Curtains) (colleagueMap.get(interMap.get("Curtains"))))
                    .UpCurtains();

        } else if (colleagueMap.get(colleagueName) instanceof TV) {//如果TV发现消息

        } else if (colleagueMap.get(colleagueName) instanceof Curtains) {
            //如果是以窗帘发出的消息，这里处理...
        }

    }

    @Override
    public void sendMessage() {
        // TODO Auto-generated method stub

    }

}

