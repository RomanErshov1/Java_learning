package com.era.data;

public class UserDataSet extends DataSet {
    @UserName
    private final String name;

    @UserAge
    private int age;

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId(){
        return id;
    }
}
