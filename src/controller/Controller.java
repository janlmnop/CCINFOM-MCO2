package controller;

import javax.swing.event.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import view.*;
import model.*;

public class Controller implements ActionListener, DocumentListener {
    private MainFrame mainFrame;
    private LoginFrame login;
    private RescueOperation resOp;
    private AssignToShelter asShelter;
    private ReleaseFromShelter reShelter;
    private BorrowEquipment bEquip;
    private ReturnEquipment rEquip;
    private ResponseReport rRep;
    private LoginErrorMessage logEM;
    private TranErrorMessage tranEM;

    private String previousScreen;

    public Controller() {}

    public Controller(MainFrame mainFrame, LoginFrame login, RescueOperation resOp, AssignToShelter asShelter,
                     ReleaseFromShelter reShelter, BorrowEquipment bEquip, ReturnEquipment rEquip, ResponseReport rRep,
                     LoginErrorMessage logEM, TranErrorMessage tranEM) {
        this.mainFrame = mainFrame;
        this.login = login;
        this.resOp = resOp;
        this.asShelter = asShelter;
        this.reShelter = reShelter;
        this.bEquip = bEquip;
        this.rEquip = rEquip;
        this.rRep = rRep;
        this.logEM = logEM;
        this.tranEM = tranEM;

        // add transaction action listeners
        login.getLoginButton().addActionListener(this);
        resOp.getUpdateButton().addActionListener(this);
        asShelter.getAssignButton().addActionListener(this);
        reShelter.getReleaseButton().addActionListener(this);
        bEquip.getBorrowButton().addActionListener(this);
        rEquip.getReturnButton().addActionListener(this);

        // back button action listeners
        logEM.getLoginExitButton().addActionListener(this);
        tranEM.getOtherBackButtons().addActionListener(this);

        // retry button action listeners
        logEM.getLoginRetryButton().addActionListener(this);
        tranEM.getOtherRetryButtons().addActionListener(this);

        // load initial data for response report
        loadReportFilterOptions();
        refreshReportTable();

    }

    @Override
    public void actionPerformed (ActionEvent e) {
        // login screen
        if (e.getSource() == login.getLoginButton()) {
            int employeeID = login.getUserID();
            String password = login.getPassword();
            isValidUser(employeeID, password);
        }

        // rescue operation
        if (e.getSource() == resOp.getUpdateButton()) {
            String disasterType = resOp.getDisasterType();
            String startDateTime = resOp.getStartDateTime();
            String endDateTime = resOp.getEndDateTime();
            String employeeAssigned = resOp.getEmployeeAssigned();
            String rescuedResident = resOp.getRescuedResident();
            String location = resOp.getLoc();
            int casualties = resOp.getCasualties();
            int damages = resOp.getDamages();

            rescueOperation(disasterType, startDateTime, endDateTime, employeeAssigned, rescuedResident, location, casualties, damages);
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

        // release resident from shelter
        if (e.getSource() == reShelter.getReleaseButton()) {
            String residentID = reShelter.getResidentID();
            String shelterID = reShelter.getShelterID();
            String date = reShelter.getDate();
            String time = reShelter.getTime();
            String employeeAssigned = reShelter.getEmployeeAssigned();

            releaseFromShelter(residentID, shelterID, date, time, employeeAssigned);
        }
        
        // refresh response report table contents
        if (e.getSource() == rRep.getFilterButton()) {
            refreshReportTable();
        }

        /* ERROR BACK BUTTONS */
        if (e.getSource() == logEM.getLoginExitButton()) {
            System.exit(0);
        }
        if (e.getSource() == tranEM.getOtherBackButtons()) {
            mainFrame.showTransactionsMenu();
        } 

        /* ERROR RETRY BUTTONS */
        if (e.getSource() == logEM.getLoginRetryButton()) {
            mainFrame.showLoginPane();
        }
        if (e.getSource() == tranEM.getOtherRetryButtons()) {
            if ("rescueOperation".equals(previousScreen)) {
                mainFrame.showRescueOperationPane();
            } else if ("assignToShelter".equals(previousScreen)) {
                mainFrame.showAssignToShelterPane();
            } else if ("releaseFromShelter".equals(previousScreen)) {
                mainFrame.showReleaseFromShelterPane();
            } else if ("borrowEquipment".equals(previousScreen)) {
                mainFrame.showBorrowEquipmentPane();
            } else if ("returnEquipment".equals(previousScreen)) {
                mainFrame.showReturnEquipmentPane();
            } else {
                mainFrame.showTransactionsMenu(); 
            }
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

    public void loadEmployeeNames(RescueOperation viewRO) {
        Employee employeeModel = new Employee();
        List<String> names = employeeModel.getEmployeeList();
        viewRO.getEmployeeComboBox().removeAllItems();
        for (String s : names)
            viewRO.getEmployeeComboBox().addItem(s);
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

    public void loadResidentNames(RescueOperation viewRO) {
        Resident residentModel = new Resident();
        List<String> names = residentModel.getResidentList();
        viewRO.getResidentComboBox().removeAllItems();
        for (String s : names)
            viewRO.getResidentComboBox().addItem(s);
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

    public void loadReportFilterOptions() {
        Response thisResponse = new Response();

        // load years dropdown
        List<Integer> years = thisResponse.getAvailableYears();
        String[] yearStrings = new String[years.size()];
        for (int i = 0; i < years.size(); i++) {
            yearStrings[i] = String.valueOf(years.get(i));
        }
        rRep.setYearOptions(yearStrings);
        
        // load disaster types dropdown
        List<String> disasters = thisResponse.getDisasterTypes();
        rRep.setDisasterOptions(disasters.toArray(new String[0]));
    }



    /* DB MANIPULATION ON ACTUAL TRANSACTIONS */
    public int isValidUser(int inputId, String inputPassword) {
        Employee thisEmployee = new Employee();
        if(thisEmployee.logUserIn(inputId, inputPassword)) {
            login.getLoginButton().addActionListener(e -> mainFrame.showTransactionsMenu());
            return 1;
        } else {
            mainFrame.showLoginErrorMessagePane("Invalid Employee ID or Password. Please try again.");
            login.passwordField.setText("");
            login.idField.setText("");
            return 0;
        }
    }

    public int rescueOperation(String disasterType, String startDateTime, String endDateTime, String employeeAssigned, String rescuedResident, String location, int casualties, int damages) {
        try {
            Disaster thisDisaster = new Disaster();
            Response thisResponse = new Response();
            Shelter thisShelter = new Shelter();
            Employee thisEmployee = new Employee();

            // 1. connect to database
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 2.1 Create disaster record first
            PreparedStatement pstmt;
            ResultSet rst;
            int disasterId;
            pstmt = conn.prepareStatement("SELECT MAX(disaster_id) + 1 AS disasterID FROM disaster");
            rst = pstmt.executeQuery();
            while(rst.next()) {
                disasterId = rst.getInt("disasterID");
                thisDisaster.setDisasterID(disasterId);
            }
            pstmt.close();

            // insert disaster record
            pstmt = conn.prepareStatement("INSERT INTO disaster (disaster_id, disaster_type, date_occurred, location, casualties, damages) VALUES (?, ?, DATE(?), ?, ?, ?)");
            pstmt.setInt(1, thisDisaster.getDisasterID());
            pstmt.setString(2, disasterType);
            pstmt.setString(3, startDateTime);
            pstmt.setString(4, location);
            pstmt.setInt(5, casualties);
            pstmt.setInt(6, damages);
            pstmt.executeUpdate();
            pstmt.close();

            // 2.2 get the next response ID
            int responseId;
            pstmt = conn.prepareStatement("SELECT MAX(response_id) + 1 AS responseID FROM response");
            rst = pstmt.executeQuery();
            while (rst.next()) {
                responseId = rst.getInt("responseID");
                thisResponse.setResponseID(responseId);
            }
            pstmt.close();

            // 2.3 get employee ID from name
            int employeeId;
            pstmt = conn.prepareStatement("SELECT employee_id FROM employee WHERE CONCAT(first_name, ' ', last_name) = ?");
            pstmt.setString(1, employeeAssigned);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                employeeId = rst.getInt("employee_id");
                thisEmployee.setEmployeeID(employeeId);
            }
            pstmt.close();

            // 2.3.5 check available shelters
            int shelterId;
            pstmt = conn.prepareStatement("SELECT shelter_id FROM shelter");
            rst = pstmt.executeQuery();
            
            if (rst.next()) {
                shelterId = rst.getInt("shelter_id");
                thisShelter.setShelterID(shelterId);
            } else {
                throw new SQLException("No shelters available in the database. Please add a shelter first.");
            }
            pstmt.close();

            // 2.4 insert response record with all required foreign keys
            pstmt = conn.prepareStatement("INSERT INTO response (response_id, disaster_id, shelter_id, employee_id, response_type, response_start, response_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, thisResponse.getResponseID());
            pstmt.setInt(2, thisDisaster.getDisasterID());
            pstmt.setInt(3, thisShelter.getShelterID());
            pstmt.setInt(4, thisEmployee.getEmployeeID());
            pstmt.setString(5, "RS"); 
            pstmt.setString(6, startDateTime);
            pstmt.setString(7, endDateTime);
            pstmt.executeUpdate();
            pstmt.close();

            System.out.println("Success!");
            rst.close();
            pstmt.close();
            conn.close();
            return 1;

        } catch (Exception ex) {
            previousScreen = "rescueOperation";
            System.out.println(ex.getMessage());
            mainFrame.showTranErrorMessagePane(ex.getMessage() + ". Try Again.");
            resOp.typeField.setText("");
            resOp.startField.setText("");
            resOp.endField.setText("");
            resOp.locationField.setText("");
            resOp.casualtiesField.setText("");
            resOp.damagesField.setText("");
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
                previousScreen = "assignToShelter";
                mainFrame.showTranErrorMessagePane("Resident ID not found");
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
                previousScreen = "assignToShelter";
                mainFrame.showTranErrorMessagePane("Shelter is closed.");
                return 0;
            }

            // ensuring resident has not evacuated already
            pstmt = conn.prepareStatement("SELECT 1 FROM response_resident rr WHERE rr.resident_id = ? AND rr.role = 'evacuated' LIMIT 1");
            pstmt.setInt(1, residentIdInt);
            rst = pstmt.executeQuery();

            if (rst.next()) {
                rst.close();
                pstmt.close();
                conn.close();
                System.out.println("Resident is already assigned.");
                return 0;
            }

            rst.close();
            pstmt.close();

            // computing current occupants in shelter
            int occupants = 0;
            pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT rr.resident_id) AS occupants FROM response r JOIN response_resident rr ON r.response_id = rr.response_id WHERE r.shelter_id = ? AND rr.role = 'evacuated'");
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
                System.out.println("Employee not found.");
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
        catch (Exception ex) {
            System.out.println(ex.getMessage());
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

            pstmt = conn.prepareStatement("UPDATE equipment SET availability='Not Available' WHERE equipment_name LIKE ? && quantity_per_name<0"); 
            pstmt.setString(1, item);
            pstmt.executeUpdate();

            // close assets
            pstmt.close();
            conn.close();

            System.out.println("Success!");
            return 1;

        } catch (Exception ex) {
            previousScreen = "borrowEquipment";
            System.out.println(ex.getMessage());
            mainFrame.showTranErrorMessagePane(ex.getMessage() + ". Try Again.");
            bEquip.qtyField.setText("");
            bEquip.dateField.setText("");
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
            pstmt.setInt(1, Integer.parseInt(qty));
            pstmt.setString(2, item);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("UPDATE equipment SET availability='Available' WHERE equipment_name LIKE ? && quantity_per_name>0"); 
            pstmt.setString(1, item);
            pstmt.executeUpdate();

            // close assets
            pstmt.close();
            conn.close();

            System.out.println("Success!");
            return 1;

        } catch (Exception ex) {
            previousScreen = "returnEquipment";
            System.out.println(ex.getMessage());
            mainFrame.showTranErrorMessagePane(ex.getMessage() + ". Try Again.");
            rEquip.qtyField.setText("");
            rEquip.dateField.setText("");
            return 0;
        }
    }


    public int releaseFromShelter(String residentID, String shelterID, String date, String time, String employeeAssigned) {
        try {
            int residentIdInt = Integer.parseInt(residentID);
            int shelterIdInt = Integer.parseInt(shelterID);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbapp", "root", "Caf3Latt3");

            // 1. get most recent disaster id
            int disasterId = -1;
            PreparedStatement pstmt = conn.prepareStatement("SELECT disaster_id FROM disaster ORDER BY date_occurred DESC LIMIT 1");
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
                disasterId = rst.getInt("disaster_id");
            }
            rst.close();
            pstmt.close();

            if (disasterId == -1) {
                conn.close();
                System.out.println("No disaster records.");
                return 0;
            }

            // 2. check resident exists
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

            // 3. check shelter exists
            pstmt = conn.prepareStatement("SELECT capacity, status FROM shelter WHERE shelter_id = ?");
            pstmt.setInt(1, shelterIdInt);
            rst = pstmt.executeQuery();
            if (!rst.next()) {
                rst.close();
                pstmt.close();
                conn.close();
                System.out.println("Shelter ID not found.");
                return 0;
            }
            rst.close();
            pstmt.close();

            // 4. get employee id
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
                System.out.println("Employee not found.");
                return 0;
            }

            // 5. insert a response record to log the release
            Response thisResponse = new Response();
            pstmt = conn.prepareStatement("SELECT IFNULL(MAX(response_id), 0) + 1 AS responseID FROM response");
            rst = pstmt.executeQuery();
            while (rst.next()) {
                thisResponse.setResponseID(rst.getInt("responseID"));
            }
            rst.close();
            pstmt.close();

            String dateTime = date + " " + time;
            pstmt = conn.prepareStatement("INSERT INTO response (response_id, disaster_id, shelter_id, employee_id, response_type, response_start, response_end) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, thisResponse.getResponseID());
            pstmt.setInt(2, disasterId);
            pstmt.setInt(3, shelterIdInt);
            pstmt.setInt(4, employeeId);
            pstmt.setString(5, "RL");
            pstmt.setString(6, dateTime);
            pstmt.setString(7, dateTime);
            pstmt.executeUpdate();
            pstmt.close();

            // 6. remove evacuated resident entry (free occupant)
            pstmt = conn.prepareStatement("DELETE rr FROM response_resident rr JOIN response r ON rr.response_id = r.response_id WHERE rr.resident_id = ? AND r.shelter_id = ? AND rr.role = 'evacuated'");
            pstmt.setInt(1, residentIdInt);
            pstmt.setInt(2, shelterIdInt);
            pstmt.executeUpdate();
            pstmt.close();

            conn.close();
            System.out.println("Success!");
            return 1;
        } catch (Exception ex) {
            previousScreen = "releaseFromShelter";
            System.out.println(ex.getMessage());
            mainFrame.showTranErrorMessagePane(ex.getMessage() + ". Try Again.");
            reShelter.getEmployeeComboBox().removeAllItems();
            return 0;
        }
    }

    /* Shelter report wiring */
    public void loadShelterReportOptions(view.ShelterReport viewSR) {
        Response thisResponse = new Response();
        // load years
        List<Integer> years = thisResponse.getAvailableYears();
        String[] yearStrings = new String[years.size()];
        for (int i = 0; i < years.size(); i++) {
            yearStrings[i] = String.valueOf(years.get(i));
        }
        viewSR.setYearOptions(yearStrings);

        // load shelter options
        List<String> shelters = thisResponse.getShelterList();
        viewSR.setShelterOptions(shelters.toArray(new String[0]));
    }

    public void refreshShelterReport(view.ShelterReport viewSR) {
        Response thisResponse = new Response();
        int year = viewSR.getSelectedYear();
        int month = viewSR.getSelectedMonth();
        int shelterId = viewSR.getSelectedShelterID();

        List<String[]> reportData = thisResponse.getShelterOccupancyReport(year, month, shelterId);
        String[][] dataArray = reportData.toArray(new String[0][]);
        String[] columnNames = {"Shelter ID", "Shelter", "Capacity", "Total Residents Sheltered (Month)", "Average Daily Occupancy"};
        viewSR.setTableData(dataArray, columnNames);
    }

    /* equipment report wiring */
    public void loadEquipmentReportOptions(view.EquipmentReport viewER) {
        Response thisResponse = new Response();

        //years
        List<Integer> years = thisResponse.getAvailableYearsForEquipment();
        String[] yearStrings = new String[years.size()];

        for (int i = 0; i < years.size(); i++) {
            yearStrings[i] = String.valueOf(years.get(i));
        }
        viewER.setYearOptions(yearStrings);

        //equipment list
        List<String> eq = thisResponse.getEquipmentList();
        viewER.setEquipmentOptions(eq.toArray(new String[0]));
    }

    public void refreshEquipmentReport(view.EquipmentReport viewER) {
        Response thisResponse = new Response();

        int year = viewER.getSelectedYear();
        int month = viewER.getSelectedMonth();
        int equipmentId = viewER.getSelectedEquipmentID();

        List<String[]> rows = thisResponse.getEquipmentUtilizationReport(year, month, equipmentId);

        String[] cols = {"Equipment", "Total Uses", "Total Qty Lent", "Avg Qty/Use", "Stock", "Availability"};
        String[][] data = rows.toArray(new String[0][]);
        viewER.setTableData(data, cols);
    }

    /* updates response report */
    public void refreshReportTable() {
        Response thisResponse = new Response();

        int year = rRep.getSelectedYear();
        int month = rRep.getSelectedMonth();
        
        List<String[]> reportData = thisResponse.getRescueReportByMonth(year, month);
        
        // convert to 2D array for table
        String[][] dataArray = reportData.toArray(new String[0][]);
        String[] columnNames = {"Disaster ID", "Disaster Type", "Date Occurred", "Location", "Residents Rescued"};
        rRep.setTableData(dataArray, columnNames);

        System.out.println("Average: " + thisResponse.getAverageRescuedPerDisaster(year, month));
    }
}