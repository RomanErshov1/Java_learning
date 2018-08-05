package com.era.phonebook.dbService;

import com.era.phonebook.base.DBService;
import com.era.phonebook.base.datasets.CityCode;
import com.era.phonebook.base.datasets.Contact;
import com.era.phonebook.base.datasets.DataSet;
import com.era.phonebook.base.datasets.Person;
import com.era.phonebook.dbService.dao.CityDAO;
import com.era.phonebook.dbService.dao.ContactDAO;
import com.era.phonebook.dbService.dao.PersonDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;

    public DBServiceImpl(){
        Configuration configuration = new Configuration()
                .addAnnotatedClass(DataSet.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(CityCode.class)
                .addAnnotatedClass(Person.class)
                .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                .configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    private <R> R runInSession(Function<Session, R> function){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    @Override
    public void saveCity(CityCode cityCode) {
        runInSession(session -> {
            CityDAO cityDAO = new CityDAO(session);
            return cityDAO.create(cityCode);
        });
    }

    @Override
    public CityCode readCity(long id) {
        return runInSession(session -> {
           CityDAO cityDAO = new CityDAO(session);
           return cityDAO.read(CityCode.class, id);
        });
    }

    @Override
    public void editCity(CityCode newCityCode) {
        runInSession(session -> {
           CityDAO cityDAO = new CityDAO(session);
           cityDAO.update(newCityCode);
           return null;
        });
    }

    @Override
    public void deleteCity(CityCode cityCode) {
        runInSession(session -> {
           CityDAO cityDAO = new CityDAO(session);
           cityDAO.delete(cityCode);
           return null;
        });
    }

    @Override
    public void savePerson(Person person) {
        runInSession(session -> {
            PersonDAO personDAO = new PersonDAO(session);
            return personDAO.create(person);
        });
    }

    @Override
    public Person readPerson(long id) {
        return runInSession(session -> {
            PersonDAO personDAO = new PersonDAO(session);
            return personDAO.read(Person.class, id);
        });
    }

    @Override
    public void editPerson(Person newPerson) {
        runInSession(session -> {
           PersonDAO personDAO = new PersonDAO(session);
           personDAO.update(newPerson);
           return null;
        });
    }

    @Override
    public void deletePerson(Person person) {
        runInSession(session -> {
            PersonDAO personDAO = new PersonDAO(session);
            personDAO.delete(person);
            return null;
        });
    }

    @Override
    public void saveContact(Contact contact) {
        runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            return contactDAO.create(contact);
        });
    }

    @Override
    public Contact readContact(long id) {
        return runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            return contactDAO.read(Contact.class, id);
        });
    }

    @Override
    public void editContact(Contact newContact) {
        runInSession(session -> {
           ContactDAO contactDAO = new ContactDAO(session);
           contactDAO.update(newContact);
           return null;
        });
    }

    @Override
    public void deleteContact(Contact contact) {
        runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            contactDAO.delete(contact);
            return null;
        });
    }

    @Override
    public List<Contact> findContactsByName(String name) {
        return runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            return contactDAO.findByName(name);
        });
    }

    @Override
    public List<Contact> findContactsByCity(String nameCity) {
        return runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            return contactDAO.findByCity(nameCity);
        });
    }

    @Override
    public List<Contact> findContactsByBirthday(Date birthday) {
        return runInSession(session -> {
            ContactDAO contactDAO = new ContactDAO(session);
            return contactDAO.findByBirthday(birthday);
        });
    }

    @Override
    public List<Person> findPersonsByPhoneNumber(String phoneNumber) {
        return runInSession(session -> {
            PersonDAO personDAO = new PersonDAO(session);
            return personDAO.findByPhoneNumber(phoneNumber);
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }
}
