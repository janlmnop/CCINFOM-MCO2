/**
 *  This contains the Borrow Equipment panel, where users can borrow available equipment by
 *  specifying its name, the quanitity, the borrower's name, and the current date.
 * 
 *  Notes:
 *  - not connected to a database yet
*/

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BorrowEquipment extends JPanel {            
    /* UI COMPONENTS */
    private JLabel titleLabel = new JLabel();
    private JTable equipmentTable;
    private JButton backButton = new JButton();
    private JButton borrowButton = new JButton();

    private JComboBox<String> itemField;
    private JComboBox<String> borrowerField;
    private JTextField qtyField;
    private JTextField dateField;


    public BorrowEquipment() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("Borrow Equipment");
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
        borrowButton = new JButton("Borrow");

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(borrowButton, gbcButtons);
        gbcButtons.gridx = 2;

        /* table panel */
        // JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // equipmentTable = new JTable(sample, colNames);
        // JScrollPane scrollPane = new JScrollPane(equipmentTable);
        // scrollPane.setPreferredSize(new Dimension(600,250));
        // tablePanel.add(scrollPane);

        /* equipment specifications panel */
        JPanel equipmentSpecsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        JLabel itemLabel = new JLabel("Item:");
        itemField = new JComboBox<String>();                              
        itemField.setPreferredSize(new Dimension(255, 25));

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyField = new JTextField(20);                           // NOTE : add input validation

        JLabel borrowerLabel = new JLabel("Borrower:");
        borrowerField = new JComboBox<String>();                        
        borrowerField.setPreferredSize(new Dimension(255, 25));

        JLabel dateLabel = new JLabel("Date (dd/MM/yyy):");
        dateField = new JTextField(20);

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
        //centerPanel.add(tablePanel);
        centerPanel.add(equipmentSpecsPanel);
        add(centerPanel, BorderLayout.CENTER);   
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /* gets the back button */
    public JButton getBackButton() {
        return backButton;
    }

    /* gets the return button */
    public JButton getBorrowButton() {
        return borrowButton;
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
        borrowButton.addActionListener(listener);
    }
}