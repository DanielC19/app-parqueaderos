package app_parqueaderos;

import java.sql.*;

public class Lot {
    private final int id;
    private boolean available;
    private int user_id;

    public Lot(int id, boolean available, int user_id) {
        this.id = id;
        this.available = available;
        this.user_id = user_id;
    }

    public void reserve(int user_id){
        this.user_id = user_id;
        this.available = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");

            PreparedStatement query = conn.prepareStatement("UPDATE lots SET availability = ?, user_id = ? WHERE id =  ?");
            query.setBoolean(1, false);
            query.setInt(2, this.user_id);
            query.setInt(3, this.id);

            query.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void toggleAvailability() {
        this.available = !this.available;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");

            PreparedStatement query = conn.prepareStatement("UPDATE lots SET availability = ?, user_id = ? WHERE id =  ?");
            query.setBoolean(1, this.available);
            query.setInt(2, 0);
            query.setInt(3, this.id);

            query.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Lot findById(int id) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM lots WHERE id = " + id);

            while (res.next()) {
                return new Lot(res.getInt("id"), res.getBoolean("availability"), res.getInt("user_id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean availability(){
        return this.available;
    }

    public int getId(){
        return this.id;
    }

    public int getUser() {
        return user_id;
    }
}
