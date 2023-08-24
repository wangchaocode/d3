package com.example.d3.desingpatterns;



import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author wangchao
 * @Description:代理：静态，动态（jdk,cglib)
 * @date 2023/8/24 10:08
 * @vVersion 1.0
 */
public class ProxyTest {
    static interface ITeacher{
        /**
         *
         */
        void teach() ;
        String sayHello(String m);

    }
    static class TeacherImpl implements ITeacher{

        @Override
        public void teach() {
            System.out.println("I am teaching");
        }

        @Override
        public String sayHello(String m) {
            return null;
        }
    }

    /**
     * 静态代理
     */
    static class ProxyTeach implements ITeacher{
        private ITeacher teacher;

        public ProxyTeach(ITeacher teacher) {
            this.teacher = teacher;
        }

        @Override
        public void teach() {
            System.out.println("教师开始讲课...");
            teacher.teach();
            System.out.println("教师讲课结束.");
        }

        @Override
        public String sayHello(String m) {

            return "hello,I am "+m;
        }
    }

    /**
     * 动态代理
     */
    static class Dyncnamic {
        private Object target;

        public Dyncnamic(Object object) {
            this.target = object;
        }

        public Object getInstance() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object target, Method method, Object[] args) throws Throwable {
                            System.out.println("动态proxy开始代理。。。");
                            Object obj = method.invoke(target, args);
                            System.out.println("参数："+ Arrays.toString(args)+"代理完成,返回值："+obj);
                            return obj;
                        }
                    });
        }
        static class CglibProxy implements MethodInterceptor {
            private Object target;

            public CglibProxy(Object object) {
                this.target = object;
            }
            public Object getProxyInstance(){
                Enhancer enhancer=new Enhancer();
                enhancer.setSuperclass(target.getClass());
                enhancer.setCallback(this);
                return enhancer.create();
            }
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("动态cglib开始代理。。。");
                Object obj = method.invoke(target, args);
                System.out.println("参数："+ Arrays.toString(args)+"代理完成,返回值："+obj);
                return obj;
            }
        }
    }

    public static void main(String[] args) {
        //静态代理，必须实现对应方法
        ITeacher teacherOld = new TeacherImpl();

        ProxyTeach proxyTeach = new ProxyTeach(teacherOld);
        proxyTeach.teach();

        Dyncnamic dyncnamic = new Dyncnamic(teacherOld);
        ITeacher teacher= (ITeacher) dyncnamic.getInstance();
        System.out.println(teacher.getClass());
        teacher.teach();
        teacher.sayHello("jack");

        Dyncnamic.CglibProxy cglibProxy=new Dyncnamic.CglibProxy(teacherOld);
        ITeacher teacher2= (ITeacher) cglibProxy.getProxyInstance();
        teacher2.sayHello("Tom");
        System.out.println(teacher2.getClass());


    }
}
