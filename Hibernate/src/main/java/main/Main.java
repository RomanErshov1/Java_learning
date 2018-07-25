package main;

import base.DBService;
import base.datasets.AddressDataSet;
import base.datasets.PhoneDataSet;
import base.datasets.UserDataSet;
import dbService.DBServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        Set<PhoneDataSet> roma_phones = new HashSet<>();
        roma_phones.add(new PhoneDataSet("+79026863591"));
        roma_phones.add(new PhoneDataSet("+79524426980"));
        Set<PhoneDataSet> sasha_phones = new HashSet<>();
        sasha_phones.add(new PhoneDataSet("+79050112636"));
        sasha_phones.add(new PhoneDataSet("+79151576325"));
        sasha_phones.add(new PhoneDataSet("+79084783148"));
        dbService.save(new UserDataSet("Roma", 27, new AddressDataSet("Lukina"), roma_phones));
        dbService.save(new UserDataSet("Sasha", 36, new AddressDataSet("Gagarina"), sasha_phones));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.read(2);
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        dataSets.forEach(System.out::println);

        dbService.shutdown();
    }
}
