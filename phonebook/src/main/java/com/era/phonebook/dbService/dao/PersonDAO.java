package com.era.phonebook.dbService.dao;

import com.era.phonebook.base.datasets.Contact;
import com.era.phonebook.base.datasets.Person;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

public class PersonDAO extends GenericDAOImpl<Person> {
    public PersonDAO(Session session){
        super(session);
    }

    public List<Person> findByPhoneNumber(String phoneNumber){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);
        Metamodel metamodel = session.getEntityManagerFactory().getMetamodel();
        EntityType<Person> Person_ = metamodel.entity(Person.class);
        ListJoin<Person, Contact> join = root.join(Person_.getDeclaredList("contacts", Contact.class));
        criteria.where(criteriaBuilder.like(join.get("phoneNumber"), "%" + phoneNumber + "%"));
        return session.createQuery(criteria).getResultList();
    }
}
