package com.sandbox.test;

import java.util.concurrent.TimeUnit;

public class TracePrint {

    private static void myprint(int a, String b) {

        System.out.println(a);
        System.out.println(b);
    }


    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                int a=3;
                String b="tom";
                myprint(a,b);
            } catch (Throwable cause) {
                cause.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(5);
        }
    }


}
