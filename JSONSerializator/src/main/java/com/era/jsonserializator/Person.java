package com.era.jsonserializator;

public class Person {
    int age;
    boolean old;
    String name;
    Wallet wallet;

    public Person(int age, boolean old, String name, Wallet wallet) {
        this.age = age;
        this.old = old;
        this.name = name;
        this.wallet = wallet;
    }
}
