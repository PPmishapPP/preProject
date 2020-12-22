package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory;

    public UserDaoHibernateImpl() {
        factory = Util.getFactory();
    }


    @Override
    public void createUsersTable() {
        String sqlCreateTable =
                "CREATE TABLE IF NOT EXISTS users " +
                        "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT, " +
                        "last_name TINYTEXT," +
                        " age TINYINT, " +
                        "PRIMARY KEY(id))";
        try(Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sqlCreateTable).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDropTable =
                "DROP TABLE IF EXISTS users";
        try(Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sqlDropTable).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete User where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()){
            return session.createQuery("from User").getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User");
            query.executeUpdate();
            transaction.commit();
        }
    }
}
