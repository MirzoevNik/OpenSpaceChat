package com.mirzoevnik.openspacechat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author MirzoevNik
 * @param <T> type of object
 * @param <PK> primary key
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Integer> implements GenericDao<T, PK> {

    protected final Connection connection;

    /**
     * @return query for creating
     */
    public abstract String getCreateQuery();

    /**
     * @return query for selection
     */
    public abstract String getSelectQuery();

    /**
     * @return query for updating
     */
    public abstract String getUpdateQuery();

    /**
     * @return query for deleting
     */
    public abstract String getDeleteQuery();

    /**
     * @param resultSet result of query
     * @return list of objects
     * @throws PersistException
     */
    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;

    /**
     * @param statement prepared query
     * @param entity insertable object
     * @throws PersistException
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T entity) throws PersistException;

    /**
     * @param statement prepared query
     * @param entity updated object
     * @throws PersistException
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T entity) throws PersistException;

    /**
     * @param connection JDBC connection
     */
    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param entity exisiting object
     * @return persisted object in DB
     * @throws PersistException
     */
    @Override
    public T persist(T entity) throws PersistException {
        // if entity is exist in DB
        if (entity.getId() != null) {
            throw new PersistException("Entity is already persist");
        }
        
        T persistInstance;
        
        // creating new persist
        String query = getCreateQuery();
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            prepareStatementForInsert(statement, entity);

            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        }
        catch (Exception e) {
            throw new PersistException(e);
        }

        // selection new persist in DB
        query = getSelectQuery();
        query = query.concat(" WHERE id = LAST_INSERT_ID();");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> result = parseResultSet(resultSet);
            if (result == null || result.size() != 1) {
                throw new PersistException("Exception on find by key new persist data");
            }
            persistInstance = result.iterator().next();
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
        
        return persistInstance;
    }

    /**
     * @param key primary key for searching object
     * @return found object
     * @throws PersistException
     */
    @Override
    public T getByKey(Integer key) throws PersistException {
        List<T> list;
        String query = getSelectQuery();
        query = query.concat(" WHERE id = ?;");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();
            list = parseResultSet(result);
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
        
        if (list == null || list.isEmpty())
            return null;
        if (list.size() > 1)
            throw new PersistException("Received more than one record");

        return list.iterator().next();
    }

    /**
     * @return list of all objects in DB
     * @throws PersistException
     */
    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String query = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            list = parseResultSet(result);
        }
        catch(Exception e) {
            throw new PersistException(e);
        }

        return list;
    }

    /**
     * @param entity exisiting object
     * @throws PersistException
     */
    @Override
    public void update(T entity) throws PersistException {
        String query = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepareStatementForUpdate(statement, entity);
            
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        }
        catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /**
     * @param entity exisiting object
     * @throws PersistException
     */
    @Override
    public void delete(T entity) throws PersistException {
        String query = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try {
                statement.setObject(1, entity.getId());
            }
            catch (Exception e) {
                throw new PersistException(e);
            }
            
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 records: " + count);
            }
            statement.close();
        }
        catch(Exception e) {
            throw new PersistException(e);
        }
    }
}
