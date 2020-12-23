package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        modifyTable(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT, " +
                        "last_name TINYTEXT," +
                        " age TINYINT, " +
                        "PRIMARY KEY(id))");
    }

    public void dropUsersTable() {
        modifyTable("DROP TABLE IF EXISTS users");
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlInsertUser = "INSERT INTO users VALUES (null, ?, ?, ?)";
        try {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sqlInsertUser)) {
                    statement.setString(1, name);
                    statement.setString(2, lastName);
                    statement.setByte(3, age);
                    statement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveUser = "DELETE FROM users WHERE id = ?";
        try {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(sqlRemoveUser)) {
                    statement.setLong(1, id);
                    statement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlGetAll = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(sqlGetAll);
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("name"),
                            resultSet.getString("last_name"),
                            resultSet.getByte("age"));
                    user.setId(resultSet.getLong("id"));
                    list.add(user);
                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        modifyTable("TRUNCATE TABLE users");
    }

    private void modifyTable(String sql) {
        try {
            try {
                connection.setAutoCommit(false);
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
