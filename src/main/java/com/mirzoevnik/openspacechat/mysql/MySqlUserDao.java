package com.mirzoevnik.openspacechat.mysql;

import com.mirzoevnik.openspacechat.dao.AbstractJDBCDao;
import com.mirzoevnik.openspacechat.dao.PersistException;
import com.mirzoevnik.openspacechat.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MirzoevNik
 */
public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {

    /**
     * @param connection connection to MySQL
     */
    public MySqlUserDao(Connection connection) {
        super(connection);
    }

    /**
     * @return query for creating user
     */
    @Override
    public String getCreateQuery() {
        return "INSERT INTO open_space_chat.User (login, password, name, surname, country)  VALUES (?, ?, ?, ?, ?);";
    }

    /**
     * @return query for selection user
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM open_space_chat.User";
    }

    /**
     * @return query for updating user
     */
    @Override
    public String getUpdateQuery() {
        return "UPDATE open_space_chat.User SET login = ?, password = ? , name = ?, surname = ?, country = ? WHERE id = ?;";
    }

    /**
     * @return query for deleting user
     */
    @Override
    public String getDeleteQuery() {
        return "DELETE FROM open_space_chat.User WHERE id = ?;";
    }

    /**
     * @return created user
     * @throws PersistException
     */
    @Override
    public User create() throws PersistException {
        User user = new User();
        return persist(user);
    }

    /**
     * @param resultSet result of query
     * @return list of users
     * @throws PersistException
     */
    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
        List<User> users = new LinkedList<>();

        try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setCountry(resultSet.getString("country"));

                users.add(user);
            }
        }
        catch(Exception e) {
            new PersistException(e);
        }
        
        return users;
    }

    /**
     * @param statement prepared query
     * @param user insertable user
     * @throws PersistException
     */
    @Override
    public void prepareStatementForInsert(PreparedStatement statement, User user) throws PersistException {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getCountry());
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @param statement prepared query
     * @param user updated user
     * @throws PersistException
     */
    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, User user) throws PersistException {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getCountry());
            statement.setInt(6, user.getId());
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @param login user's login
     * @param password user's password
     * @return found user
     * @throws PersistException
     */
    public User getByLoginAndPassword(String login, String password) throws PersistException {
        List<User> users;
        String query = getSelectQuery();
        query = query.concat(" WHERE login = ? and password = ?;");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();
            users = parseResultSet(result);
        }
        catch (Exception e) {
            throw new PersistException(e);
        }

        if (users == null || users.isEmpty())
            return null;
        if (users.size() > 1)
            throw new PersistException("Received more than one record");

        return users.iterator().next();
    }
}