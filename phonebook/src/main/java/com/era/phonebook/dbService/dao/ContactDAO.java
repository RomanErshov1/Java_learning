package com.era.phonebook.dbService.dao;

import com.era.phonebook.base.datasets.CityCode;
import com.era.phonebook.base.datasets.Contact;
import com.era.phonebook.base.datasets.Person;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Date;
import java.util.List;


public class ContactDAO extends GenericDAOImpl<Contact> {
    public ContactDAO(Session session){
        super(session);
    }

    @SuppressWarnings("unchecked")
    public List<Contact> findByName(String name){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);
        Metamodel metamodel = session.getEntityManagerFactory().getMetamodel();
        EntityType<Contact> Contact_ = metamodel.entity(Contact.class);
        ListJoin<Contact, Person> join = root.join(Contact_.getDeclaredList("persons", Person.class));
        criteria.where(criteriaBuilder.like(join.get("fullName"), "%" + name + "%"));
        return session.createQuery(criteria).getResultList();
    }

    public List<Contact> findByCity(String cityName){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);
        Metamodel metamodel = session.getEntityManagerFactory().getMetamodel();
        EntityType<Contact> Contact_ = metamodel.entity(Contact.class);
        Join<Contact, CityCode> join = root.join(Contact_.getSingularAttribute("cityCode", CityCode.class));
        criteria.where(criteriaBuilder.like(join.get("title"), "%" + cityName + "%"));
        return session.createQuery(criteria).getResultList();
    }

    public List<Contact> findByBirthday(Date birthday){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteria.from(Contact.class);
        Metamodel metamodel = session.getEntityManagerFactory().getMetamodel();
        EntityType<Contact> Contact_ = metamodel.entity(Contact.class);
        ListJoin<Contact, Person> join = root.join(Contact_.getDeclaredList("persons", Person.class));
        criteria.where(criteriaBuilder.equal(join.get("birthday").as(java.sql.Date.class), birthday));
        return session.createQuery(criteria).getResultList();
    }
}
