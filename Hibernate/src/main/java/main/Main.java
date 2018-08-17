package main;

import base.DBService;
import base.datasets.AddressDataSet;
import base.datasets.PhoneDataSet;
import base.datasets.UserDataSet;
import dbService.DBServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args){
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);


        dbService.save(new UserDataSet("Roma", 27, new AddressDataSet("Lukina"),
                new PhoneDataSet("+79026863591"),
                new PhoneDataSet("+79524426980")));
        dbService.save(new UserDataSet("Sasha", 36, new AddressDataSet("Gagarina"),
                new PhoneDataSet("+79050112636"),
                new PhoneDataSet("+79151576325"),
                new PhoneDataSet("+79084783148")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.read(2);
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        dataSets.forEach(System.out::println);

        dataSet = dbService.read(2);
        System.out.println(dataSet);

        dbService.shutdown();
    }
}
