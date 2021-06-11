package com.sqin.jvm.jmm;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 23:44 2021/6/1
 **/
public class TestStack {

    public static void main(String[] args) {
        int i = 8;
        i++;
        System.out.println(i);
    }
}
