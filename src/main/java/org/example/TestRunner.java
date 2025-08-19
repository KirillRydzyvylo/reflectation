package org.example;

import org.example.CastException.BadTestClassError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public static Map<TestResult, ArrayList<Test>> runTest(Class<?> c) throws BadTestClassError {
        Map<TestResult, ArrayList<Test>> result = new HashMap<>();
        for(TestResult r : TestResult.values()){
            result.put(r, new ArrayList<>());
        }

        List<MyMethod> allMyMethods = new ArrayList<>();

        Constructor<?> constructor = null;
        Object object;
        try {
            constructor = c.getDeclaredConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BadTestClassError("Неудалось создать объект из полученного класса");
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        for (Method m : c.getDeclaredMethods()) {
            allMyMethods.add(new MyMethod(m));
        }

        List<MyMethod> tests = allMyMethods.stream().filter(MyMethod::isTest).toList();

        tests = tests.stream()
                .sorted(Comparator.comparing(MyMethod::getOrder).reversed()
                        .thenComparing(Comparator.comparing(MyMethod::getTestOrder).reversed())
                        .thenComparing(MyMethod::getTestName))
                .toList();

        runSubMethods(allMyMethods.stream().filter(MyMethod::isBeforeSuite).toList(),constructor);
        Test testResult;
        for (MyMethod test : tests){

            testResult = new Test();
            testResult.setName(test.getTestName());
            if(test.isDisabled()){
                testResult.setResult(TestResult.Skipped);
                result.get(TestResult.Skipped).add(testResult);
                continue;
            }

            runSubMethods(allMyMethods.stream().filter(MyMethod::isBeforeEach).toList(),object);

            Method method = test.getMethod();
            method.setAccessible(true);
            try {
                method.invoke(object);
                testResult.setResult(TestResult.Success);
                result.get(TestResult.Success).add(testResult);
            } catch (IllegalAccessException e) {
                testResult.setResult(TestResult.Error);
                result.get(TestResult.Error).add(testResult);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getCause();
                if (targetException.getClass().getName().contains("TestAssertionError")){
                    testResult.setResult(TestResult.Failed);
                    result.get(TestResult.Failed).add(testResult);
                }
                else {
                    testResult.setResult(TestResult.Error);
                    result.get(TestResult.Error).add(testResult);
                }
            }

            runSubMethods(allMyMethods.stream().filter(MyMethod::isAfterEach).toList(),object);
        }

        runSubMethods(allMyMethods.stream().filter(MyMethod::isAfterSuite).toList(),constructor);
        return null;
    }

    private static void runSubMethods(List<MyMethod> myMethods, Object instanceObject){
        for (MyMethod myMethod : myMethods) {
            Method method = myMethod.getMethod();
            method.setAccessible(true);
            try {
                method.invoke(instanceObject);
            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}