/**
 *  This is contains the error message that pops up when transactions aren't unsuccessful.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class TranErrorMessage extends JPanel {
    /* UI components */
    private JLabel errorMessage;
    private JButton backButton = new JButton("Back to Transactions Menu");
    private JButton retryButton = new JButton("Retry Transaction");

    public TranErrorMessage() {
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

        buttonPanel.add(backButton, gbcButtons);
        gbcButtons.gridx = 1;
        buttonPanel.add(retryButton, gbcButtons);
        gbcButtons.gridx = 2;
        
        /* combine */
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public JButton getOtherBackButtons() {
        return backButton; 
    }

    public JButton getOtherRetryButtons() {
        return retryButton; 
    }

    public void setErrorMessage(String message) {
        errorMessage.setText(message);
    }

    public void setActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
        retryButton.addActionListener(listener);
    }
}