package com.sandbox.test;


import java.util.concurrent.TimeUnit;

public class ExceptionDIDemo {

    private  static String getDetail(User user){

        if (null==user || "".equals(user)){
            return "null";
        }
        return user.getName();
    }
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                User u = new User(3, "lucy");
                System.out.println("结果为: "+getDetail(u));
            } catch (Throwable cause) {
                cause.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(5);
        }


    }
}
