/**
 *  This contains the Return Equipment panel, where users can the equipment they've used
 *  by specifying its name, the quanitity, the borrower's name, and the current date.
*/

package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import model.Equipment;


public class ReturnEquipment extends JPanel {
    /* attributes */
    Equipment equipment = new Equipment();

    /* UI COMPONENTS */
    private JLabel titleLabel = new JLabel();
    private JButton backButton = new JButton();
    private JButton returnButton = new JButton();
    private JTable equipmentTable;

    JLabel itemLabel = new JLabel();
    JLabel qtyLabel = new JLabel();
    JLabel borrowerLabel = new JLabel();
    JLabel dateLabel = new JLabel();

    JComboBox<String> itemField;  
    public JTextField qtyField;                     
    JComboBox<String> borrowerField;
    public JTextField dateField;


    public ReturnEquipment() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Return Equipment");
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
        returnButton = new JButton("Return");

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(returnButton, gbcButtons);
        gbcButtons.gridx = 2;

        /* table panel */
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Equipment Name", "Quantity", "Availability"};
        
        equipmentTable = new JTable(equipment.getEquipmentData(), columnNames);
        equipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        equipmentTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        equipmentTable.getColumnModel().getColumn(1).setPreferredWidth(50); 
        equipmentTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        /* equipment specifications panel */
        JPanel equipmentSpecsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        itemLabel.setText("Item:");
        itemField = new JComboBox<>();
        itemField.setPreferredSize(new Dimension(255, 25));

        qtyLabel.setText("Quantity:");
        qtyField = new JTextField();
        qtyField.setColumns(20);                                        // NOTE : add input validation

        borrowerLabel.setText("Borrower:");
        borrowerField = new JComboBox<>();
        borrowerField.setPreferredSize(new Dimension(255, 25));

        dateLabel.setText("Date (yyyy-MM-dd):");
        dateField = new JTextField();
        dateField.setColumns(20);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 0;
        equipmentSpecsPanel.add(itemLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        equipmentSpecsPanel.add(itemField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 1;
        equipmentSpecsPanel.add(qtyLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        equipmentSpecsPanel.add(qtyField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 2;
        equipmentSpecsPanel.add(borrowerLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        equipmentSpecsPanel.add(borrowerField, gbcSpecs);

        gbcSpecs.gridx = 0; 
        gbcSpecs.gridy = 3;
        equipmentSpecsPanel.add(dateLabel, gbcSpecs);
        gbcSpecs.gridx = 1;
        equipmentSpecsPanel.add(dateField, gbcSpecs);



        /* combine all panels */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(tablePanel);
        centerPanel.add(equipmentSpecsPanel);  
        add(centerPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /* gets the back button */
    public JButton getBackButton() {
        return backButton;
    }

    /* gets the borrow button */
    public JButton getReturnButton() {
        return returnButton;
    }

    public JComboBox<String> getItemComboBox() {
        return itemField;
    }

    public JComboBox<String> getBorrowerComboBox() {
        return borrowerField;
    }

    public String getItem() {
        return itemField.getSelectedItem().toString();
    }

    public String getQuantity() {
        return qtyField.getText();
    }

    public String getBorrower() {
        return borrowerField.getSelectedItem().toString();
    }

    public String getDate() {
        return dateField.getText();
    }

    public void setActionListener(ActionListener listener) {
        returnButton.addActionListener(listener);
    }
}