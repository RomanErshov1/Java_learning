package com.era.data;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void saveUser(DataSet user) throws SQLException;

    DataSet getUser(long id, Class userClass) throws SQLException;
}
