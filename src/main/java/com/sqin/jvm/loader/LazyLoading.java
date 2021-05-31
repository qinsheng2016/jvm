package com.sqin.jvm.loader;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 0:37 2021/5/28
 **/
public class LazyLoading {

    public static void main(String[] args) {
        // 访问final值的时候，P类并没有到initializing
//        System.out.println(P.i);

        //
        System.out.println(P.j);
//        try {
//            Class.forName("com.sqin.jvm.loader.LazyLoading$P");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public static class P {
        final static int i = 9;
        static int j = 8;
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
