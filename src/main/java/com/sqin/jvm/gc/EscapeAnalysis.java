package com.sqin.jvm.gc;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 15:43 2021/6/11
 **/
public class EscapeAnalysis {

    class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i) {
        new User(i, "name " + i);
    }

    public static void main(String[] args) {
        EscapeAnalysis ea = new EscapeAnalysis();
        long start = System.currentTimeMillis();
        for(int i=0; i<10000000; i++){
            ea.alloc(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


}


