package controller;

import javax.swing.event.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.*;

import view.*;
import model.Employee;
import model.Resident;
import model.Shelter;
import model.Equipment;

public class Controller implements ActionListener, DocumentListener {
    private MainFrame mainFrame;
    private RescueOperation resOp;
    private AssignToShelter asShelter;
    private ReleaseFromShelter reShelter;
    private BorrowEquipment bEquip;
    private ReturnEquipment rEquip;

    private Employee employee;
    private Resident resident;
    private Shelter shelter;
    private Equipment equipment;

    public Controller() {

    }

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
            pstmt.setInt(1, thisEquipment.getEquipmentID());
            pstmt.setInt(1, Integer.parseInt(qty));
            pstmt.setString(2, item);

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