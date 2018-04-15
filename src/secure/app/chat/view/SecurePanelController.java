package secure.app.chat.view;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import secure.app.chat.MainApp;
import secure.app.chat.NotificationInterface;
import secure.app.chat.model.Message;

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
        
    }
    
    @FXML
    private void handleUserList() {
        mainApp.showUserListDialog();
        
    }
    
    @FXML
    private void handleInbox() throws RemoteException {
    	
    	mainApp.showInboxDialog();
        
    }
    
    @FXML
    private void handleNewMessage() {
        mainApp.showNewMessageDialog();
        
    }
    
    @FXML
    private void handleSent() {
        mainApp.showSentDialog();
        
    }
    
}