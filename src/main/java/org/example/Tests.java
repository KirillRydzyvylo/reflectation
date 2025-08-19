package org.example;

import org.example.CastAnnotation.*;
import org.example.CastAnnotation.Test;
import org.example.CastException.TestAssertionError;

public class Tests {

    public Tests() {
    }

    @BeforeEach
    public void beforeEach1(){
        System.out.println("BeforeEach 1");
    }
    @BeforeEach
    public void beforeEach2(){
        System.out.println("BeforeEach 2");
    }

    @AfterEach
    public void afterEach1(){
        System.out.println("AfterEach 1");
    }
    @AfterEach
    public void afterEach2(){
        System.out.println("AfterEach 2");
    }

    @Test
    public void test1(){
        System.out.println("test1  @Test() и @Order нет ");
    }

    @Test("Имя Test 2")
    public void test2(){
        System.out.println("test2 @Test(Имя Test 2) @Order нет");
    }

    @Order(1)
    @Test()
    public void test3(){
        System.out.println("test3 @Test() и Order(1)");
    }


    @Order(10)
    @Test()
    public void test4(){
        System.out.println("test4 @Test() и @Order(10)");
    }

    @Order()
    @Test()
    public void test5(){
        System.out.println("test5 @Test() и @Order()");
    }

    @Test(order = 7)
    public void test6(){
        System.out.println("test6 б @Test (order = 7) и @Order нет");
    }

    @Order()
    @Test()
    public void atest6() throws TestAssertionError{
        System.out.println("atest6 @Test() и @Order()");
        throw new TestAssertionError();
    }

    @Test(order = 2, value = "Тест с проверкой")
    public void test7() throws TestAssertionError{
        System.out.println("test7 @Test(order = 2, value = \"Тест с проверкой\") и @Order нет");
    }

    @BeforeSuite
    public static void beforeSuite1(){
        System.out.println("BeforeSuite 1");
    }

    @BeforeSuite
    public static void beforeSuite2(){
        System.out.println("BeforeSuite 2");
    }

    @AfterSuite
    public static void afterSuite1(){
        System.out.println("AfterSuite 1");
    }

    @AfterSuite
    public static void afterSuite2(){
        System.out.println("AfterSuite 2");
    }
}
