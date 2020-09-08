package com.ldx.javaSpringBoot.modules.account.controller;

public class TestThread {
    public static void main(String[] args){
      Thread thread=new Thread();
       thread.start();
        for (int i=0;i<00;i++){
            if (i%2==0){
                System.out.print(i+"*****************");
            }
        }

    }
}
class Thread extends java.lang.Thread {
@Override
    public void run(){
        for (int i=0;i<00;i++){
            if (i%2!=0){
                System.out.print(i);
            }
        }

    }
}
