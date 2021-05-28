package pl.coderslab.entity;

import pl.coderslab.DAO_project.DbUtil;
import pl.coderslab.DAO_project.User;

public class Test {
    public static void main(String[] args) {
//        User user = new User();
//        user.setEmail("test3@o2.pl");
//        user.setUserName("Janusz");
//        user.setPassword("gdsfgrg");
        UserDao usDao = new UserDao();
//        user = usDao.create(user);
//        System.out.println(user.getId());
//
        User[] users = usDao.findAll();
        for (User user: users) {
            System.out.println(user.getId() + " " + user.getUserName() + " "
                    + user.getEmail() + " " + user.getPassword());
        }
//
//        System.out.println(usDao.read(2));
//        usDao.delete(7);
//        user1.setUserName("Krystian0003");
//        user1.setEmail("krystianx@wp.pl");
//        user1.setPassword("admin");
//        usDao.Update(user1);
    }
}
