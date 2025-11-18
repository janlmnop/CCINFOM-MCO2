/**
 *  This contains the Response Report panel, where users can view the total and average
 *  residents rescued and evacuated per disaster cases for a given year and month.
*/

package view;

import javax.swing.*;
import java.awt.*;

public class ResponseReport extends JPanel {
    /* attributes */
    private String[] months = {"January", "February", "March", "April",
                                "May", "June", "July", "August",
                                "September", "October", "November", "December"};
    private String[] colNames = {"Disaster ID", "Disaster Type", "Date Occurred", "Location", "Residents Rescued"};
    private String[][] data;

    /* UI components */
    private JLabel titleLabel = new JLabel();
    private JButton backTransButton = new JButton();
    private JButton backRepsButton = new JButton();
    private JButton filterButton = new JButton();

    private JLabel filterByLabel = new JLabel();
    private JLabel monthLabel = new JLabel();
    private JLabel yearLabel = new JLabel();
    private JLabel disasterLabel = new JLabel();

    private JComboBox<String> monthField = new JComboBox<String>(months);
    private JComboBox<String> yearField = new JComboBox<String>();
    private JComboBox<String> disasterField = new JComboBox<String>();

    private JTable responseReportTable = new JTable();
    private JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));



    public ResponseReport() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Response Report");
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

        disasterLabel.setText("Disaster:");
        disasterField.setPreferredSize(new Dimension(150, 25));

        filterButton.setText("Apply Filter");
        filterButton.setPreferredSize(new Dimension(120, 25));

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
        filterByPanel.add(disasterLabel, gbcSpecs);
        gbcSpecs.gridx = 5;
        filterByPanel.add(disasterField, gbcSpecs);

        gbcSpecs.gridx = 6;
        gbcSpecs.gridy = 1;
        filterByPanel.add(filterButton, gbcSpecs);

        /* stats panel -- will see if I'll add this pa */
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Summary Statistics"));

        /* table panel */
        responseReportTable = new JTable(data, colNames);
        JScrollPane scrollPane = new JScrollPane(responseReportTable);
        scrollPane.setPreferredSize(new Dimension(600, 100));
        tablePanel.add(scrollPane);

        /* combine center panels */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(filterByPanel);
        // centerPanel.add(statsPanel);
        centerPanel.add(tablePanel);



        /* combine all panels */
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    /* gets the back button */
    public JButton getBackTransButton() {
        return backTransButton;
    }

    /* gets the back to reports button */
    public JButton getBackRepsButton() {
        return backRepsButton;
    }

    /* gets the filter button */
    public JButton getFilterButton() {
        return filterButton;
    }

    /* fills table data */
    public void setTableData(String[][] data, String[] columnNames) {
        this.data = data;
        this.colNames = columnNames;
        responseReportTable = new JTable(data, columnNames);
        
        // refresh the table in the UI based on filters
        JScrollPane scrollPane = new JScrollPane(responseReportTable);
        scrollPane.setPreferredSize(new Dimension(600, 350));
        
        // removes old table and adds the new one
        tablePanel.removeAll();
        tablePanel.add(scrollPane);
        tablePanel.revalidate();
        tablePanel.repaint();
    }
    
    /* gets the month in the dropdown*/
    public int getSelectedMonth() {
        return monthField.getSelectedIndex() + 1;
    }
    
    /* gets the year in the dropdown */
    public int getSelectedYear() {
        String selectedYear = yearField.getSelectedItem().toString();
        
        try {
            return Integer.parseInt(selectedYear);
        } catch (NumberFormatException e) {
            return 2025;
        }
    }
    
    /* get selected disaster type */
    public String getSelectedDisasterType() {
        return disasterField.getSelectedItem().toString();
    }
    
    /* populate year combobox */
    public void setYearOptions(String[] years) {
        yearField.removeAllItems();
        for (String year : years)
            yearField.addItem(year);

        // set default selection to current year if available
        if (years.length > 0)
            yearField.setSelectedItem(years[0]);
    }
    
    /* populate disaster type combobox */
    public void setDisasterOptions(String[] disasters) {
        disasterField.removeAllItems();
        for (String disaster : disasters)
            disasterField.addItem(disaster);

        // set default selection to first item
        if (disasters.length > 0)
            disasterField.setSelectedItem(disasters[0]);
    }
}