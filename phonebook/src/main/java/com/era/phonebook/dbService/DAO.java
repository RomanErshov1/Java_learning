package com.era.phonebook.dbService;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
    Serializable create(T instance);

    T read (Class clazz, Serializable id);

    void update(T transientObject);

    void delete(T persistentObject);

    List<T> readAll(Class clazz);
}
