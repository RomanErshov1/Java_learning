package com.era.testframework;

import com.era.testframework.annotations.After;
import com.era.testframework.annotations.Before;
import com.era.testframework.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestFramework {

    private TestFramework(){}

    private static List<Method> findTestMethods(Class testingClass){
        List<Method> testMethods = new ArrayList<>();
        Method[] methods = testingClass.getDeclaredMethods();

        for (Method method : methods){
            if (method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    private static Method findBeforeMethod(Class testingClass){
        Method[] methods = testingClass.getDeclaredMethods();

        return Arrays.stream(methods).filter(
                method -> method.isAnnotationPresent(Before.class)).findFirst().orElse(null);
    }

    private static Method findAfterMethod(Class testingClass){
        Method[] methods = testingClass.getDeclaredMethods();

        return Arrays.stream(methods).filter(
                method -> method.isAnnotationPresent(After.class)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    private static void runAllTests(Class testingClass){

        List<Method> testMethods = findTestMethods(testingClass);
        Method beforeMethod = findBeforeMethod(testingClass);
        Method afterMethod = findAfterMethod(testingClass);

        for (Method test : testMethods){
            Object object = ReflectionHelper.instantiate(testingClass);
            assert object != null;
            ReflectionHelper.callMethod(object, beforeMethod.getName());
            ReflectionHelper.callMethod(object, test.getName());
            ReflectionHelper.callMethod(object, afterMethod.getName());
        }
    }

    public static void runClasses(Class testingClass){
      runAllTests(testingClass);
    }

    public static void runPackage(String packageName){
        List<Class> classes = ClassFinder.find(packageName);

        for (Class mClass : classes){
            if (findTestMethods(mClass).size() > 0) runAllTests(mClass);
        }
    }
}
