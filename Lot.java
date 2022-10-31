package app_parqueaderos;

import java.sql.*;

public class Lot {
    private int id;
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

            PreparedStatement query = conn.prepareStatement("UPDATE lots SET available = ? WHERE id =  ?");
            query.setBoolean(1, false);
            query.setInt(2, this.id);

            query.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void toggleAvailability() {
        if (this.available) {
            this.available = false;
        } else {
            this.available = true;
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");

            PreparedStatement query = conn.prepareStatement("UPDATE lots SET available = ? WHERE id =  ?");
            query.setBoolean(1, this.available);
            query.setInt(2, this.id);

            query.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
