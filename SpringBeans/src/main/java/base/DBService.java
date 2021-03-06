package base;

import base.datasets.UserDataSet;

import java.util.List;

public interface DBService {
    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> readAll();

    void shutdown();

    int getCacheSize();

    int getCacheHit();

    int getCahceMiss();
}
