# JVM

## Class加载过程

### Loading

把class文件Load到内存中去。

#### 类加载器

##### Bootstrap 

加载最核心部分的类

##### ExtClassLoader

加载Extension扩展类

##### AppClassLoader

加载平时写的类

```java
package com.sqin.jvm.loader;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 19:28 2021/5/27
 * 测试Java中各种类由哪一个类加载器进行加载的
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
```

##### 自定义ClassLoader

继承ClassLoader， 重写findClass方法。在热部署，Spring动态代理，都是需要使用自己去加载这些新的类。

#### 双亲委派机制

##### 什么是双亲委派机制

在加载Class文件时，会通过类加载器的parent（这里的parent不是父类，也不是父类加载器，仅仅是一个成员变量，指向上一级类加载器）进行查找（每个类加载器会把加载到内存中的类缓存起来），如果找不到，继续往上查找，如果Bootstrap还是找不到，才会去加载这个类，加载的时候由上往下，如果这个类不归Bootsrap加载，他会指派ExtClassLoader去加载，一直往下，如果找不到这个class，报ClassNotFoundException。

##### 为什么要用双亲委派机制

主要是为了安全性

#### LazyLoading

### Linking

#### Verification

校验加载到内存中的class文件是否符合class文件的标准。

#### Preparation

给静态变量赋默认值，这里是默认值，不是初始值，比如`static int i = 8`，在这一步会先赋一个默认值0给i。

#### Resolution

把class文件中常量池里面的符号引用，给它转换成内存地址，直接可以访问到内容了。

### Initializing

给静态变量赋初始值，比如刚才的i，在这一步会赋一个值为8。静态语句块也会在这一步被执行。



## Java内存模型