package com.era.main;

import com.era.connection.DBServiceConnection;
import com.era.data.DBService;
import com.era.data.UserDataSet;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try(DBService dbService = new DBServiceConnection()){
            System.out.println(dbService.getMetaData());
            dbService.saveUser(new UserDataSet(1, "Sasha", 35));

            UserDataSet roma = (UserDataSet)dbService.getUser(2, UserDataSet.class);
            System.out.println("Selected: " + roma.getName() + " " + roma.getAge());
        }
    }
}
