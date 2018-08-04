package com.era.phonebook.dbService.dao;

import com.era.phonebook.base.datasets.CityCode;
import org.hibernate.Session;

public class CityDAO extends GenericDAOImpl<CityCode> {
    public CityDAO(Session session){
        super(session);
    }
}
