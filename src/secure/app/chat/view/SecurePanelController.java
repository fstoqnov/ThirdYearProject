package secure.app.chat.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import secure.app.chat.MainApp;

public class SecurePanelController {
    

    @FXML
    private Button newMessage;
    @FXML
    private Button inbox;
    @FXML
    private Button sent;
    @FXML
    private Button userList;
    @FXML
    private Button keyGenerator;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SecurePanelController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        
    }
    
    @FXML
    private void handleKeyGenerator() {
        mainApp.showKeyGenerator();
        
    }
    
    
    
}