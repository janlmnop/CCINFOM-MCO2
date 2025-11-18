/**
 *  This is contains the error message that pops up when login is unsuccessful.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class LoginErrorMessage extends JPanel {
    /* UI components */
    private JLabel errorMessage;
    private JButton exitButton = new JButton("Exit");
    private JButton retryButton = new JButton("Retry");

    public LoginErrorMessage() {
        /* panel settings */
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        /* message panel */
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridBagLayout());
        errorMessage = new JLabel();
        errorMessage.setFont(new Font("Arial", Font.BOLD, 24));
        errorMessage.setForeground(Color.BLACK);
        messagePanel.add(errorMessage);

        /* buttons panel */
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 200, 10, 200);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;

        exitButton = new JButton("Exit");
        retryButton = new JButton("Retry");

        buttonPanel.add(exitButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(retryButton, gbcButtons);
        gbcButtons.gridx = 2;
        
        /* combine */
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getLoginExitButton() {
        return exitButton; 
    }

    public JButton getLoginRetryButton() {
        return retryButton; 
    }

    public void setErrorMessage(String message) {
        errorMessage.setText(message);
    }

    public void setActionListener(ActionListener listener) {
        exitButton.addActionListener(listener);
        retryButton.addActionListener(listener);
    }
}