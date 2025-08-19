package org.example;

public class Test {
    private TestResult result;
    private String name;
    private  Class<? extends Exception> exception;

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Exception> getException() {
        return exception;
    }

    public void setException(Class<? extends Exception> exception) {
        this.exception = exception;
    }
}
