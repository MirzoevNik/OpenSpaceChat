package com.mirzoevnik.openspacechat.dao;

/**
 * @author MirzoevNik
 * @param <Context> context for creating of dao
 */
public interface DaoFactory<Context> {

    /**
     * @param <Context> context for creating of dao
     */
    interface DaoCreator<Context> {

        /**
         * @param context context for creating of dao
         * @return created dao
         */
        GenericDao create(Context context);
    }

    /**
     * @return context
     * @throws PersistException
     */
    Context getContext() throws PersistException;

    /**
     * @param context context for creating of dao
     * @param daoClass class of dao
     * @return dao
     * @throws PersistException
     */
    GenericDao getDao(Context context, Class daoClass) throws PersistException;
}
