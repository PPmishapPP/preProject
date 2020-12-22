package jm.task.core.jdbc.util;



import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String userName = "root";
    private static final String password = "setchatka";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public static SessionFactory getFactory()  {

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, url);
        settings.put(Environment.USER, userName);
        settings.put(Environment.PASS, password);
        settings.put(Environment.SHOW_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);

        //ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        //        .applySettings(configuration.getProperties()).build();

        //return configuration.buildSessionFactory(serviceRegistry);

        return configuration.buildSessionFactory();
    }
}
