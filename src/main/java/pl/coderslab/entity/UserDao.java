package pl.coderslab.entity;

import pl.coderslab.DAO_project.DbUtil;
import pl.coderslab.DAO_project.User;
import pl.coderslab.BCrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users VALUES (null, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email=?, username=? WHERE id =?";
    private static final String UPDATE_PASSWORD_USER_QUERY = "UPDATE users SET password=? WHERE id =?";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL_QUERY = "SELECT * FROM users";
    private static final String DELETE_ALL_QUERY = "DELETE FROM users";

    public User create (User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getUserName());
            stm.setString(3, hashPassword(user.getPassword()));
            if (stm.executeUpdate() == 1) {
                System.out.println("Wpis został utworzony");
            } else {
                System.out.println("Wystąpił problem");
                return null;
            }
            ResultSet res = stm.getGeneratedKeys();
            if (res.next()) {
                user.setId(res.getInt(1));
            }
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Próba dodania użytkownika zakończona niepowodzeniem");
            return null;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read (int userID) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(READ_USER_QUERY);
            stm.setInt(1, userID);
            ResultSet res = stm.executeQuery();
            User user = new User();
            if (res.next()) {
                user.setId(userID);
                user.setEmail(res.getString("email"));
                user.setUserName(res.getString("username"));
                user.setPassword(res.getString("password"));
                return user;
            } else {
                System.out.println("Brak użytkownika o podanym ID.");
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Odczyt zakończony niepowodzeniem");
            return null;
        }
    }

    public void update (User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(UPDATE_USER_QUERY);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getUserName());
            stm.setInt(3, user.getId());
            if (stm.executeUpdate() == 1) {
                System.out.println("Wiersz został zmodyfikowany");
            } else {
                System.out.println("Wystąpił błąd przy próbie modyfikacji wiersza");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Modyfikacja wiersza zakończona niepowodzeniem");
        }
    }

    public void updatePassword (User user, String password) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(UPDATE_PASSWORD_USER_QUERY);
            stm.setString(1, hashPassword(password));
            stm.setInt(2, user.getId());
            if (stm.executeUpdate() == 1) {
                System.out.println("Wiersz został zmodyfikowany");
            } else {
                System.out.println("Wystąpił błąd przy próbie modyfikacji wiersza");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Modyfikacja wiersza zakończona niepowodzeniem");
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(DELETE_USER_QUERY);
            stm.setInt(1, userId);
            if (stm.executeUpdate() == 1) {
                System.out.println("Wpis został usunięty");
            } else {
                System.out.println("Próba usunięcia użytkownika zakończona niepowodzeniem");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Próba usunięcia użytkownika zakończona niepowodzeniem");
        }
    }

    public User[] findAll() {
        User[] usersArray = new User[0];
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(READ_ALL_QUERY);
            ResultSet res = stm.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId(res.getInt("id"));
                user.setEmail(res.getString("email"));
                user.setUserName(res.getString("username"));
                user.setPassword(res.getString("password"));
                usersArray = addToArray(user, usersArray);
            }
            return usersArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Próba odczytania danych zakończona niepowodzeniem");
            return null;
        }
    }

    private User[] addToArray(User u, User[] users) {
        users = Arrays.copyOf(users, users.length+1);
        users[users.length-1] = u;
        return users;
    }

    public void deleteAll() {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement stm = conn.prepareStatement(DELETE_ALL_QUERY);
            stm.executeUpdate();
            System.out.println("Tabela została wyczyszczona");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Próba usunięcia danych zakończona niepowodzeniem");
        }
    }
}
