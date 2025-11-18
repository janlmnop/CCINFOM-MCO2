/**
 *  This contains the Shelter Occupancy Report panel, where users can view the total and average
 *  occupancy of shelters per year and month. 
 * 
 *  Notes:
 *  - not connected to a database yet
*/

package view;

import javax.swing.*;
import java.awt.*;

public class ShelterReport extends JPanel {
    /* attributes */
    private String[] months = {"January", "February", "March", "April",
                                "May", "June", "July", "August",
                                "September", "October", "November", "December"};
    
    //values (REPLACE)
    private String[] colNames = {"Shelter ID", "Shelter", "Capacity", "Total Residents Sheltered (Month)", "Aveage Occupancy"};
    private String[][] data = {{"1", "Basketball Court", "20", "25", "17.5"},
                               {"2", "City Hall", "10", "8", "9.5"}};

    /* UI components */
    private JLabel titleLabel = new JLabel();
    private JButton backTransButton = new JButton();
    private JButton backRepsButton = new JButton();
    private JButton filterButton = new JButton();

    private JLabel filterByLabel = new JLabel();
    private JLabel monthLabel = new JLabel();
    private JLabel yearLabel = new JLabel();
    private JLabel shelterLabel = new JLabel();

    private JComboBox<String> monthField = new JComboBox<String>(months);
    private JComboBox<String> yearField = new JComboBox<String>();
    private JComboBox<String> shelterField = new JComboBox<String>();

    private JTable shelterReportTable = new JTable();
    private JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    
    public ShelterReport() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Shelter Occupancy Report");
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

        shelterLabel.setText("Shelter:");
        shelterField.setPreferredSize(new Dimension(150, 25));

        filterButton.setText("Apply Filter");
        filterButton.setPreferredSize(new Dimension(120, 25));

        gbcSpecs.gridx = 4; 
        gbcSpecs.gridy = 1;
        filterByPanel.add(shelterLabel, gbcSpecs);
        gbcSpecs.gridx = 5;
        filterByPanel.add(shelterField, gbcSpecs);

        gbcSpecs.gridx = 6;
        gbcSpecs.gridy = 1;
        filterByPanel.add(filterButton, gbcSpecs);
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
        filterByPanel.add(shelterLabel, gbcSpecs);
        gbcSpecs.gridx = 5;
        filterByPanel.add(shelterField, gbcSpecs);


        /* stats panel (unsure, can delete) */

        /* table panel */
        shelterReportTable = new JTable(data, colNames);
        JScrollPane scrollPane = new JScrollPane(shelterReportTable);
        scrollPane.setPreferredSize(new Dimension(400,350));
        tablePanel.add(scrollPane);

        /* combine center panels */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(filterByPanel);
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

    /* gets the borrow button */
    public JButton getBackRepsButton() {
        return backRepsButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    /* fills table data */
    public void setTableData(String[][] data, String[] columnNames) {
        shelterReportTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(shelterReportTable);
        scrollPane.setPreferredSize(new Dimension(600, 350));

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
        if (yearField.getSelectedItem() == null) return -1;
        String selectedYear = yearField.getSelectedItem().toString();
        try {
            return Integer.parseInt(selectedYear);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getSelectedShelterID() {
        if (shelterField.getSelectedItem() == null) return -1;
        String sel = shelterField.getSelectedItem().toString();
        // expects format "<id> - <name>" or just name
        if (sel.contains(" - ")) {
            try {
                return Integer.parseInt(sel.split(" - ")[0]);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    /* populate year combobox */
    public void setYearOptions(String[] years) {
        yearField.removeAllItems();
        for (String year : years)
            yearField.addItem(year);

        if (years.length > 0)
            yearField.setSelectedItem(years[0]);
    }

    /* populate shelter combobox */
    public void setShelterOptions(String[] shelters) {
        shelterField.removeAllItems();
        for (String s : shelters)
            shelterField.addItem(s);

        if (shelters.length > 0)
            shelterField.setSelectedItem(shelters[0]);
    }

}
