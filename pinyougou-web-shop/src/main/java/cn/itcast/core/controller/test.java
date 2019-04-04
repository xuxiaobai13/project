package cn.itcast.core.controller;

import java.util.Random;

public class test {
    public static void main(String[] args) {
        int i = new Random().nextInt(6);
        System.out.println(i);
        long l = new Random().nextLong();
        System.out.println(l);
        System.out.println((long) (10000+Math.random()*99999));


    }
}
