/**
 *  This is the apps driver. Run the app here.
 * 
 *  Notes:
 *  - call model, view, and controller here
 */

import view.*;
import controller.*;

public class Driver {
    public static void main(String[] args) throws Exception {
        MainFrame view = new MainFrame();
        Controller controller = new Controller();
    }
}