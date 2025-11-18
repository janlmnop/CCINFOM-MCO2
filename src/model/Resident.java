package model;

import java.sql.*;
import java.util.*;

public class Resident extends Person {
    /* ATTRIBUTES */
    private int residentID;     // could be ULID


    /* CONSTRUCTOR */
    public Resident() {}


    /* GETTERS & SETTERS */
    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }


    /* METHODS */
    public List<String> getResidentList() {
        List<String> list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            PreparedStatement pstmt = conn.prepareStatement("SELECT CONCAT(first_name, \" \", last_name) AS full_name FROM resident ORDER BY first_name, last_name ASC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("full_name"));
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
}