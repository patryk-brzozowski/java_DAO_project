package pl.coderslab.entity;

import pl.coderslab.DAO_project.DbUtil;
import pl.coderslab.DAO_project.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users VALUES (null, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username=?, email=?, password=?";
    private static final String PRINT_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL_QUERY = "SELECT * FROM users";
    private static final String DELETE_ALL_QUERY = "DELETE FROM users";

    public User create (User user) {
        String email = user.getEmail();
        String userName = user.getUserName();
        String password = user.getPassword();
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(CREATE_USER_QUERY);
            stm.setString(1, email);
            stm.setString(2, userName);
            stm.setString(3, password);
            if (stm.executeUpdate() == 1) {
                System.out.println("Wpis został utworzony");
            } else {
                System.out.println("Wystąpił problem");
            }
            stm = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
            stm.setString(1, email);
            ResultSet res = stm.executeQuery();
            res.next();
            int id = res.getInt("id");
            user.setId(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Próba dodania użytkownika zakończona niepowodzeniem");
        }
        return user;
    }
}
