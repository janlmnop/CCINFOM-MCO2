package controller;

import javax.swing.event.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import view.*;
import model.Employee;
import model.Resident;
import model.Response;
import model.Shelter;
import model.Equipment;

public class Controller implements ActionListener, DocumentListener {
    private MainFrame mainFrame;
    private RescueOperation resOp;
    private AssignToShelter asShelter;
    private ReleaseFromShelter reShelter;
    private BorrowEquipment bEquip;
    private ReturnEquipment rEquip;

    public Controller() {}

    public Controller(MainFrame mainFrame, RescueOperation resOp, AssignToShelter asShelter, ReleaseFromShelter reShelter, BorrowEquipment bEquip, ReturnEquipment rEquip) {
        this.mainFrame = mainFrame;
        this.resOp = resOp;
        this.asShelter = asShelter;
        this.reShelter = reShelter;
        this.bEquip = bEquip;
        this.rEquip = rEquip;

        resOp.getUpdateButton().addActionListener(this);
        asShelter.getAssignButton().addActionListener(this);
        reShelter.getReleaseButton().addActionListener(this);
        bEquip.getBorrowButton().addActionListener(this);
        rEquip.getReturnButton().addActionListener(this);
    }


    @Override
    public void actionPerformed (ActionEvent e) {
        // rescue operation
        if (e.getSource() == resOp.getUpdateButton()) {
            String disasterType = resOp.getDisasterType();
            String startDateTime = resOp.getStartDateTime();
            String endDateTime = resOp.getEndDateTime();
            String employeeAssigned = resOp.getEmployeeAssigned();
            String rescuedResident = resOp.getRescuedResident();

            rescueOperation(disasterType, startDateTime, endDateTime, employeeAssigned, rescuedResident);
        }

        // borrow equipment
        if (e.getSource() == bEquip.getBorrowButton()) {
            String item = bEquip.getItem();
            String quantity = bEquip.getQuantity();
            String borrower = bEquip.getBorrower();
            String date = bEquip.getDate();

            borrowEquipment(item, quantity, borrower, date);
        }

        // return equipment 
        if (e.getSource() == rEquip.getReturnButton()) {
            String item = rEquip.getItem();
            String quantity = rEquip.getQuantity();
            String borrower = rEquip.getBorrower();
            String date = rEquip.getDate();

            returnEquipment(item, quantity, borrower, date);
        }

        // assign resident to shelter
        if (e.getSource() == asShelter.getAssignButton()) {
            String residentID = asShelter.getResidentID();
            String shelterID = asShelter.getShelterID();
            String date = asShelter.getDate();
            String time = asShelter.getTime();
            String employeeAssigned = asShelter.getEmployeeAssigned();

            assignToShelter(residentID, shelterID, date, time, employeeAssigned);
        }
        
    }

    @Override
    public void insertUpdate (DocumentEvent e) {}

    @Override
    public void removeUpdate (DocumentEvent e) {}

    @Override
    public void changedUpdate (DocumentEvent e) {}


    /* SHOWS DROPDOWN VALUES FROM DB */
    public void loadEquipmentNames(BorrowEquipment viewBE) {
        Equipment equipmentModel = new Equipment();

        List<String> items = equipmentModel.getEquipmentList();

        viewBE.getItemComboBox().removeAllItems();

        for (String s : items)
            viewBE.getItemComboBox().addItem(s);
    }

    public void loadEquipmentNames(ReturnEquipment viewRE) {
        Equipment equipmentModel = new Equipment();

        List<String> items = equipmentModel.getEquipmentList();

        viewRE.getItemComboBox().removeAllItems();

        for (String s : items)
            viewRE.getItemComboBox().addItem(s);
    }

    public void loadEmployeeNames(AssignToShelter viewATS) {
        Employee employeeModel = new Employee();

        List<String> names = employeeModel.getEmployeeList();

        viewATS.getEmployeeComboBox().removeAllItems();

        for (String s : names)
            viewATS.getEmployeeComboBox().addItem(s);
    }

    public void loadEmployeeNames(ReleaseFromShelter viewRFS) {
        Employee employeeModel = new Employee();

        List<String> names = employeeModel.getEmployeeList();

        viewRFS.getEmployeeComboBox().removeAllItems();

        for (String s : names)
            viewRFS.getEmployeeComboBox().addItem(s);
    }

    public void loadResidentNames(BorrowEquipment viewBE) {
        Resident residentModel = new Resident();

        List<String> names = residentModel.getResidentList();

        viewBE.getBorrowerComboBox().removeAllItems();

        for (String s : names)
            viewBE.getBorrowerComboBox().addItem(s);
    }

    public void loadResidentNames(ReturnEquipment viewRE) {
        Resident residentModel = new Resident();

        List<String> names = residentModel.getResidentList();

        viewRE.getBorrowerComboBox().removeAllItems();

        for (String s : names)
            viewRE.getBorrowerComboBox().addItem(s);
    }


    /* DB MANIPULATION ON ACTUAL TRANSACTIONS */
    public int rescueOperation(String disasterType, String startDateTime, String endDateTime, String employeeAssigned, String rescuedResident) {
         try {
            Response thisResponse = new Response();

            // 1. connect to our database
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 2.1 to get the next response ID
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(response_id) + 1 AS responseID FROM response");
            ResultSet rst = pstmt.executeQuery();   // result set gets the value after excuting the query
            while (rst.next()) {
                thisResponse.setResponseID(rst.getInt("responseID"));
            }

            // 2.2 log response details
            pstmt = conn.prepareStatement("INSERT INTO response (response_id, response_type, response_start, response_end) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, thisResponse.getResponseID());
            pstmt.setString(2, "RS");   // always rescue
            pstmt.setString(3, startDateTime);
            pstmt.setString(4, endDateTime);

            pstmt.executeUpdate();

            // close assets
            pstmt.close();
            conn.close();

            System.out.println("Success!");
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int assignToShelter(String residentID, String shelterID, String date, String time, String employeeAssigned) {
        try {
            //parse ints
            int residentIdInt = Integer.parseInt(residentID);
            int shelterIdInt = Integer.parseInt(shelterID);

            // 1. connect to our database
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 2.a reading disaster records (chooses most recent disaster)
            int disasterId = -1;
            PreparedStatement pstmt = conn.prepareStatement("SELECT disaster_id FROM disaster ORDER BY date_occurred DESC LIMIT 1");
            ResultSet rst = pstmt.executeQuery();
        
            while (rst.next()) {
                disasterId = rst.getInt("disaster_id");
            }
            rst.close();
            pstmt.close();

            if (disasterId == -1) {
                System.out.println("No disaster records.");
                conn.close();
                return 0;
            }

            // 2.b reading resident record
            pstmt = conn.prepareStatement("SELECT resident_id FROM resident WHERE resident_id = ?");
            pstmt.setInt(1, residentIdInt);
            rst = pstmt.executeQuery();

            if (!rst.next()) {
                rst.close();
                pstmt.close();
                conn.close();
                System.out.println("Resident ID not found.");
                return 0;
            }
            rst.close();
            pstmt.close();

            // 2.c read shelter record to check capacity and status
            int capacity = 0;
            String shelterStatus = "";

            pstmt = conn.prepareStatement("SELECT capacity, status FROM shelter WHERE shelter_id = ?");
            pstmt.setInt(1, shelterIdInt);
            rst = pstmt.executeQuery();

            if (rst.next()) {
                capacity = rst.getInt("capacity");
                shelterStatus = rst.getString("status");
            }
            else {
                rst.close();
                pstmt.close();
                conn.close();
               System.out.println("Shelter ID not found.");
                return 0;
            }
            rst.close();
            pstmt.close();

            if (!shelterStatus.equals("Open")) {
                conn.close();
                System.out.println("Shelter is closed.");
                return 0;
            }

            // computing current occupants in shelter
            int occupants = 0;
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS occupants FROM response r JOIN response_resident rr ON r.response_id = rr.response_id WHERE r.shelter_id = ? AND rr.role = 'evacuated'");
            pstmt.setInt(1, shelterIdInt);
            rst = pstmt.executeQuery();
            
            if (rst.next()) {
                occupants = rst.getInt("occupants");
            }
            rst.close();
            pstmt.close();

            if (occupants >= capacity) {
                conn.close();
                System.out.println("Shelter is full.");
                return 0;
            }

            // 2.d reading employee records for employee_id
            int employeeId = -1;
            pstmt = conn.prepareStatement("SELECT employee_id FROM employee WHERE CONCAT(first_name, ' ', last_name) = ?");
            pstmt.setString(1, employeeAssigned);
            rst = pstmt.executeQuery();

            if (rst.next()) {
                employeeId = rst.getInt("employee_id");
            }
            rst.close();
            pstmt.close();

            if (employeeId == -1) {
                conn.close();
                System.out.println("Employee nt found.");
                return 0;
            }

            // 3. recording evacuation details in response
            Response thisResponse = new Response();

            //3.1 get next response ID
            pstmt = conn.prepareStatement("SELECT IFNULL(MAX(response_id), 0) + 1 AS responseID FROM response");
            rst = pstmt.executeQuery(); 

            while (rst.next()) {
                thisResponse.setResponseID(rst.getInt("responseID"));
            }
            rst.close();
            pstmt.close();

            // 3.2 building datetime string from date + time inputs
            String dateTime = date + " " + time;

            //3.3 inserting information into response (E/evacuation type)
            pstmt = conn.prepareStatement("INSERT INTO response (response_id, disaster_id, shelter_id, employee_id, response_type, response_start, response_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
        
            pstmt.setInt(1, thisResponse.getResponseID());
            pstmt.setInt(2, disasterId);
            pstmt.setInt(3, shelterIdInt);
            pstmt.setInt(4, employeeId);
            pstmt.setString(5, "E"); //for evacuation
            pstmt.setString(6, dateTime); //response start
            pstmt.setString(7, dateTime); //response end
        
            pstmt.executeUpdate();
            pstmt.close();

            //3.4 inserting evaucated resident into response_resident
            pstmt = conn.prepareStatement("INSERT INTO response_resident (response_id, resident_id, role) VALUES (?, ?, 'evacuated')");
            pstmt.setInt(1, thisResponse.getResponseID());
            pstmt.setInt(2, residentIdInt);
            pstmt.executeUpdate();
                pstmt.close();

            //4. closing connection
            conn.close();

            System.out.println("Success!");
            return 1;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int borrowEquipment(String item, String qty, String borrower, String date) {
        try {
            Equipment thisEquipment = new Equipment();

            // 1. connect to our database
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 2.1 to get the next equipment ID
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(equipment_id) + 1 AS equipmentID FROM equipment");
            ResultSet rst = pstmt.executeQuery();   // result set gets the value after excuting the query
            while (rst.next()) {
                thisEquipment.setEquipmentID(rst.getInt("equipmentID"));
            }

            // 2.2 set the equipment as unavailable & deduct the quantity
            pstmt = conn.prepareStatement("UPDATE equipment SET quantity_per_name=quantity_per_name-? WHERE equipment_name LIKE ?"); 
            pstmt.setInt(1, Integer.parseInt(qty));
            pstmt.setString(2, item);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("UPDATE equipment SET availability='Not Available' WHERE equipment_name LIKE ? && quantity_per_name=0"); 
            pstmt.setString(1, item);
            pstmt.executeUpdate();

            // close assets
            pstmt.close();
            conn.close();

            System.out.println("Success!");
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int returnEquipment(String item, String qty, String borrower, String date) {
        try {
            Equipment thisEquipment = new Equipment();

            // 1. connect to our database
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 2.1 to get the next equipment ID
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(equipment_id) + 1 AS equipmentID FROM equipment");
            ResultSet rst = pstmt.executeQuery();   // result set gets the value after excuting the query
            while (rst.next()) {
                thisEquipment.setEquipmentID(rst.getInt("equipmentID"));
            }

            // 2.2 add to quantity + set available if qty is above 0
            pstmt = conn.prepareStatement("UPDATE equipment SET quantity_per_name=quantity_per_name+? WHERE equipment_name LIKE ?");
            //pstmt.setInt(1, thisEquipment.getEquipmentID());
            pstmt.setInt(1, Integer.parseInt(qty));
            pstmt.setString(2, item);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("UPDATE equipment SET availability='Available' WHERE equipment_name LIKE ? && quantity_per_name>0"); 
            pstmt.setString(1, item);
            pstmt.executeUpdate();      // pag query, may result na bumabalik, pag update wala

            // close assets
            pstmt.close();
            conn.close();

            System.out.println("Success!");
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
} 
