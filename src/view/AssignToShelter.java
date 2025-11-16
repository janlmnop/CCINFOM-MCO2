/**
 *  This contains the Assign Resident to Shelter panel, where users (employees) can assign
 *  residents to shelters by specifying the resident's ID, the shelter's ID, the current date,
 *  the current time, and the employee assigned for accounting purposes.
 * 
 *  Notes:
 *  - connected to db
*/

package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import model.Employee;

public class AssignToShelter extends JPanel {
    /* attributes */
    private ArrayList<Employee> employees;

    /* UI components */
    private JLabel titleLabel = new JLabel("Assign Resident to Shelter");
    private JButton backButton = new JButton("Back");
    private JButton assignButton = new JButton("Assign");

    private JLabel residentIDLabel = new JLabel();
    private JLabel shelterIDLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JLabel timeLabel = new JLabel();
    private JLabel employeeAssignedLabel = new JLabel();

    private JTextField residentIDField;
    private JTextField shelterIDField;
    private JTextField dateField;
    private JTextField timeField;  
    private JComboBox<String> employeeAssignedField;

    public AssignToShelter() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(assignButton, gbcButtons);
        gbcButtons.gridx = 2;

        /* specifications panel */
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        residentIDLabel = new JLabel("Resident ID:");
        residentIDField = new JTextField(20);                               // aternative

        shelterIDLabel = new JLabel("Shelter ID:");
        shelterIDField = new JTextField(20);   
        
        dateLabel = new JLabel("Date (dd/MM/yyy):");
        dateField = new JTextField(20);

        timeLabel = new JLabel("Time (24:59):");
        timeField = new JTextField(20);
        
        employeeAssignedLabel = new JLabel("Employee Assigned:");
        employeeAssignedField = new JComboBox<>();                                    // NOTE : change this too; it should come from the db
        employeeAssignedField.setPreferredSize(new Dimension(255, 25));

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 0;
        fieldsPanel.add(residentIDLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(residentIDField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 1;
        fieldsPanel.add(shelterIDLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(shelterIDField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 2;
        fieldsPanel.add(dateLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(dateField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 3;
        fieldsPanel.add(timeLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(timeField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 4;
        fieldsPanel.add(employeeAssignedLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        fieldsPanel.add(employeeAssignedField, gbcSpecs);


        /* combine all panels */
        add(fieldsPanel);
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAssignButton() {
        return assignButton;
    }

    public JComboBox<String> getEmployeeComboBox() {
        return employeeAssignedField;
    }

    public String getResidentID() {
        return residentIDField.getText();
    }

    public String getShelterID() {
        return shelterIDField.getText();
    }

    public String getDate() {
        return dateField.getText();
    }

    public String getTime() {
        return timeField.getText();
    }

    public String getEmployeeAssigned() {
        if (employeeAssignedField.getSelectedItem() == null) {
            return "";
        }
        return employeeAssignedField.getSelectedItem().toString();
    }
}
