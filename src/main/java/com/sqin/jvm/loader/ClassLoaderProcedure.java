package com.sqin.jvm.loader;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 22:10 2021/5/31
 * @Description 给静态成员变量赋默认值和初始值
 **/
public class ClassLoaderProcedure {

    /**
     * 根据T类中两行代码的顺序不同，count值是不同的：
     * 如果T()在前，在T.class被load到内存中时，在preparation先给成员变量赋初始值，这时count为0，然后执行count++，count变为1
     *  再给count赋值，最后count为2
     * 如果count在前，先loading，然后preparation赋值为0，再resolution，再由initializing初始化赋初始值，先给count赋值为2，
     *  然后执行count++，最后count为3
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(T.count);
    }

}

class T {
    public static T t = new T();
    public static int count = 2;

    // 这里的成员变量赋值也是需要两步的，第一步new对象的时候，申请内存，然后给m赋默认值0，在调用构造方法时，给他赋初始值8.
    private int m = 8;

    private T() {
        count++;
    }
}
