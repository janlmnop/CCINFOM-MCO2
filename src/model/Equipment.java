package model;

import java.sql.*;
import java.util.*;

public class Equipment {
    /* ATTRIBUTES */
    private int equipmentID;
    private String equipmentName;
    private int quantity = 0;
    private String availability;

    /* CONSTRUCTOR */
    public Equipment() {}


    /* GETTERS & SETTERS */
    public int getEquipmentID() {
        return equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAvailability() {
        return availability;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


    /* dito muna yung new -- this came from the tut video*/
    public ArrayList<Integer> equipmentID_list = new ArrayList<>();
    public ArrayList<String> equipmentName_list = new ArrayList<>();
    public ArrayList<Boolean> availability_list = new ArrayList<>();


    // this is to show the db contents in the dropdown options
    public List<String> getEquipmentList() {
        List<String> list = new ArrayList<>();

        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            PreparedStatement pstmt = conn.prepareStatement("SELECT equipment_name FROM equipment ORDER BY equipment_name ASC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("equipment_name"));
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