/**
 *  This contains the Equipment Utilization Report panel, where users can view the total and average
 *  equipment utilization per year and month. 
 * 
 *  Notes:
 *  - not connected to a database yet
*/

package view;

import javax.swing.*;
import java.awt.*;

public class EquipmentReport extends JPanel {
    /* attributes */
    private String[] months = {"January", "February", "March", "April",
                                "May", "June", "July", "August",
                                "September", "October", "November", "December"};
    
    //values
    private String[] colNames = {"Equipment", "Total Uses", "Total Employees", "Total Residents", "Average Uses"};
    private String[][] data = {{"Portalets", "7", "5", "30", "4.5"},   
                               {"Tens", "10", "6", "50", "7.3"},
                               {"Ambulances", "5", "8", "35", "2.3"}};

    /* UI components */
    private JLabel titleLabel = new JLabel();
    private JButton backTransButton = new JButton();
    private JButton backRepsButton = new JButton();

    private JLabel filterByLabel = new JLabel();
    private JLabel monthLabel = new JLabel();
    private JLabel yearLabel = new JLabel();
    private JLabel equipmentLabel = new JLabel();
            
    private JComboBox<String> monthField = new JComboBox<String>(months);
    private JComboBox<String> yearField = new JComboBox<String>();
    private JComboBox<String> equipmentField = new JComboBox<String>();

    private JTable equipmentReportTable = new JTable();


    public EquipmentReport() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Equipment Utilization Report");
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

        backTransButton = new JButton("< Back to Transactions");
        backRepsButton = new JButton("Back to Reports >");

        buttonPanel.add(backTransButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(backRepsButton, gbcButtons);
        gbcButtons.gridx = 2;

        /* filter by panel */
        JPanel filterByPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        filterByLabel.setText("Filter By:");

        monthLabel.setText("Month:");
        monthField.setPreferredSize(new Dimension(150, 25));

        yearLabel.setText("Year:");
        yearField.setPreferredSize(new Dimension(100, 25)); 

        equipmentLabel.setText("Equipment:");
        equipmentField.setPreferredSize(new Dimension(150, 25));

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 0;
        filterByPanel.add(filterByLabel, gbcSpecs);
        gbcSpecs.gridx = 1;

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 1;
        filterByPanel.add(monthLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        filterByPanel.add(monthField, gbcSpecs);

        gbcSpecs.gridx = 2; 
        gbcSpecs.gridy = 1;
        filterByPanel.add(yearLabel, gbcSpecs);
        gbcSpecs.gridx = 3;
        filterByPanel.add(yearField, gbcSpecs);

        gbcSpecs.gridx = 4; 
        gbcSpecs.gridy = 1;
        filterByPanel.add(equipmentLabel, gbcSpecs);
        gbcSpecs.gridx = 5;
        filterByPanel.add(equipmentField, gbcSpecs);


        /* stats panel (unsure) */

        /* table panel */
        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        equipmentReportTable = new JTable(data, colNames);
        JScrollPane scrollPane = new JScrollPane(equipmentReportTable);
        scrollPane.setPreferredSize(new Dimension(400,350));
        tablePanel.add(scrollPane);

        /* combine center panels */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(filterByPanel);
        centerPanel.add(scrollPane);

        /* combine all panels */
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

    }

    /* gets the back button */
    public JButton getBackTransButton() {
        return backTransButton;
    }

    /* gets the borrow button */
    public JButton getBackRepsButton() {
        return backRepsButton;
    }



}