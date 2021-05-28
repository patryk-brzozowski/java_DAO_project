package pl.coderslab.entity;

import pl.coderslab.DAO_project.User;

public class Test {
    public static void main(String[] args) {
        UserDao usDao = new UserDao();

//        String email = ("test3@o2.pl");
//        String userName = ("Janusz");
//        String password = ("password");
//        User createUser = new User(email, userName, password);
//        createUser = usDao.create(createUser);
//        System.out.println(createUser);

        User readUser = usDao.read(5);
        System.out.println(readUser);

        User[] users = usDao.findAll();
        for (User user: users) {
            System.out.println(user);
        }

//        User updateUser = new User("email@email.com", "username", "admin");
//        updateUser.setId(5);
//        usDao.update(updateUser);
//        System.out.println(updateUser);

//        usDao.delete(7);

//        usDao.deleteAll();;

    }
}
