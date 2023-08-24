package com.example.d3.desingpatterns;

import lombok.Data;

/**
 * @author wangchao
 * @Description: 装饰者模式
 * @date 2023/8/23 16:58
 * @vVersion 1.0
 */
public class Decoractor {
    static abstract class Drink {
        public String des; // 描述
        private float price = 0.0f;
        public String getDes() {
            return des;
        }
        public void setDes(String des) {
            this.des = des;
        }
        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }

        //计算费用的抽象方法
        //子类来实现
        public abstract float cost();

        @Override
        public String toString() {
            return "Drink{" +
                    "des='" + des + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
    static class DecoratorInner extends Drink{

        private Drink drink;

        public DecoratorInner(Drink drink) {
            this.drink = drink;
        }

        @Override
        public float cost() {
            return getPrice()+drink.cost();
        }

        @Override
        public String getDes() {
            return des+":"+getPrice()+" && "+drink.getDes();
        }
    }
    static class Chocolate extends DecoratorInner {

        public Chocolate(Drink obj) {
            super(obj);
            setDes(" 巧克力 ");
            setPrice(3.0f); // 调味品 的价格
        }

    }
    static class Coffee  extends Drink {

        @Override
        public float cost() {
            // TODO Auto-generated method stub
            return super.getPrice();
        }
    }

    /**
     * 脱咖啡因咖啡
     */
    static class DeCaf extends Coffee {

        public DeCaf() {
            setDes(" 无因咖啡 ");
            setPrice(1.0f);
        }
    }

    /**
     * 意大利咖啡
     */
    static class Espresso extends Coffee {

        public Espresso() {
            setDes(" 意大利咖啡 ");
            setPrice(6.0f);
        }
    }
    static class Milk extends DecoratorInner {
        public Milk(Drink obj) {
            super(obj);
            // TODO Auto-generated constructor stub
            setDes(" 牛奶 ");
            setPrice(2.0f);
        }
    }

    /**
     * 奶沫咖啡
     */
    static class ShortBlack extends Coffee{

        public ShortBlack() {
            setDes(" shortblack ");
            setPrice(4.0f);
        }
    }
    static class Sugar extends DecoratorInner{
        public Sugar(Drink drink) {
            super(drink);
            setDes(" 糖 ");
            setPrice(2.0f);
        }
    }
   static void info(Drink drink,String msg){
        String str= !"".equals(msg)?msg:"";
        str+="总费用："+drink.cost()+",描述："+drink.getDes();
        System.out.println(str);
    }
    public static void main(String[] args) {
        Drink order =new Espresso();
        info(order,"");
        Milk milk = new Milk(order);
        info(milk,"加牛奶-->");
        Chocolate chocolate = new Chocolate(milk);
        info(chocolate,"加巧克力");
        Sugar sugar = new Sugar(chocolate);
        info(sugar,"加糖 ");
    }
}
