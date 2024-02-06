package org.example;

import org.example.tests.*;
import org.testng.TestNG;

public class Main {

    public static void main(String[] args) {

        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { LoginPageTest.class, InvetoryPageTest.class, CartPageTest.class, CheckoutOnePageTest.class, CheckoutTwoPageTest.class});
        testng.run();

        System.exit(0);
    }
}