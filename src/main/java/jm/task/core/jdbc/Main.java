package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    private static UserService userService;

    public static void main(String[] args) {
        userService = new UserServiceImpl();
        userService.createUsersTable();

        User user1 = new User("Иван", "Иванов", (byte) 25);
        User user2 = new User("Пётр", "Петров", (byte) 24);
        User user3 = new User("Василий", "Васильев", (byte) 23);
        User user4 = new User("Герман", "Севостьянов", (byte) 34);

        saveUser(user1);
        saveUser(user2);
        saveUser(user3);
        saveUser(user4);


        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
    private static void saveUser(User user){
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());
    }
}
