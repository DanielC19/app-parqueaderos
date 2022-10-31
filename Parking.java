package app_parqueaderos;

import java.util.ArrayList;
import java.sql.*;

public class Parking {
    private String name;
    private ArrayList<Section> sections = new ArrayList<>();

    public Parking(int id, String name) {
        this.name = name;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT id, name FROM sections WHERE parking_id = " + id);

            while (res.next()) {
                Section section = new Section(res.getInt("id"), res.getString("name"));
                this.sections.add(section);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Parking> buildParking() {
        ArrayList<Parking> parking = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderos", "root", "");
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT id, name FROM parking");

            while (res.next()) {
                Parking item = new Parking(res.getInt("id"), res.getString("name"));
                parking.add(item);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return parking;
    }

    public int availability() {
        int counter = 0;
        for (Section section : this.sections) {
            counter += section.availability();
        }
        return counter;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Section> getSections() {
        return this.sections;
    }
}
