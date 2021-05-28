package pl.coderslab.DAO_project;

public class User {
    private int id;
    private String email;
    private String userName;
    private String password;

    public User() {
    }

    public User (String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return String.format("ID: %s, email: %s, username: %s, password: %s", id, email, userName, password);
    }
}
