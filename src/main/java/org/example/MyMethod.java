package org.example;

import org.example.CastAnnotation.*;
import org.example.CastAnnotation.Test;
import org.example.CastException.BadTestClassError;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MyMethod {
    private Method method;
    private String testName;
    private int testOrder;
    private int order;

    public MyMethod(Method method) throws BadTestClassError {
        this.method = method;
        if ((isBeforeSuite() || isAfterSuite()) && !Modifier.isStatic(method.getModifiers())) {
            String message = "Для аннотация BeforeSuite и AfterSuite метод " + method.getName() + " должен иметь модификатор static";
            throw new BadTestClassError(message);
        }

        if ((isTest() || isAfterEach() || isBeforeEach()) && Modifier.isStatic(method.getModifiers())) {
            String message = "Для аннотация Test, AfterEach и isBeforeEach метод " + method.getName() + " не должен иметь модификатор static";
            throw new BadTestClassError(message);
        }

        if(isTest()) {
            saveOrder();
            saveTestName();
            saveTestOrder();
        }
    }

    public Method getMethod() {
        return method;
    }

    public boolean isTest() {
        return method.isAnnotationPresent(Test.class);
    }

    public boolean isDisabled() {
        return method.isAnnotationPresent(Disabled.class);
    }

    public boolean isAfterSuite() {
        return method.isAnnotationPresent(AfterSuite.class);
    }

    public boolean isBeforeSuite() {
        return method.isAnnotationPresent(BeforeSuite.class);
    }

    public boolean isAfterEach() {
        return method.isAnnotationPresent(AfterEach.class);
    }

    public boolean isBeforeEach() {
        return method.isAnnotationPresent(BeforeEach.class);
    }

    public String getTestName() {
        return testName;
    }

    public int getTestOrder() {
        return testOrder;
    }

    public int getOrder() {
        return order;
    }

    private void saveOrder() {
        if (!method.isAnnotationPresent(Order.class)) {
            order = 1;
        } else {
            int or = method.getAnnotation(Order.class).value();
            if (or > 10) {
                order = 10;
            } else order = Math.max(or, 1);
        }
    }

    private void saveTestName() {
        testName = method.getAnnotation(Test.class).value();
        if ("".equals(testName)) {
            testName = method.getName();
        }
    }

    private void saveTestOrder() {
        int or = method.getAnnotation(Test.class).order();
        if (or > 10) {
            testOrder = 10;
        } else testOrder = Math.max(or, 0);
    }
}
