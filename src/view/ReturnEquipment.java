/**
 *  This contains the Return Equipment panel, where users can the equipment they've used
 *  by specifying its name, the quanitity, the borrower's name, and the current date.
 * 
 *  Notes:
 *  - should update db when Borrow button is pressed
 *  - extra: should return success message
*/

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ReturnEquipment extends JPanel {
    /* UI COMPONENTS */
    private JLabel titleLabel = new JLabel();
    private JButton backButton = new JButton();
    private JButton returnButton = new JButton();

    JLabel itemLabel = new JLabel();
    JLabel qtyLabel = new JLabel();
    JLabel borrowerLabel = new JLabel();
    JLabel dateLabel = new JLabel();

    JComboBox<String> itemField;  
    JTextField qtyField = new JTextField();                       // NOTE : add input validation
    JComboBox<String> borrowerField;
    JTextField dateField = new JTextField();


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

        /* equipment specifications panel */
        JPanel equipmentSpecsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSpecs = new GridBagConstraints();
        gbcSpecs.insets = new Insets(10, 10, 10, 10);
        gbcSpecs.anchor = GridBagConstraints.WEST;

        itemLabel.setText("Item:");
        itemField = new JComboBox<>();
        itemField.setPreferredSize(new Dimension(255, 25));

        qtyLabel.setText("Quantity:");
        qtyField.setColumns(20);                                        // NOTE : add input validation

        borrowerLabel.setText("Borrower:");
        borrowerField = new JComboBox<>();
        borrowerField.setPreferredSize(new Dimension(255, 25));

        dateLabel.setText("Date (yyyy-MM-dd):");
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
        add(equipmentSpecsPanel, BorderLayout.CENTER);   
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