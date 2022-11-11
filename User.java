package app_parqueaderos;

import java.sql.*;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE email = '"+email+"' AND password = '"+password+"'");

            while (res.next()) {
                this.id = res.getInt("id");
                this.name = res.getString("name");
                this.email = res.getString("email");
                this.password = res.getString("password");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT id FROM users WHERE email = '"+email+"' AND password = '"+password+"'");
            while (res.next()) {
                this.id = res.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean register(String name, String email, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");

            PreparedStatement query = conn.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
            query.setString(1, name);
            query.setString(2, email);
            query.setString(3, password);

            int res = query.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean login(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT id FROM users WHERE email = '"+email+"' AND password = '"+password+"'");

            return res.next();
        } catch (Exception e) {
            return false;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
