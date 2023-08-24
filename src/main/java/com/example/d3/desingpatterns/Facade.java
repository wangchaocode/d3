package com.example.d3.desingpatterns;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import lombok.Data;

/**
 * @author wangchao
 * @Description: 外观模式
 *  对机器统一管理
 * @date 2023/8/24 9:39
 * @vVersion 1.0
 */
public class Facade {
    @Data
    static class DvdPlayer{
        private static DvdPlayer INSTANCE = new DvdPlayer();

        public static DvdPlayer getINSTANCE() {
            return INSTANCE;
        }
        public void on() {
            System.out.println(" dvd on ");
        }
        public void off() {
            System.out.println(" dvd off ");
        }

        public void play() {
            System.out.println(" dvd is playing ");
        }

        //....
        public void pause() {
            System.out.println(" dvd pause ..");
        }
    }

    /**
     * 投影仪
     */
    static class Projector {

        private static Projector instance = new Projector();

        public static Projector getInstance() {
            return instance;
        }

        public void on() {
            System.out.println(" Projector on ");
        }

        public void off() {
            System.out.println(" Projector ff ");
        }

        public void focus() {
            System.out.println(" Projector is Projector  ");
        }
    }
 @Data
 static class FacadeClass{
     private DvdPlayer dvdPlayer;
     private Projector projector;

     public FacadeClass() {
         this.dvdPlayer =DvdPlayer.getINSTANCE();
         this.projector = Projector.getInstance();
     }

     public void reday(){
         dvdPlayer.on();
         projector.on();
     }
     public void play(){
         dvdPlayer.play();
     }
     public void pause(){
         dvdPlayer.pause();
     }
     public void end(){
         dvdPlayer.off();
         projector.off();
     }
 }

    public static void main(String[] args) {
        FacadeClass facadeClass = new FacadeClass();
        facadeClass.reday();
        facadeClass.play();
        facadeClass.end();
    }
}
