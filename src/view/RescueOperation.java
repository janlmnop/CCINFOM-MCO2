package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import model.Employee;
import model.Resident;

public class RescueOperation extends JPanel {
    /* attributes */
    private ArrayList<Employee> employees;
    private ArrayList<Resident> residents;

    /* UI components */
    private JLabel titleLabel = new JLabel();
    private JButton backButton = new JButton();
    private JButton updateButton = new JButton();

    private JLabel typeLabel = new JLabel();
    private JLabel durationLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JLabel timeLabel = new JLabel();
    private JLabel employeesLabel = new JLabel();
    private JLabel residentsLabel = new JLabel();

    private JTextField typeField = new JTextField();
    private JTextField durationField = new JTextField();
    private JTextField dateField = new JTextField();
    private JTextField timeField = new JTextField();
    private JTextField employeesField = new JTextField();
    private JTextField residentsField = new JTextField();



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

        typeLabel.setText("Disaster Type: ");
        typeField.setColumns(20);                

        dateLabel.setText("Date (dd/MM/yyy):");
        dateField.setColumns(20);

        timeLabel.setText("Time (24:59):");
        timeField.setColumns(20);

        durationLabel.setText("Response Duration"); 
        durationField.setColumns(20);
        
        employeesLabel.setText("Employee(s) Assigned:");                          
        employeesField.setColumns(20);

        residentsLabel.setText("Rescued Resident(s):");
        residentsField.setColumns(20);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 0;
        fieldsPanel.add(typeLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(typeField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 1;
        fieldsPanel.add(dateLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(dateField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 2;
        fieldsPanel.add(timeLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(timeField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 3;
        fieldsPanel.add(durationLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(durationField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 4;
        fieldsPanel.add(employeesLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(employeesField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 5;
        fieldsPanel.add(residentsLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(residentsField, gbcSpecs);

        
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
}