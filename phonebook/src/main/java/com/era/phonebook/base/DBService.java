package com.era.phonebook.base;

import com.era.phonebook.base.datasets.CityCode;
import com.era.phonebook.base.datasets.Contact;
import com.era.phonebook.base.datasets.Person;

import java.time.LocalDate;
import java.util.List;

public interface DBService {

    String getLocalStatus();

    void saveCity(CityCode cityCode);

    CityCode readCity(long id);

    void editCity(CityCode newCityCode);

    void deleteCity(CityCode cityCode);

    void savePerson(Person person);

    Person readPerson(long id);

    void editPerson(Person newPerson);

    void deletePerson(Person person);

    void saveContact(Contact contact);

    Contact readContact(long id);

    void editContact(Contact newContact);

    void deleteContact(Contact contact);

    List<Contact> findContactsByName(String name);

    List<Contact> findContactsByCity(String nameCity);

    List<Contact> findContactsByBirthday(LocalDate birthday);

    List<Person> findPersonsByPhoneNumber(String phoneNumber);

    void shutdown();
}