package com.example.d3.desingpatterns;

import org.omg.CORBA.Object;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchao
 * @Description: 观察者模式
 * @date 2023/8/24 16:17
 * @vVersion 1.0
 */
public class ObserverTest {
    public static void main(String[] args) {
        Subject bookSubject= new BookSubject();
        for (int i = 0; i < 5; i++) {
            ObserverTeacher observerTeacher1 = new ObserverTeacher(new Books("图书-"+i));
           bookSubject.add(observerTeacher1);
        }

        bookSubject.showList();
        bookSubject.update(new Books("世界通史"));
        bookSubject.showList();
    }
}

/**
 * 订阅书籍
 */
class Books{
    private String name;

    public Books(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Books{" +
                "name='" + name + '\'' +
                '}';
    }
}

interface Subject{
    void add(Observer observer);
    void remove(Observer observer);

    void update(Books book);
    void showList();
}
interface Observer{
    void update(Books books);
    void showMe();
}
class BookSubject implements Subject {
    List<Observer> observerList=new ArrayList<>();
    @Override
    public void add(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void update(Books book) {
       for (Observer ob: observerList){
           ob.update(book);
       }
    }
    public void showList() {
        for (Observer ob : observerList) {
            ob.showMe();
        }
    }

}
class ObserverTeacher implements Observer{

    private Books book;

    public ObserverTeacher(Books book) {
        this.book = book;
    }

    @Override
    public void update(Books books) {
        System.out.println("接到通知，书籍变更："+books.toString());
        this.book=books;
    }

    @Override
    public void showMe() {
        System.out.println(this.hashCode()+" 这里正在观察..看书：."+book.toString());
    }
}


