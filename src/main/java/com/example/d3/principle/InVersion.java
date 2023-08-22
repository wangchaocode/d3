package com.example.d3.principle;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangchao
 * @Description: 依赖倒转
 * @date 2023/8/22 9:25
 * @vVersion 1.0
 */
@Slf4j
public class InVersion {

    interface ISendMsg{
        public String send();

    }
    static class Email implements ISendMsg{
        @Override
        public String send() {
            return "这里是[email]正在发消息";
        }
    }
    static class Email2 implements ISendMsg{
        @Override
        public String send() {
            return "这里是[email2]正在发消息";
        }
    }
    static class Person{
        public void print(ISendMsg is){

            System.out.println("sout:"+is.send());
            log.info("log:"+is.send());
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.print(new Email());
        person.print(new Email2());
    }
}
