package com.example.d3.desingpatterns;

/**
 * @author wangchao
 * @Description: 模板
 * @date 2023/8/24 14:49
 * @vVersion 1.0
 */
public class TemplateTest {
    static abstract class SoybeanMilk{
        //模板方法, make , 模板方法可以做成final , 不让子类去覆盖.
        final void make() {

            select();
            if(customerWantCondiments()){
                addCondiments();
            }
            soak();
            beat();

        }

        //选材料
        void select() {
            System.out.println("第一步：选择好的新鲜黄豆  ");
        }

        //添加不同的配料， 抽象方法, 子类具体实现
        abstract void addCondiments();

        //浸泡
        void soak() {
            System.out.println("第三步， 黄豆和配料开始浸泡， 需要3小时 ");
        }

        void beat() {
            System.out.println("第四步：黄豆和配料放到豆浆机去打碎  ");
        }
        //钩子方法，决定是否需要添加配料
        boolean customerWantCondiments() {
            return true;
        }
    }
    static class PeanutSoyaMilk extends SoybeanMilk {

        @Override
        void addCondiments() {
            // TODO Auto-generated method stub
            System.out.println(" 加入上好的花生 ");
        }

    }
    static class RedBeanSoyaMilk extends SoybeanMilk {

        @Override
        void addCondiments() {
            // TODO Auto-generated method stub
            System.out.println(" 加入红豆 ");
        }
        public boolean customerWantCondiments(){
            System.out.println("==========这一步不再加材料...");
            return false;
        }

    }

    public static void main(String[] args) {
        System.out.println("----制作红豆豆浆----");
        SoybeanMilk redBeanSoyaMilk = new RedBeanSoyaMilk();
        redBeanSoyaMilk.make();

        System.out.println("----制作花生豆浆----");
        SoybeanMilk peanutSoyaMilk = new PeanutSoyaMilk();
        peanutSoyaMilk.make();
    }
}
