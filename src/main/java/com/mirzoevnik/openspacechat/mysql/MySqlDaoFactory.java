package com.mirzoevnik.openspacechat.mysql;

import com.mirzoevnik.openspacechat.dao.*;
import com.mirzoevnik.openspacechat.entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MirzoevNik
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String user = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost:3306/open_space_chat?useUnicode=true&characterEncoding=utf8";
    private String driver = "com.mysql.jdbc.Driver";

    private Map<Class, DaoCreator> creators;

    public MySqlDaoFactory() {
        // loading of the driver for working with DB
        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<>();
        creators.put(User.class, new DaoCreator<Connection>() {

            /**
             * @param connection context for creating of MySqlDao
             * @return created MySqlDao
             */
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });
    }

    /**
     * @return connection to MySQL
     * @throws PersistException
     */
    @Override
    public Connection getContext() throws PersistException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            throw new PersistException(e);
        }

        return connection;
    }

    /**
     * @param connection context for creating of MySqlDao
     * @param daoClass class of MySqlDao
     * @return MySqlDao
     * @throws PersistException
     */
    @Override
    public GenericDao getDao(Connection connection, Class daoClass) throws PersistException {
        DaoCreator creator = creators.get(daoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + daoClass + " not found");
        }

        return creator.create(connection);
    }
}