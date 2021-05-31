package com.sqin.jvm.loader;

import java.io.*;

/*
 * @Author Sheng Qin
 * @Description
 * @Date 22:38 2021/5/27
 **/
public class MyClassLoader extends ClassLoader {

    MyClassLoader(){
        super(new MyClassLoaderParent());
    }

    @Override
    protected Class<?> findClass(String name) {
        File f = new File("F:/Temp/", name.replace(".", "/").concat(".class"));

        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;

            while((b=fis.read()) != 0) {
                baos.write(b);
            }

            byte[] bytes = baos.toByteArray();
            baos.close();
            fis.close();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        // todo ClassFormatError: Truncated class file
//        ClassLoader myClassLoader = new MyClassLoader();
//        try {
//            Class helloWorld = myClassLoader.loadClass("com.sqin.jvm.loader.HelloWorld");
//            System.out.println(helloWorld.getClassLoader());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        MyClassLoader myClassLoader = new MyClassLoader();
        System.out.println(myClassLoader.getParent());

    }


}
