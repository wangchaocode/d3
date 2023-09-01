package com.example.d3.desingpatterns;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchao
 * @Description: 备忘录模式，一键满血
 * @date 2023/9/1 10:34
 * @vVersion 1.0
 */
public class Memorandum {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        OperClass oc=new OperClass(caretaker,originator);

        oc.saveState("满血100");

        oc.saveState("受到伤害  血量30");

        originator.getStateFromMemento(caretaker.get(0));

        System.out.println("恢复后：状态"+originator.getState());

    }

}
/**
 * 外观
 */
class  OperClass{
    Caretaker caretaker;
    ParentOri pi;

    public OperClass(Caretaker caretaker,Originator originator) {
        this.caretaker=caretaker;
        this.pi=originator;
    }
    public void saveState(String state){
        this.pi.setState(state);
        this.caretaker.add(this.pi.saveStateMemento());
        System.out.println("存档，当前状态："+state);
    }
}
class Caretaker {

    //在List 集合中会有很多的备忘录对象
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento memento) {
        mementoList.add(memento);
    }

    //获取到第index个Originator 的 备忘录对象(即保存状态)
    public Memento get(int index) {
        return mementoList.get(index);
    }
}
class Memento {
    private String state;

    //构造器
    public Memento(String state) {
        super();
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
@Data
abstract class  ParentOri{
    private String state;//状态信息
    public abstract Memento saveStateMemento();
}
class Originator extends ParentOri{

    //编写一个方法，可以保存一个状态对象 Memento
    //因此编写一个方法，返回 Memento
    @Override
    public Memento saveStateMemento() {
        return new Memento(super.getState());
    }

    //通过备忘录对象，恢复状态
    public void getStateFromMemento(Memento memento) {
        super.setState( memento.getState());
    }
}
