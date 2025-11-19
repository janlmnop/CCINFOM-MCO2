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
    
    private Controller controller;

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
    private LoginErrorMessage loginErrorMessage = new LoginErrorMessage();
    private TranErrorMessage tranErrorMessage = new TranErrorMessage();



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
        mainPanel.add(loginErrorMessage, "login error message");
        mainPanel.add(tranErrorMessage, "transaction error message");

        //test
        controller = new Controller(this, loginFrame, rescueOperation, assignToShelter, releaseFromShelter, borrowEquipment, returnEquipment, responseReport, loginErrorMessage, tranErrorMessage);
        //test

        /* redirections */
        /* from transactions menu */
        transactionsMenu.getBtnT1().addActionListener(e -> showRescueOperationPane());
        transactionsMenu.getBtnT2().addActionListener(e -> showAssignToShelterPane());
        transactionsMenu.getBtnT3().addActionListener(e -> showReleaseFromShelterPane());
        transactionsMenu.getBtnT4().addActionListener(e -> showBorrowEquipmentPane());
        transactionsMenu.getBtnT5().addActionListener(e -> showReturnEquipmentPane());
        transactionsMenu.getBtnReports().addActionListener(e -> showViewReportsPane());

        /* for back buttons (all transsactions -> transaction menu) */
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

        /* for back buttons (all reports -> transaction menu) */
        responseReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        shelterReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        equipmentReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());
        employeeReport.getBackTransButton().addActionListener(e -> showTransactionsMenu());

        /* for back buttons (all reports -> reports pane) */
        responseReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        shelterReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        equipmentReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        employeeReport.getBackRepsButton().addActionListener(e -> showViewReportsPane());
        
        /* add mainPanel to Frame */
        add(mainPanel);
        setVisible(true);
    }

    public void showLoginPane() {
        cardLayout.show(mainPanel, "login");
    }

    /* leads to transactions menu frame */
    public void showTransactionsMenu() {
        cardLayout.show(mainPanel, "transactions");
    }

    /* leads to assign to shelter pane */
    public void showRescueOperationPane() {
        cardLayout.show(mainPanel, "rescue operation");

        controller.loadEmployeeNames(rescueOperation);
        controller.loadResidentNames(rescueOperation);
    }

    /* leads to assign to shelter pane */
    public void showAssignToShelterPane() {
        cardLayout.show(mainPanel, "assign to shelter");

        controller.loadEmployeeNames(assignToShelter);
    }

    /* leads to release from shelter pane */
    public void showReleaseFromShelterPane() {
        cardLayout.show(mainPanel, "release from shelter");

        controller.loadEmployeeNames(releaseFromShelter);
    }

    /* leads to borrow equipment pane */
    public void showBorrowEquipmentPane() {
        cardLayout.show(mainPanel, "borrow equipment");

        controller.loadEquipmentNames(borrowEquipment);
        controller.loadResidentNames(borrowEquipment);
    }


    /* leads to return equipment pane */
    public void showReturnEquipmentPane() {
        cardLayout.show(mainPanel, "return equipment");

        controller.loadEquipmentNames(returnEquipment);
        controller.loadResidentNames(returnEquipment);
    }

    /* leads to view reports pane */
    public void showViewReportsPane() {
        cardLayout.show(mainPanel, "view reports");
    }

    /* leads to response reports pane */
    public void showResponseReportsPane() {
        cardLayout.show(mainPanel, "response report");
    }
    
    /* leads to shelter occupancy reports pane */
    public void showShelterReportsPane() {
        cardLayout.show(mainPanel, "shelter report");
        controller.loadShelterReportOptions(shelterReport);
        controller.refreshShelterReport(shelterReport);
        shelterReport.getFilterButton().addActionListener(e -> controller.refreshShelterReport(shelterReport));
    }
    
    /*leads to equipment utilization reports pane */
    public void showEquipmentReportsPane() {
        cardLayout.show(mainPanel, "equipment report");
                controller.loadEquipmentReportOptions(equipmentReport);
        controller.refreshEquipmentReport(equipmentReport);
        equipmentReport.getFilterButton().addActionListener(e -> controller.refreshEquipmentReport(equipmentReport));
    }

    /* leads to employee deployment reports page */
    public void showEmployeeReportsPane() {
        cardLayout.show(mainPanel, "employee report");
    }
    
    /* leads to success message pane */
    public void showSuccessMessagePane() {
        cardLayout.show(mainPanel, "success message");
    }

    /* leads to login error message pane */
    public void showLoginErrorMessagePane(String message) {
        loginErrorMessage.setErrorMessage(message);
        cardLayout.show(mainPanel, "login error message");
    }

    /* leads to Transactions error message pane */
    public void showTranErrorMessagePane(String message) {
        tranErrorMessage.setErrorMessage(message);
        cardLayout.show(mainPanel, "transaction error message");
    }
}
