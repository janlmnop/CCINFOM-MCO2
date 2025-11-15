/**
 *  This contains the Transactions Menu, where users can select from five options.
 * 
 *  Note:
 *  - don't forget to change transactions if changed in docs.
*/

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TransactionsMenu extends JPanel {
    /* UI Components */
    private JButton btnT1 = new JButton(T1);
    private JButton btnT2 = new JButton(T2);
    private JButton btnT3 = new JButton(T3);
    private JButton btnT4 = new JButton(T4);
    private JButton btnT5 = new JButton(T5);
    private JButton btnReports = new JButton(Reports);
    private JLabel label = new JLabel();

    public static final String T1 = "Rescue Operation";
    public static final String T2 = "Assign Resident to Shelter";
    public static final String T3 = "Release Resident from Shelter";
    public static final String T4 = "Borrow Equipment";
    public static final String T5 = "Return Equipment";
    public static final String Reports = "View Reports";


    /* CONSTRUCTOR */
    public TransactionsMenu() {
        /* heading */
        label = new JLabel();
        label.setForeground(Color.black);
        label.setText("Transactions Menu");
        label.setFont(new Font("Arial", Font.BOLD, 24));

        /* buttons panel */
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(true);

        JLabel background = new JLabel();
        background.setLayout(new GridBagLayout());

        this.setLayout(new BorderLayout());
        this.add(background, BorderLayout.CENTER);

        setupButton(btnT1);
        setupButton(btnT2);
        setupButton(btnT3);
        setupButton(btnT4);                                                  
        setupButton(btnT5);
        setupButton(btnReports);

        /* position heading in gridbag */
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        gbcLabel.anchor = GridBagConstraints.CENTER;
        gbcLabel.insets = new Insets(-470, 0, 0, 0);        // this sets the precise location
        background.add(label, gbcLabel);

        /* positions buttons in gridbag */
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(btnT1, gbcButtons);
        gbcButtons.gridy = 1;
        buttonPanel.add(btnT2, gbcButtons);
        gbcButtons.gridy = 2;
        buttonPanel.add(btnT3, gbcButtons);
        gbcButtons.gridy = 3;
        buttonPanel.add(btnT4, gbcButtons);
        gbcButtons.gridy = 4;
        buttonPanel.add(btnT5, gbcButtons);
        gbcButtons.gridy = 5;
        buttonPanel.add(btnReports, gbcButtons);

        /* combine all panels */
        GridBagConstraints gbcBackground = new GridBagConstraints();
        gbcBackground.gridx = 0;
        gbcBackground.gridy = 0;
        gbcBackground.weightx = 1;
        gbcBackground.weighty = 1;
        gbcBackground.anchor = GridBagConstraints.CENTER; 
        gbcBackground.fill = GridBagConstraints.NONE;
        background.add(buttonPanel, gbcBackground);
    }


    /* button preferences */
    private void setupButton(JButton btn) {
        btn.setPreferredSize(new Dimension(300, 50));               // this sets the button length so that they are uniform
        btn.setFocusPainted(false);
    }

    /* gets button or transaction 1 */
    public JButton getBtnT1() {
        return btnT1;
    }

    /* gets button or transaction 2 */
    public JButton getBtnT2() {
        return btnT2;
    }

    /* gets button or transaction 3 */
    public JButton getBtnT3() {
        return btnT3;
    }

    /* gets button or transaction 4 */
    public JButton getBtnT4() {
        return btnT4;
    }

    /* gets button or transaction 5 */
    public JButton getBtnT5() {
        return btnT5;
    }

    /* gets view report button */
    public JButton getBtnReports() {
        return btnReports;
    }

    public void setActionListener(ActionListener listener) {
        btnT5.addActionListener(listener);
    }
}