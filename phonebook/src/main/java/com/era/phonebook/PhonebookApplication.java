package com.era.phonebook;

import com.era.phonebook.base.DBService;
import com.era.phonebook.base.datasets.CityCode;
import com.era.phonebook.base.datasets.Contact;
import com.era.phonebook.base.datasets.Person;
import com.era.phonebook.dbService.DBServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PhonebookApplication {

    public static void main(String[] args) {

        DBService dbService = new DBServiceImpl();
        System.out.println(dbService.getLocalStatus());

        testCityTable(dbService);

        testPerson(dbService);

        testContact(dbService);

        dbService.shutdown();
    }

    private static void testContact(DBService dbService) {
        CityCode city_one = dbService.readCity(2);
        List<Person> personList = new ArrayList<>();
        personList.add(dbService.readPerson(1));
        personList.add(dbService.readPerson(2));
        dbService.saveContact(new Contact("+7(831)2796160", "city", city_one, personList));

        personList = new ArrayList<>();
        personList.add(dbService.readPerson(3));
        Contact contact = new Contact("+79050112636", "mobile", null, personList);
        dbService.saveContact(contact);
        contact.setCityCode(dbService.readCity(3));
        contact.setPhoneNumber("+7(812)1572498");
        dbService.editContact(contact);

        personList = new ArrayList<>();
        personList.add(dbService.readPerson(1));
        dbService.saveContact(new Contact("+7(831)270-61-00", "city", city_one, personList));

        contact = new Contact("+79105874570", "mobile", null, personList);
        dbService.saveContact(contact);
        dbService.deleteContact(contact);

        System.out.println(dbService.readContact(1));
        System.out.println(dbService.readContact(2));
    }

    private static void testPerson(DBService dbService) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dbService.savePerson(new Person("Roma", formatter.parse("06-08-1991"), "Good"));
            Person sasha = new Person("Sasha", formatter.parse("29-05-1982"), "");
            dbService.savePerson(sasha);
            sasha.setBirthday(formatter.parse("28-05-1982"));
            dbService.editPerson(sasha);
            dbService.savePerson(new Person("Yura", formatter.parse("14-12-1993"), "Yurec-molodec"));
            Person vasya = new Person("Vasya", formatter.parse("12-12-1996"),"Fool");
            dbService.savePerson(vasya);
            dbService.deletePerson(vasya);
            System.out.println(dbService.readPerson(1));
            System.out.println(dbService.readPerson(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void testCityTable(DBService dbService) {
        dbService.saveCity(new CityCode("Moscow", "495"));
        CityCode nizhny = new CityCode("Nizhny Novgorod", "812");
        dbService.saveCity(nizhny);
        nizhny.setCode("831");
        dbService.editCity(nizhny);
        dbService.saveCity(new CityCode("Saint Petersburg", "812"));
        CityCode samara = new CityCode("Samara", "846");
        dbService.saveCity(samara);
        dbService.deleteCity(samara);
        System.out.println(dbService.readCity(1));
        System.out.println(dbService.readCity(2));
    }
}
