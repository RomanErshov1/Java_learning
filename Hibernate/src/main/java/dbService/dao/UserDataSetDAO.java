package dbService.dao;

import base.datasets.UserDataSet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet){
        session.save(dataSet);
    }

    public UserDataSet read(long id){
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet readByName(String name){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteriaQuery = criteriaBuilder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteriaQuery.from(UserDataSet.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteriaQuery);
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll(){
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteriaQuery = criteriaBuilder.createQuery(UserDataSet.class);
        criteriaQuery.from(UserDataSet.class);
        return session.createQuery(criteriaQuery).list();
    }
}
