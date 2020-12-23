package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory factory;

    public UserDaoHibernateImpl() {
        factory = Util.getFactory();
    }

    @Override
    public void createUsersTable() {
        modifyTable("CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT, " +
                "last_name TINYTEXT," +
                " age TINYINT, " +
                "PRIMARY KEY(id))");
    }

    @Override
    public void dropUsersTable() {
        modifyTable("DROP TABLE IF EXISTS users");
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            Query query = session.createQuery("delete User where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User").getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        try  {
            Query query = session.createQuery("delete from User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    private void modifyTable(String sql) {
        Session session = factory.openSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        try {
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
