package com.era.phonebook.dbService.dao;

import com.era.phonebook.dbService.DAO;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDAOImpl<T> implements DAO<T> {
    protected Session session;

    public GenericDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public Serializable create(T instance) {
        return session.save(instance);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(Class clazz, Serializable id) {
        return (T)session.load(clazz, id);
    }

    @Override
    public void update(T transientObject) {
        session.update(transientObject);
    }

    @Override
    public void delete(T persistentObject) {
        session.delete(persistentObject);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> readAll(Class clazz) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }
}
