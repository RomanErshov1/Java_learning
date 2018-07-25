package com.era.executor;

import com.era.data.DataSet;
import com.era.data.UserAge;
import com.era.data.UserName;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DataTExecutor {
    private Connection connection;

    private static final String INSERT_QUERY = "insert into users (name, age) values ('%s', '%d')";
    private static final String SELECT_QUERY = "select * from users where id = %d";

    public DataTExecutor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public <T extends DataSet> void save (T user) throws SQLException {
        try (Statement statement = connection.createStatement()) {

            String name = "";
            int age = 0;
            Field[] fields = user.getClass().getDeclaredFields();
            try {
                Field nameField = Arrays
                        .stream(fields)
                        .filter(field -> field.isAnnotationPresent(UserName.class))
                        .findFirst().get();
                nameField.setAccessible(true);
                name = (String) nameField.get(user);

                Field ageField = Arrays
                        .stream(fields)
                        .filter(field -> field.isAnnotationPresent(UserAge.class))
                        .findFirst()
                        .get();

                ageField.setAccessible(true);
                age = ageField.getInt(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            statement.execute(String.format(INSERT_QUERY, name, age));
            System.out.println("Insert " + statement.getUpdateCount() + " rows");
        }
    }

    public <T extends DataSet> T load (long id, Class<T> clazz, TResultHandler<T> handler) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(String.format(SELECT_QUERY, id));
            ResultSet resultSet = statement.getResultSet();
            return handler.handle(resultSet);
        }
    }
}
