package dao;

import com.mirzoevnik.openspacechat.dao.GenericDao;
import com.mirzoevnik.openspacechat.dao.Identified;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.Serializable;
import java.util.List;

@RunWith(Parameterized.class)
public abstract class GenericDaoTest<Context> {

    protected Class daoClass;

    protected Identified notPersistedDao;

    public GenericDaoTest(Class daoClass, Identified<Integer> notPersistedDao) {
        this.daoClass = daoClass;
        this.notPersistedDao = notPersistedDao;
    }

    public abstract GenericDao dao();

    public abstract Context context();

    @Test
    public void testCreate() throws Exception {
        Identified dao = dao().create();

        Assert.assertNotNull(dao);
        Assert.assertNotNull(dao.getId());
    }

    @Test
    public void testPersist() throws Exception {
        Assert.assertNull("Id befor persist is not null", notPersistedDao.getId());

        notPersistedDao = dao().persist(notPersistedDao);

        Assert.assertNotNull("After persist id is null", notPersistedDao.getId());
    }

    @Test
    public void testGetByKey() throws Exception {
        Serializable key = dao().create().getId();
        Identified dao = dao().getByKey(key);
        Assert.assertNotNull(dao);
    }

    @Test
    public void testGetAll() throws Exception {
        List list = dao().getAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testDelete() throws Exception {
        Identified dao = dao().create();
        Assert.assertNotNull(dao);

        List list = dao().getAll();
        Assert.assertNotNull(list);

        int oldListSize = list.size();
        Assert.assertTrue(oldListSize > 0);

        dao().delete(dao);

        list = dao().getAll();
        Assert.assertNotNull(list);

        int newListSize = list.size();
        Assert.assertEquals(1, oldListSize - newListSize);
    }

}
