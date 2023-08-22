package com.example.d3.exercise;

import lombok.Data;

import java.util.*;

/**
 * @author wangchao
 * @date 2023/8/14 10:12:12
 */
public class ObserverClass {
    // 抽象的观察者接口
    interface Observer {
        void update(double balance);

        void updateLast(UserDomain ud);
        default void update2(UserDomain ud,double balance){
            update(balance);
            updateLast(ud);
        };

    }

    // 具体的警报观察者
    static class AlertObserver implements Observer {
        @Override
        public void update(double balance) {
            if (balance < 1000) {
                System.out.println("警报！余额低于1000元。当前余额：" + balance);
            }
        }

        @Override
        public void updateLast(UserDomain ud) {
            System.out.println("info===当前用户信息：{}"+ud.toString());
        }

    }

    // 具体的报表观察者
    static class ReportObserver implements Observer {
        @Override
        public void update(double balance) {
            System.out.println("生成一份报告。当前余额：" + balance);
        }

        @Override
        public void updateLast(UserDomain ud) {
            System.out.println("ReportObserver->当前用户信息：{}"+ud.toString());
        }
    }

    // 被观察者（主题）接口
    interface Subject {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }

    @Data
    static class UserDomain{
        private String name;
        private int age;
        private double allMoney;

        public UserDomain(String name, int age, double allMoney) {
            this.name = name;
            this.age = age;
            this.allMoney = allMoney;
        }
    }
    // 具体的银行账户类（被观察者）
    @Data
    static class BankAccount implements Subject {
        private double balance;
        private UserDomain userDomainj;
        private List<Observer> observers = new ArrayList<>();

        public BankAccount(double balance) {
            this.balance = balance;
        }

        public double getBalance() {
            return balance;
        }

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update2(userDomainj,balance);
            }
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                this.getUserDomainj().setAllMoney(this.getUserDomainj().getAllMoney()+balance);
                notifyObservers(); // 当存款或取款时，通知观察者更新余额信息。。
            }
        }
    }
//    现在我们可以创建一个银行账户，并添加观察者：
    public static void main(String[] args) {
        BankAccount account = new BankAccount(10000); // 创建一个初始余额为10000元的银行账户。
        UserDomain u=new UserDomain("张三",22,0);
        account.setUserDomainj(u);
        AlertObserver alertObserver = new AlertObserver(); // 创建一个警报观察者。
        ReportObserver reportObserver = new ReportObserver(); // 创建一个报表观察者。
        // 每个观察者接口都可以去注册，然后主题内实现对应方法。
        account.registerObserver(alertObserver); // 将警报观察者注册到银行账户中。
        account.registerObserver(reportObserver); // 将报表观察者注册到银行账户中。
        account.deposit(3300);

//        新增订阅
      /*  BankAccount account2 = new BankAccount(10000); // 创建一个初始余额为10000元的银行账户。
        UserDomain u2=new UserDomain("张三",22,5989);
        account2.setUserDomainj(u2);
        account2.registerObserver(alertObserver);
        account2.registerObserver(reportObserver);
        account2.deposit(3000);*/
//        account.withdraw(5000); // 从银行账户中取出5000元。由于账户余额低于1000元，将触发警报观察者的update方法，并打印警报信息。同时，报表观察者的update方法也将被调用，并打印生成报告的信息。
    }
}
