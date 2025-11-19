/**
 *  This contains the Reports Menu, where users can select from four report types.
*/

package view;

import javax.swing.*;
import java.awt.*;

public class ViewReports extends JPanel {
    /* UI COMPONENTS */
    private final String RR = "Response Report";
    private final String SB = "Shelter Occupancy Report";

    private JLabel titleLabel = new JLabel();
    private JButton backButton = new JButton();
    private JButton responseButton = new JButton(RR);
    private JButton shelterButton = new JButton(SB);
    private JButton equipmentButton = new JButton("Equipment Utilization Report");
    private JButton employeeButton = new JButton("Employee Deployment Report");
    

    public ViewReports() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel.setText("View Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel);

        /* buttons panel */
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.WEST;

        backButton = new JButton("< Back to Transactions");

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;

        /* center panel */
        JPanel centerPanel = new JPanel(new FlowLayout());
        
        setupButton(responseButton);
        setupButton(shelterButton);
        setupButton(equipmentButton);
        setupButton(employeeButton);

        centerPanel.add(responseButton);
        centerPanel.add(shelterButton);
        centerPanel.add(equipmentButton);
        centerPanel.add(employeeButton);

        /* combine all panels */
        add(centerPanel, BorderLayout.CENTER);   
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

     /* button preferences */
    private void setupButton(JButton btn) {
        btn.setPreferredSize(new Dimension(300, 100)); 
        btn.setFocusPainted(false);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getResponseButton() {
        return responseButton;
    }

    public JButton getShelterButton() {
        return shelterButton;
    }

    public JButton getEquipmentButton() {
        return equipmentButton;
    }

    public JButton getEmployeeButton() {
        return employeeButton;
    }
}
