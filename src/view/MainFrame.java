/**
 *  This is where all other frames are called.
 * 
 *  Note:
 *  - think of it as the first card in the deck
 *  - add other frames here
 *  - fix this
 *  - sorry medj magulo haha
*/

package view;

import java.awt.*;
import javax.swing.*;

import controller.*;


public class MainFrame extends JFrame {
    /* ATTRIBUTES */
    private CardLayout cardLayout;
    private JPanel mainPanel;


    /* OTHER FRAMES */
    private LoginFrame loginFrame = new LoginFrame();;
    private TransactionsMenu transactionsMenu = new TransactionsMenu();
    private RescueOperation rescueOperation = new RescueOperation();
    private AssignToShelter assignToShelter = new AssignToShelter();
    private ReleaseFromShelter releaseFromShelter = new ReleaseFromShelter();
    private BorrowEquipment borrowEquipment = new BorrowEquipment();
    private ReturnEquipment returnEquipment = new ReturnEquipment();
    private ViewReports viewReports = new ViewReports();
    private ResponseReport responseReport = new ResponseReport();
    private ShelterReport shelterReport = new ShelterReport();
    private EquipmentReport equipmentReport = new EquipmentReport();
    private EmployeeReport employeeReport = new EmployeeReport();
    private SuccessMessage successMessage = new SuccessMessage();
    private ErrorMessage errorMessage = new ErrorMessage();



    /* CONSTRUCTOR */
    public MainFrame() {
        super("Barangay Resident Evacuation and Shelter Management System");                // app name

        /* UI preferences */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);

        /* initialize layout */
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        /* add frames to card layout */
        mainPanel.add(loginFrame, "login");
        mainPanel.add(transactionsMenu, "transactions");
        mainPanel.add(rescueOperation, "rescue operation");
        mainPanel.add(assignToShelter, "assign to shelter");
        mainPanel.add(releaseFromShelter, "release from shelter");
        mainPanel.add(borrowEquipment, "borrow equipment");
        mainPanel.add(returnEquipment, "return equipment");
        mainPanel.add(viewReports, "view reports");
        mainPanel.add(responseReport, "response report");
        mainPanel.add(shelterReport, "shelter report");
        mainPanel.add(equipmentReport, "equipment report");
        mainPanel.add(employeeReport, "employee report");
        mainPanel.add(successMessage, "success message");
        mainPanel.add(errorMessage, "error message");

        /* redirections : this might be in controller instead of here */
        /* from transactions menu */
        loginFrame.getLoginButton().addActionListener(e -> showTransactionsMenu());
        transactionsMenu.getBtnT1().addActionListener(e -> showRescueOperationPane());
        transactionsMenu.getBtnT2().addActionListener(e -> showAssignToShelterPane());
        transactionsMenu.getBtnT3().addActionListener(e -> showReleaseFromShelterPane());
        transactionsMenu.getBtnT4().addActionListener(e -> showBorrowEquipmentPane());
        transactionsMenu.getBtnT5().addActionListener(e -> showReturnEquipmentPane());
        transactionsMenu.getBtnReports().addActionListener(e -> showViewReportsPane());

        /* for back buttons in transactions */
        rescueOperation.getBackButton().addActionListener(e -> showTransactionsMenu());
        assignToShelter.getBackButton().addActionListener(e -> showTransactionsMenu());
        releaseFromShelter.getBackButton().addActionListener(e -> showTransactionsMenu());
        borrowEquipment.getBackButton().addActionListener(e -> showTransactionsMenu());
        returnEquipment.getBackButton().addActionListener(e -> showTransactionsMenu());
        viewReports.getBackButton().addActionListener(e -> showTransactionsMenu());

        /* success message redirection */
        successMessage.getBackButton().addActionListener(e -> showTransactionsMenu());


        /* fail message redirections */
        // rescueOperation.getUpdateButton().addActionListener(e -> showErrorMessagePane());
        // assignToShelter.getAssignButton().addActionListener(e -> showErrorMessagePane());
        // releaseFromShelter.getReleaseButton().addActionListener(e -> showErrorMessagePane());
        // borrowEquipment.getBorrowButton().addActionListener(e -> showErrorMessagePane());
        // returnEquipment.getReturnButton().addActionListener(e -> showErrorMessagePane());

        /* error message redirections */
        // errorMessage.getBackButton().addActionListener(e -> showTransactionsMenu());
        // errorMessage.getRetryButton().addActionListener(e -> showReturnEquipmentPane());    // THIS IS WRONG; JUST A TEST

        /* from view reports menu */
        viewReports.getResponseButton().addActionListener(e -> showResponseReportsPane());
        viewReports.getShelterButton().addActionListener(e -> showShelterReportsPane());
        viewReports.getEquipmentButton().addActionListener(e -> showEquipmentReportsPane());
        viewReports.getEmployeeButton().addActionListener(e -> showEmployeeReportsPane());

        /* for back buttons (all reports) */
        responseReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        shelterReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        equipmentReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        employeeReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());

        /* reports to transactions menu */
        responseReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        shelterReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        equipmentReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        employeeReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        
        /* add mainPanel to Frame */
        add(mainPanel);
        setVisible(true);
    }


    /* leads to transactions menu frame */
    private void showTransactionsMenu() {
        cardLayout.show(mainPanel, "transactions");
    }

    /* leads to assign to shelter pane */
    private void showRescueOperationPane() {
        cardLayout.show(mainPanel, "rescue operation");
    }

    /* leads to assign to shelter pane */
    private void showAssignToShelterPane() {
        cardLayout.show(mainPanel, "assign to shelter");

        Controller controller = new Controller(this, rescueOperation, assignToShelter, releaseFromShelter, borrowEquipment, returnEquipment);
        controller.loadEmployeeNames(assignToShelter);
    }

    /* leads to release from shelter pane */
    private void showReleaseFromShelterPane() {
        cardLayout.show(mainPanel, "release from shelter");

        Controller controller = new Controller(this, rescueOperation, assignToShelter, releaseFromShelter, borrowEquipment, returnEquipment);
        controller.loadEmployeeNames(releaseFromShelter);
    }

    /* leads to borrow equipment pane */
    private void showBorrowEquipmentPane() {
        cardLayout.show(mainPanel, "borrow equipment");

        Controller controller = new Controller(this, rescueOperation, assignToShelter, releaseFromShelter, borrowEquipment, returnEquipment);
        controller.loadEquipmentNames(borrowEquipment);
        controller.loadResidentNames(borrowEquipment);
    }


    /* leads to return equipment pane */
    private void showReturnEquipmentPane() {
        cardLayout.show(mainPanel, "return equipment");

        Controller controller = new Controller(this, rescueOperation, assignToShelter, releaseFromShelter, borrowEquipment, returnEquipment);
        controller.loadEquipmentNames(returnEquipment);
        controller.loadResidentNames(returnEquipment);
    }

    /* leads to view reports pane */
    private void showViewReportsPane() {
        cardLayout.show(mainPanel, "view reports");
    }

    /* leads to response reports pane */
    private void showResponseReportsPane() {
        cardLayout.show(mainPanel, "response report");
    }
    
    /* leads to shelter occupancy reports pane */
    private void showShelterReportsPane() {
        cardLayout.show(mainPanel, "shelter report");
    }
    
    /*leads to equipment utilization reports pane */
    private void showEquipmentReportsPane() {
        cardLayout.show(mainPanel, "equipment report");
    }

    /* leads to employee deployment reports page */
    private void showEmployeeReportsPane() {
        cardLayout.show(mainPanel, "employee report");
    }
    
    /* leads to success message pane */
    private void showSuccessMessagePane() {
        cardLayout.show(mainPanel, "success message");
    }

    /* leads to success message pane */
    private void showErrorMessagePane() {
        cardLayout.show(mainPanel, "error message");
    }
}
