package com.sbrf.reboot;

public class Calculator {

    public int getAddition(int a, int b) {
        return a + b;
    }

    public int getSubtraction(int a, int b) {
        return a - b;
    }

    public int getMultiplication(int a, int b) {
        return a * b;
    }

    //double-type for "lazy" preventing div by 0
    public double getDivision(double a, double b) {
        return a / b;
    }

    public double getIntegerPower(int base, int power) {
        int result = 1;
        int currentPower = power >= 0 ? power : -power;
        for (int i = 0; i < currentPower; i++) {
            result *= base;
        }
        return power >= 0 ? result : (1.0 / result);
    }

    public int getAbs(int a) {
        return a >= 0 ? a : -a;
    }

    public int getRemainder(int a, int b) {
        return a - (a / b) * b;
    }
}