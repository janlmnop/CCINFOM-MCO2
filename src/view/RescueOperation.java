package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RescueOperation extends JPanel {
    /* UI components */
    private JLabel titleLabel = new JLabel();
    private JButton backButton = new JButton();
    private JButton updateButton = new JButton();

    private JLabel typeLabel = new JLabel();
    private JLabel startLabel = new JLabel();
    private JLabel endLabel = new JLabel();
    private JLabel employeeLabel = new JLabel();
    private JLabel residentLabel = new JLabel();

    private JTextField typeField;
    private JTextField startField;
    private JTextField endField;
    private JTextField employeeField;
    private JTextField residentField;



    public RescueOperation() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Rescue Operation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        /* buttons panel */
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 200, 10, 200);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;

        backButton = new JButton("Back");
        updateButton = new JButton("Update Records");

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(updateButton, gbcButtons);
        gbcButtons.gridx = 2;

        /* specifications panel */
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        typeField = new JTextField();
        typeLabel.setText("Disaster Type: ");
        typeField.setColumns(20);                

        startField = new JTextField();
        startLabel.setText("Start Date & Time (YYYY-MM-DD HH:MI:SS):");
        startField.setColumns(20);

        endField = new JTextField();
        endLabel.setText("Start Date & Time (YYYY-MM-DD HH:MI:SS):");
        endField.setColumns(20);
        
        employeeField = new JTextField();
        employeeLabel.setText("Employee(s) Assigned:");                          
        employeeField.setColumns(20);

        residentField = new JTextField();
        residentLabel.setText("Rescued Resident(s):");
        residentField.setColumns(20);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 0;
        fieldsPanel.add(typeLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(typeField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 1;
        fieldsPanel.add(startLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(startField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 2;
        fieldsPanel.add(endLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(endField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 3;
        fieldsPanel.add(employeeLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(employeeField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 4;
        fieldsPanel.add(residentLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(residentField, gbcSpecs);

        
        /* combine all panels */
        add(fieldsPanel, BorderLayout.CENTER);   
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }



    public JButton getBackButton() {
        return backButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public String getDisasterType() {
        return typeField.getText();
    }

    public String getStartDateTime() {
        return startField.getText();
    }

    public String getEndDateTime() {
        return endField.getText();
    }

    public String getEmployeeAssigned() {
        return employeeField.getText();
    }

    public String getRescuedResident() {
        return residentField.getText();
    }

    public void setActionListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }
}