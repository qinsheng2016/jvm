package com.sqin.jvm.loader;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 19:28 2021/5/27
 **/
public class ClassLoaderTest {

    public static void main(String[] args) {

        // 被Bootstrap加载到内存中的，由C++实现，Java里没有class和他对应，显示null
        // 加载最核心的jar包，rt.jar, charset.jar等
        System.out.println("String类的类加载器: " + String.class.getClassLoader());
        System.out.println("ClassLoader类的类加载器: " + ClassLoader.class.getClassLoader());

        // sun.misc.Launcher$ExtClassLoader@677327b6
        // 加载扩展包里的，jre/lib/ext下的jar
        System.out.println("扩展包里类的类加载器:" + sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());

        System.out.println("=======================================================================================");
        // =======================================================================================
        // sun.misc.Launcher$AppClassLoader
        // 平时用的类加载器
        System.out.println("当前类（平常用的类）的类加载器: " + ClassLoaderTest.class.getClassLoader());

        // AppClassLoader类的加载器：null 是由Bootstrap加载进来
        System.out.println("AppClassLoader类的加载器: " + ClassLoaderTest.class.getClassLoader().getClass().getClassLoader());

        // AppClassLoader类的Parent：sun.misc.Launcher$ExtClassLoader@677327b6
        // 这里的Parent既不是AppClassLoader类的加载器，也不是他的父类
        System.out.println("AppClassLoader类的Parent: " + ClassLoaderTest.class.getClassLoader().getParent());


        System.out.println("=======================================================================================");
        // 根据Launcher源码，可以得出Bootstrap加载的类的路径
        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println("Bootstrap加载的jar包: ");
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));
        System.out.println();
        // 根据Launcher源码，可以得出ExtClassLoader加载的类的路径
        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println("ext加载的jar包: ");
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));
        System.out.println();
        // 根据Launcher源码，可以得出AppClassLoader加载的类的路径
        String pathApp = System.getProperty("java.class.path");
        System.out.println("app加载的jar包: ");
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));
        System.out.println();

        System.out.println("===============================手动load一个类===============================");
        Class clazz = null;
        try {
            // 手动load一个类
            clazz = MyClassLoader.class.getClassLoader().loadClass("com.sqin.jvm.loader.MyClassLoader");
            System.out.println(clazz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
