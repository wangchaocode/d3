package com.example.d3.principle;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangchao
 * @Description: 接口隔离
 * @date 2023/8/22 9:59
 * @vVersion 1.0
 */
@Slf4j
public class InterfaceSegregation {
    interface Inter1{
        void login();
    }
    interface Inter2{
        void record();
    }
    interface Inter3{
        void addScore();
    }
    @Data
    static
    class P1 implements Inter1,Inter2{

        String s;
        @Override
        public void login() {
            log.error("这里我登录了");

        }

        @Override
        public void record() {
            log.error("然后我记录一下我的登录记录");
        }
    }
    @NoArgsConstructor
    @Data
    static class RecordOper implements Inter3{
        public int score=0;
        @Override
        public void addScore() {
            score++;
            log.error("我加分了，看一下");
        }
    }
    static class Dep {
          void  dep1(Inter1 inter1){
                inter1.login();
            }
        void  dep2(Inter2 inter2){
            inter2.record();
        }
        void  dep3(Inter3 inter3){
                inter3.addScore();
            }
    }

    public static void main(String[] args) {
        Dep dep = new Dep();
        dep.dep1(new P1());
        dep.dep2(new P1());
        RecordOper recordOper = new RecordOper();
        dep.dep3(recordOper);
        log.error("分数："+recordOper.getScore());
    }
}
