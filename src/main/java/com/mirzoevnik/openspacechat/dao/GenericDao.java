package com.mirzoevnik.openspacechat.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author MirzoevNik
 * @param <T> type of object
 * @param <PK> primary key
 */
public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /**
     * @return created object
     * @throws PersistException
     */
    T create() throws PersistException;

    /**
     * @param entity exisiting object
     * @return persisted object in DB
     * @throws PersistException
     */
    T persist(T entity) throws PersistException;

    /**
     * @param key primary key for searching object
     * @return found object
     * @throws PersistException
     */
    T getByKey(PK key) throws PersistException;

    /**
     * @return list of all objects in DB
     * @throws PersistException
     */
    List<T> getAll() throws PersistException;

    /**
     * @param entity exisiting object
     * @throws PersistException
     */
    void update(T entity) throws PersistException;

    /**
     * @param entity exisiting object
     * @throws PersistException
     */
    void delete(T entity) throws PersistException;
}