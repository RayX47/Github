package com.xys.videotest.utils;

import java.io.IOException;

/**
 * 自定义打印输出流类
 */
class PrintStream extends Thread{
    java.io.InputStream __is=null;

    public PrintStream(java.io.InputStream is){
        __is=is;
    }
    public void run(){

        try {
            while (this !=null){
                int __ch=__is.read();
                if (__ch == -1){
                    break;
                }else {
                    System.out.print((char)__ch);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
