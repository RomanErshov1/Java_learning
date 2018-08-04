package com.era.phonebook.dbService.dao;

import com.era.phonebook.base.datasets.Contact;
import org.hibernate.Session;

public class ContactDAO extends GenericDAOImpl<Contact> {
    public ContactDAO(Session session){
        super(session);
    }
}
