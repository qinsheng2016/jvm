package com.sqin.jvm.loader;

/*
 * @Author Sheng Qin
 * @Description 需要这个类的时候，类才会被加载进内存
 * @Date 0:37 2021/5/28
 **/
public class LazyLoading {

    public static void main(String[] args) {
        // 访问final值的时候，P类并没有到initializing
//        System.out.println(P.i);

        // 这里没有new，没有访问P类也不会被加载
//        P p;

        // 在访问类静态成员变量时，类已经被加载且initializing完成
//        System.out.println(P.j);

        try {
            Class.forName("com.sqin.jvm.loader.LazyLoading$P");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class P {
        final static int i = 9;
        static int j = 8;
        // 静态语句块会在initializing的步骤执行，只要P类被加载，P肯定会被打印
        static {
            System.out.println("P");
        }
    }

    public static class X extends P {
        static {
            System.out.println("X");
        }
    }

}
