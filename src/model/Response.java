package model;

import java.sql.*;
import java.time.*;
import java.util.*;

public class Response {
    /* ATTRIBUTES */
    private int responseID;
    private int disasterID;
    private int shelterID;
    private int employeeID;
    private String responseType;            // RS, E, MA, RL, RO
    private LocalDateTime responseStart;    // YYYY-MM-DD HH:MI:SS       
    private LocalDateTime responseEnd;

    
    public Response() {}

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public int getDisasterID() {
        return disasterID;
    }

    public int getShelterID() {
        return shelterID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getResponseType() {
        return responseType;
    }

    public LocalDateTime getResponseStart() {
        return responseStart;
    }

    public LocalDateTime getResponseEnd() {
        return responseEnd;
    }
    
    /* GETS VALUES FOR DROPDOWN OPTIONS */
    public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT YEAR(date_occurred) as year FROM disaster ORDER BY year DESC");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return years;
    }
    
    public List<String> getDisasterTypes() {
        List<String> types = new ArrayList<>();
        types.add("All Disasters"); // Default option
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            String query = "SELECT DISTINCT disaster_type FROM disaster ORDER BY disaster_type";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                types.add(rs.getString("disaster_type"));
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return types;
    }
    
    /* REPORT GENERATION */
    public List<String[]> getRescueReportByMonth(int year, int month) {
        List<String[]> reportData = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            // multiple line query
            String query = """
                SELECT d.disaster_id, d.disaster_type, d.date_occurred, d.location, COUNT(rr.resident_id) AS total_residents_rescued
                FROM disaster d
                LEFT JOIN response res ON d.disaster_id = res.disaster_id
                LEFT JOIN response_resident rr ON res.response_id = rr.response_id
                WHERE YEAR(d.date_occurred) = ? AND MONTH(d.date_occurred) = ?
                GROUP BY d.disaster_id, d.disaster_type, d.date_occurred, d.location
                ORDER BY total_residents_rescued DESC
                """;
                
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String[] row = {
                    String.valueOf(rs.getInt("disaster_id")),
                    rs.getString("disaster_type"),
                    rs.getString("date_occurred"),
                    rs.getString("location"),
                    String.valueOf(rs.getInt("total_residents_rescued"))
                };
                reportData.add(row);
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return reportData;
    }
    
    public double getAverageRescuedPerDisaster(int year, int month) {
        double average = 0;
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            String query = """
                SELECT AVG(resident_count) AS avg_residents_per_disaster
                FROM (SELECT d.disaster_id, COUNT(rr.resident_id) AS resident_count
                    FROM disaster d
                    LEFT JOIN response res ON d.disaster_id = res.disaster_id
                    LEFT JOIN response_resident rr ON res.response_id = rr.response_id
                    WHERE YEAR(d.date_occurred) = ? AND MONTH(d.date_occurred) = ?
                    GROUP BY d.disaster_id
                ) disaster_stats
                """;
                
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                average = rs.getDouble("avg_residents_per_disaster");
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return average;
    }
}