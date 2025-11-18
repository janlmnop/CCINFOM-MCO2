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

    /* Shelter report helpers */
    public List<String> getShelterList() {
        List<String> list = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            PreparedStatement pstmt = conn.prepareStatement("SELECT shelter_id, shelter_name FROM shelter ORDER BY shelter_name ASC");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("shelter_id") + " - " + rs.getString("shelter_name"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<String[]> getShelterOccupancyReport(int year, int month, int shelterId) {
        List<String[]> reportData = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // Prepare shelter list (either single shelter or all shelters)
            String shelterQuery = "SELECT shelter_id, shelter_name, capacity FROM shelter" + (shelterId > 0 ? " WHERE shelter_id = ?" : "") + " ORDER BY shelter_name ASC";
            PreparedStatement psShel = conn.prepareStatement(shelterQuery);
            if (shelterId > 0) psShel.setInt(1, shelterId);
            ResultSet rsShel = psShel.executeQuery();

            // prepare time bounds for the month
            java.time.YearMonth ym = java.time.YearMonth.of(year, month);
            java.time.LocalDate monthStart = ym.atDay(1);
            java.time.LocalDate monthEnd = ym.atEndOfMonth();
            java.time.LocalDateTime monthStartDt = monthStart.atStartOfDay();
            java.time.LocalDateTime monthEndDt = monthEnd.atTime(23, 59, 59);

            while (rsShel.next()) {
                int sid = rsShel.getInt("shelter_id");
                String sname = rsShel.getString("shelter_name");
                int cap = rsShel.getInt("capacity");

                // Fetch all responses for this shelter that overlap the month
                String respQuery = "SELECT r.response_id, r.response_start, r.response_end "
                                  + "FROM response r "
                                  + "WHERE r.shelter_id = ? AND r.response_start <= ? AND r.response_end >= ?";
                PreparedStatement psResp = conn.prepareStatement(respQuery);
                psResp.setInt(1, sid);
                psResp.setTimestamp(2, java.sql.Timestamp.valueOf(monthEndDt));
                psResp.setTimestamp(3, java.sql.Timestamp.valueOf(monthStartDt));
                ResultSet rsResp = psResp.executeQuery();

                // collect resident intervals (residentId, start, end)
                class Interval { int residentId; java.time.LocalDateTime start; java.time.LocalDateTime end; }
                List<Interval> intervals = new ArrayList<>();

                while (rsResp.next()) {
                    int rid = rsResp.getInt("response_id");
                    java.sql.Timestamp tsStart = rsResp.getTimestamp("response_start");
                    java.sql.Timestamp tsEnd = rsResp.getTimestamp("response_end");
                    java.time.LocalDateTime start = tsStart != null ? tsStart.toLocalDateTime() : monthStartDt;
                    java.time.LocalDateTime end = tsEnd != null ? tsEnd.toLocalDateTime() : monthEndDt;

                    // get residents for this response with role 'evacuated'
                    PreparedStatement psRR = conn.prepareStatement("SELECT resident_id FROM response_resident WHERE response_id = ? AND role = 'evacuated'");
                    psRR.setInt(1, rid);
                    ResultSet rsRR = psRR.executeQuery();
                    while (rsRR.next()) {
                        Interval iv = new Interval();
                        iv.residentId = rsRR.getInt("resident_id");
                        iv.start = start;
                        iv.end = end;
                        intervals.add(iv);
                    }
                    rsRR.close();
                    psRR.close();
                }
                rsResp.close();
                psResp.close();

                // total distinct residents who were present at least one day in the month
                Set<Integer> distinctResidents = new HashSet<>();
                for (Interval iv : intervals) {
                    // consider overlap with month boundaries
                    if (!iv.end.isBefore(monthStartDt) && !iv.start.isAfter(monthEndDt)) {
                        distinctResidents.add(iv.residentId);
                    }
                }
                int totalResidents = distinctResidents.size();

                // average daily occupancy for the month: for each day count distinct residents present that day
                int daysInMonth = ym.lengthOfMonth();
                double sumDaily = 0.0;
                for (int d = 1; d <= daysInMonth; d++) {
                    java.time.LocalDateTime dayStart = ym.atDay(d).atStartOfDay();
                    java.time.LocalDateTime dayEnd = ym.atDay(d).atTime(23,59,59);
                    Set<Integer> presentThatDay = new HashSet<>();
                    for (Interval iv : intervals) {
                        if (!iv.end.isBefore(dayStart) && !iv.start.isAfter(dayEnd)) {
                            presentThatDay.add(iv.residentId);
                        }
                    }
                    sumDaily += presentThatDay.size();
                }
                double avgDailyOccupancy = daysInMonth > 0 ? sumDaily / daysInMonth : 0.0;

                String[] row = {
                    String.valueOf(sid),
                    sname,
                    String.valueOf(cap),
                    String.valueOf(totalResidents),
                    String.format("%.2f", avgDailyOccupancy)
                };
                reportData.add(row);
            }

            rsShel.close();
            psShel.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reportData;
    }
}