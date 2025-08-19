package org.example;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            TestRunner.runTest(Tests.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}