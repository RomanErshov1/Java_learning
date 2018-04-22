package com.era.testframework;

import com.era.testframework.annotations.After;
import com.era.testframework.annotations.Before;
import com.era.testframework.annotations.Test;
import org.junit.Assert;


public class ReflectionHelperTest {
    @Before
    void Before(){
        System.out.println("Before");
    }

    @After
    void After(){
        System.out.println("After");
    }

    @Test
    public void instantiate() {
        TestClass testClass = ReflectionHelper.instantiate(TestClass.class);
        Assert.assertEquals(0, testClass.getA());
        Assert.assertEquals("", testClass.getS());

        Assert.assertEquals(1, ReflectionHelper.instantiate(TestClass.class, 1).getA());
        Assert.assertEquals("A", ReflectionHelper.instantiate(TestClass.class, 1, "A").getS());
    }

    @Test
    public void getFieldValue() {
        Assert.assertEquals("A", ReflectionHelper.getFieldValue(new TestClass(1, "A"), "s"));
        Assert.assertEquals(1, ReflectionHelper.getFieldValue(new TestClass(1, "B"), "a"));
    }

    @Test
    public void setFieldValue() {
        TestClass test = new TestClass(1, "A");
        Assert.assertEquals("A", test.getS());
        ReflectionHelper.setFieldValue(test, "s", "B");
        Assert.assertEquals("B", test.getS());
    }

    @Test
    public void callMethod() {
        Assert.assertEquals("A", ReflectionHelper.callMethod(new TestClass(1, "A"), "getS"));

        TestClass test = new TestClass(1, "A");
        ReflectionHelper.callMethod(test, "setDefault");
        Assert.assertEquals("", test.getS());
    }
}
