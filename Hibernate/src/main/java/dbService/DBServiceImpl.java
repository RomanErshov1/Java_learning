package dbService;

import base.DBService;
import base.datasets.AddressDataSet;
import base.datasets.DataSet;
import base.datasets.PhoneDataSet;
import base.datasets.UserDataSet;
import dbService.dao.UserDataSetDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(DataSet.class)
                .addAnnotatedClass(UserDataSet.class)
                .addAnnotatedClass(AddressDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                .configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()){
            session.save(dataSet);
        }
    }

    public UserDataSet read(long id) {
        return runInSession(session ->
        {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session ->
        {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session ->
        {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
