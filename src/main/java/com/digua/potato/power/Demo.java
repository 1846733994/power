package com.digua.potato.power;

/**
 * 装饰着模式
 * 装饰着模式四大组成部分：
 *  1.抽象组件  需要装饰的抽象对象（抽象类或借口）
 *  2.具体组件  需要被装饰的对象
 *  3.抽象装饰类
 *  4.具体装饰类（被装饰对象）
 */
public class Demo {
    public static void main(String[] args) {

    }

    /**
     * 抽象组件  饮料
     * 提取抽象的方法（一种规定）
     */
    interface Drink{
        void money();
        void info ();
    }
    /**
     * 具体抽象组件 咖啡
     */
    class Coffee implements Drink{
        private int

        @Override
        public void money() {

        }

        @Override
        public void info() {

        }
    }
}




