package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sqlCreateTable =
                "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT, " +
                "last_name TINYTEXT," +
                " age TINYINT, " +
                "PRIMARY KEY(id))";

        try {
            connection.createStatement().executeUpdate(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String sqlDropTable =
                "DROP TABLE IF EXISTS users";
        try {
            connection.createStatement().executeUpdate(sqlDropTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        String sqlInsertUser = "INSERT INTO users VALUES (null, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsertUser);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        String sqlRemoveUser = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRemoveUser);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlGetAll = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sqlGetAll);

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlCleanTable = "TRUNCATE TABLE users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCleanTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
