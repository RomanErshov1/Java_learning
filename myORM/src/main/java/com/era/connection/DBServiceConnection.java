package com.era.connection;

import com.era.data.DBService;
import com.era.data.DataSet;
import com.era.executor.DataTExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceConnection implements DBService {

    private Connection connection;

    public DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed");
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + connection.getMetaData().getURL() + "\n" +
                    "DB Name: " + connection.getMetaData().getDatabaseProductName() + "\n" +
                    "DB Version: " + connection.getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + connection.getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(DataSet user) throws SQLException {
        DataTExecutor executor = new DataTExecutor(connection);
        executor.save(user);
    }

    @Override
    public DataSet getUser(long id, Class userClass) throws SQLException {
        DataTExecutor executor = new DataTExecutor(connection);
        return executor.load(id, userClass, resultSet -> {
            resultSet.next();
            DataSet returnValue = null;
            try {
                returnValue = (DataSet)userClass.getConstructor(long.class, String.class, int.class)
                        .newInstance(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age"));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw  new RuntimeException(e);
            }
            return returnValue;
        });
    }
}
