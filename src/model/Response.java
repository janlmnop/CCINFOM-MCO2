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
    public List<String[]> getRescueReportByMonth(int year, int month, String disasterType) {
        List<String[]> reportData = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            StringBuilder query = new StringBuilder("""
                SELECT d.disaster_id, d.disaster_type, d.date_occurred, d.location, COUNT(rr.resident_id) AS total_residents_rescued
                FROM disaster d
                LEFT JOIN response res ON d.disaster_id = res.disaster_id
                LEFT JOIN response_resident rr ON res.response_id = rr.response_id
                WHERE YEAR(d.date_occurred) = ? AND MONTH(d.date_occurred) = ?
                """);
            
            // add disaster type filter if not "All Disasters"
            if (disasterType != null && !disasterType.equals("All Disasters")) {
                query.append(" AND d.disaster_type = ?");
            }
            
            query.append(" GROUP BY d.disaster_id, d.disaster_type, d.date_occurred, d.location ORDER BY total_residents_rescued DESC");
            
            PreparedStatement pstmt = conn.prepareStatement(query.toString());
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            
            // set disaster type parameter if needed
            if (disasterType != null && !disasterType.equals("All Disasters")) {
                pstmt.setString(3, disasterType);
            }
            
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
    
    public double getAverageRescuedPerDisaster(int year, int month, String disasterType) {
        double average = 0;
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            
            // Build the query dynamically based on filters
            StringBuilder query = new StringBuilder("""
                SELECT AVG(resident_count) AS avg_residents_per_disaster
                FROM (SELECT d.disaster_id, COUNT(rr.resident_id) AS resident_count
                    FROM disaster d
                    LEFT JOIN response res ON d.disaster_id = res.disaster_id
                    LEFT JOIN response_resident rr ON res.response_id = rr.response_id
                    WHERE YEAR(d.date_occurred) = ? AND MONTH(d.date_occurred) = ?
                """);
            
            // Add disaster type filter if not "All Disasters"
            if (disasterType != null && !disasterType.equals("All Disasters")) {
                query.append(" AND d.disaster_type = ?");
            }
            
            query.append(" GROUP BY d.disaster_id) disaster_stats");
            
            PreparedStatement pstmt = conn.prepareStatement(query.toString());
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            
            // Set disaster type parameter if needed
            if (disasterType != null && !disasterType.equals("All Disasters")) {
                pstmt.setString(3, disasterType);
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                average = rs.getDouble("avg_residents_per_disaster");
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error calculating average: " + e.getMessage());
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

                // collect resident intervals by using evacuation response_start as the start
                // and preferring a linked 'released' response's response_start as the end.
                class Interval { int residentId; java.time.LocalDateTime start; java.time.LocalDateTime end; }
                List<Interval> intervals = new ArrayList<>();

                // include any evacuation that started on or before the month end
                // (do NOT filter by response_end here — we compute end from linked 'released' or use now)
                String evacQuery = "SELECT rr.resident_id, r.response_id, r.response_start, r.response_end "
                    + "FROM response_resident rr JOIN response r ON rr.response_id = r.response_id "
                    + "WHERE rr.role = 'evacuated' AND r.shelter_id = ? AND r.response_start <= ?";
                PreparedStatement psEvac = conn.prepareStatement(evacQuery);
                psEvac.setInt(1, sid);
                psEvac.setTimestamp(2, java.sql.Timestamp.valueOf(monthEndDt));
                ResultSet rsEvac = psEvac.executeQuery();

                while (rsEvac.next()) {
                    Interval iv = new Interval();
                    iv.residentId = rsEvac.getInt("resident_id");
                    java.sql.Timestamp tsStart = rsEvac.getTimestamp("response_start");
                    rsEvac.getTimestamp("response_end"); // ignore stored response_end for open intervals
                    iv.start = tsStart != null ? tsStart.toLocalDateTime() : monthStartDt;
                    iv.end = null; // prefer a linked 'released' response; if none, we'll use now

                    // prefer a linked 'released' response's response_start as the end time
                    PreparedStatement psRel = conn.prepareStatement(
                        "SELECT r2.response_start FROM response r2 JOIN response_resident rr2 ON r2.response_id = rr2.response_id "
                        + "WHERE rr2.resident_id = ? AND rr2.role = 'released' AND r2.shelter_id = ? AND r2.response_start >= ? "
                        + "ORDER BY r2.response_start ASC LIMIT 1");
                    psRel.setInt(1, iv.residentId);
                    psRel.setInt(2, sid);
                    psRel.setTimestamp(3, java.sql.Timestamp.valueOf(iv.start));
                    ResultSet rsRel = psRel.executeQuery();
                    if (rsRel.next()) {
                        java.sql.Timestamp relTs = rsRel.getTimestamp("response_start");
                        if (relTs != null) iv.end = relTs.toLocalDateTime();
                    }
                    rsRel.close();
                    psRel.close();

                    // fallback: if still null, use the current datetime (count up to today)
                    if (iv.end == null) {
                        iv.end = java.time.LocalDateTime.now();
                    }

                    intervals.add(iv);
                }
                rsEvac.close();
                psEvac.close();

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

    /* Equipment report helpers */
    public List<String> getEquipmentList() {
        List<String> list = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            PreparedStatement pstmt = conn.prepareStatement("SELECT equipment_id, equipment_name FROM equipment ORDER BY equipment_name ASC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("equipment_id") + " - " + rs.getString("equipment_name"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Integer> getAvailableYearsForEquipment() {
        List<Integer> years = new ArrayList<>();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");
            PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT YEAR(date_lent) AS y FROM response_equipment WHERE date_lent IS NOT NULL ORDER BY y DESC");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                years.add(rs.getInt("y"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return years;
    }

    public List<String[]> getEquipmentUtilizationReport(int year, int month, int equipmentId) {
        List<String[]> reportData = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // time bounds
            java.time.YearMonth ym = java.time.YearMonth.of(year, month);
            java.time.LocalDate start = ym.atDay(1);
            java.time.LocalDate end = ym.atEndOfMonth();
            String sql = "SELECT e.equipment_id, e.equipment_name AS equipment, COUNT(re.response_id) AS total_uses, SUM(re.quantity_used) " +
            "AS total_qty_lent, AVG (re.quantity_used) AS avg_qty_per_use, e.quantity_per_name AS current_stock, e.availability AS availability "+ 
            "FROM equipment e LEFT JOIN response_equipment re ON re.equipment_id = e.equipment_id AND re.date_lent BETWEEN ? AND ? " +
            "WHERE (? = 0 OR e.equipment_id = ?) GROUP BY e.equipment_id, e.equipment_name, e.quantity_per_name, e.availability ORDER BY total_uses DESC, e.equipment_name ASC";
                    
            PreparedStatement ps = conn.prepareStatement(sql);
            //month start, month end, all equipment, equipment id
            ps.setDate(1, java.sql.Date.valueOf(start));
            ps.setDate(2, java.sql.Date.valueOf(end));
            ps.setInt(3, equipmentId);
            ps.setInt(4, equipmentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] row = new String[] {
                    rs.getString("equipment"),
                    String.valueOf(rs.getInt("total_uses")),
                    String.valueOf(rs.getInt("total_qty_lent")),
                    String.format("%.2f", rs.getDouble("avg_qty_per_use")),
                    String.valueOf(rs.getInt("current_stock")),
                    rs.getString("availability")};

                    reportData.add(row);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reportData;
    }
}