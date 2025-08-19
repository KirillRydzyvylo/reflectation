package org.example;


import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<TestResult, ArrayList<Test>> result;
        try {
            result = TestRunner.runTest(Tests.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}