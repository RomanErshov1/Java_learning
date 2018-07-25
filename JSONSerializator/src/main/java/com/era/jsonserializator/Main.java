package com.era.jsonserializator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args){

        Person person1 = new Person(26, false, "Roma",
                new Wallet("MyWallet", 35000 ));
        Person person2 = new Person(82, true, "Stepan",
                new Wallet("MyWallet", 20010 ));

//        JsonSerializator.writeObject(2, "primitive_type.json");

        JsonSerializator.writeObject(person1, "data_object.json");

        JsonSerializator.writeObject(new Person[]{person1, person2}, "data_array.json");

        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        JsonSerializator.writeObject(list, "data_list.json");

        Set<Person> set = new HashSet<>();
        set.add(person1);
        set.add(person2);
        JsonSerializator.writeObject(set, "data_set.json");
    }
}
