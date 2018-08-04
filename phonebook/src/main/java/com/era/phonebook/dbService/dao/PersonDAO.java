package com.era.phonebook.dbService.dao;

import com.era.phonebook.base.datasets.Person;
import org.hibernate.Session;

public class PersonDAO extends GenericDAOImpl<Person> {
    public PersonDAO(Session session){
        super(session);
    }
}
