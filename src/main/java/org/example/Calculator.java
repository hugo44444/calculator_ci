package org.example;

public class Calculator {
    //variables shared by all "Calculator" objects
    final float PI = 3.14f;

    public void doNothing(){}

    public int sum(int num1, int num2){
        return num1 + num2;
    }
    public double sum(double num1, double num2){
        return num1 + num2;
    }
    /**
     * Creation of a method or algorithm which
     * receives a number as parameter and
     * return the square of the number as
     * result
     */
    public int square(int num){
        return num * num;
    }
    public float square(float num){
        return num * num;
    }
    /**
     * Generalization:
     * Design an algorithm that is able to
     * calculate the exponential of any number
     */
    public float exponential(float base, int exponent){
        float result = 1;
        for (int i = 0; i < exponent; i++) {
            result = result * base;
        }
        return result;
    }

    /**
     * Division of two integers.
     * Dividing by zero is not allowed, so it throws an
     * ArithmeticException with a clear message.
     */
    public int divide(int num1, int num2){
        if (num2 == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return num1 / num2;
    }

    /**
     * Division of two decimal numbers.
     * With doubles Java does not fail when dividing by zero
     * (it returns Infinity), so we check it ourselves.
     */
    public double divide(double num1, double num2){
        if (num2 == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return num1 / num2;
    }
}
