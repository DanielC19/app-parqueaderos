package app_parqueaderos;

import java.util.ArrayList;
import java.sql.*;

public class Section {
    private String name;
    private ArrayList<Lot> lots = new ArrayList<>();

    public Section(int id, String name) {
        this.name = name;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT id, user_id, availability FROM lots WHERE section_id = " + id);

            while (res.next()) {
                Lot lot = new Lot(res.getInt("id"), res.getBoolean("availability"), res.getInt("user_id"));
                this.lots.add(lot);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int availability() {
        int counter = 0;
        for (Lot lot : this.lots) {
            if (lot.availability()) counter++;
        }
        return counter;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Lot> getLots() {
        return this.lots;
    }
}
