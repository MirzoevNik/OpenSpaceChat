package dao;

import com.mirzoevnik.openspacechat.dao.DaoFactory;
import com.mirzoevnik.openspacechat.dao.GenericDao;
import com.mirzoevnik.openspacechat.dao.Identified;
import com.mirzoevnik.openspacechat.dao.PersistException;
import com.mirzoevnik.openspacechat.entities.User;
import com.mirzoevnik.openspacechat.mysql.MySqlDaoFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

public class MySqlDaoTest extends GenericDaoTest<Connection> {

    private Connection connection;

    private GenericDao dao;

    private static final DaoFactory<Connection> factory = new MySqlDaoFactory();

    @Parameterized.Parameters
    public static Collection getParametrs() {
        User testUser = new User();
        testUser.setLogin("test");
        testUser.setPassword("test");
        testUser.setName("test");

        return Arrays.asList(new Object[][] {
                {User.class, testUser}
        });
    }

    public MySqlDaoTest(Class daoClass, Identified<Integer> notPersistedDao) {
        super(daoClass, notPersistedDao);
    }

    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }
    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public GenericDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

}
