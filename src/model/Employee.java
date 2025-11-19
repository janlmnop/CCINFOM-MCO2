package model;

import java.sql.*;
import java.util.*;

public class Employee extends Person {
    /* ATTRIBUTES */
    private int employeeID;         // or ULID
    private String committee;
    private String position;
    private boolean availability;   // T for available and F for not || if you want to declare an enum, it should be a separate class
    private String password;


    /* CONSTRUCTOR(S) */    
    public Employee() {}


    /* GETTERS & SETTERS */
    public int getEmployeeID() {
        return employeeID;
    }

    public String getCommittee() {
        return committee;
    }

    public String getPosition() {
        return position;
    }

    public boolean getAvailability() {
        return availability;
    }

    public String getPassword() {
        return password;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* METHODS */
    public List<String> getEmployeeList() {
        List<String> list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            PreparedStatement pstmt = conn.prepareStatement("SELECT CONCAT(first_name, \" \", last_name) AS full_name FROM employee ORDER BY first_name, last_name ASC");
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

    public boolean logUserIn(int employeeID, String password) {
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee WHERE employee_id = ? AND 'password' = ?");

            pstmt.setInt(1, employeeID);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}