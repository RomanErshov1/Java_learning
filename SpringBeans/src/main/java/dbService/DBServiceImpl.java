package dbService;

import base.DBService;
import base.datasets.AddressDataSet;
import base.datasets.DataSet;
import base.datasets.PhoneDataSet;
import base.datasets.UserDataSet;
import cache.CacheElement;
import cache.CacheEngine;
import cache.CacheEngineImpl;
import dbService.dao.UserDataSetDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.function.Function;

public class DBServiceImpl implements DBService {

    private static final int CACHE_SIZE = 6;
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, UserDataSet> cache =
            new CacheEngineImpl<>(CACHE_SIZE, 0, 0, true);
    private final CacheEngine<String, UserDataSet> nameCache =
            new CacheEngineImpl<>(CACHE_SIZE, 0, 1000, false);

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
        runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
            return null;
        });
    }

    public UserDataSet read(long id) {
        CacheElement<Long, UserDataSet> cacheElement = cache.get(id);

        if (cacheElement == null) {
            UserDataSet baseDataSet =  runInSession(session ->
            {
                UserDataSetDAO dao = new UserDataSetDAO(session);
                return dao.read(id);
            });
            cache.put(new CacheElement<>(id, baseDataSet));
            return baseDataSet;
        }

        return cacheElement.getValue();
    }

    public UserDataSet readByName(String name) {
        CacheElement<String, UserDataSet> cacheElement = nameCache.get(name);
        if (cacheElement == null){
            UserDataSet baseDataSet = runInSession(session ->
            {
                UserDataSetDAO dao = new UserDataSetDAO(session);
                return dao.readByName(name);
            });
            nameCache.put(new CacheElement<>(name, baseDataSet));
            return baseDataSet;
        }

        return cacheElement.getValue();
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

    @Override
    public int getCacheSize() {
        return cache.getSize();
    }

    @Override
    public int getCacheHit() {
        return cache.getHitCount();
    }

    @Override
    public int getCahceMiss() {
        return cache.getMissCount();
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
