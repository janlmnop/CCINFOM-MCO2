/**
 *  This contains the Login Panel. Only valid users (employees), are able to log in.
*/

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LoginFrame extends JPanel {
    /* UI COMPONENTS */
    private JLabel titleLabel;        
    private JLabel idLabel;     
    private JLabel passwordLabel;    
    private JButton loginButton; 
    public JTextField idField;          
    public JPasswordField passwordField; 
    
    public static final String LOGIN = "login";


    /* CONSTRUCTOR */
    public LoginFrame() {
        /* frame settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        /* title panel */
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Employee Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        /* fields panel */
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        idLabel = new JLabel("Employee ID:");
        passwordLabel = new JLabel("Password:");
        idField = new JTextField(15);
        passwordField = new JPasswordField(15);

        gbc.gridx = 0; 
        gbc.gridy = 0;
        fieldsPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(idField, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 1;
        fieldsPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        fieldsPanel.add(passwordField, gbc);

        /* login button panel */
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        loginButton.setActionCommand(LOGIN);
        buttonPanel.add(loginButton);

        /* combine all panels */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        centerPanel.add(titlePanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(fieldsPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }


    /* GETTERS */
    public int getUserID() {
        int employeeID = Integer.parseInt(idField.getText());
        return employeeID;
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }
    

    /* LISTENER */
    public void setActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }    
}