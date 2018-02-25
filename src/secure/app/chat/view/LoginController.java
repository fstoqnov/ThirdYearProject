package secure.app.chat.view;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import secure.app.chat.LoginInterface;
import secure.app.chat.MainApp;

public class LoginController {
    
	@FXML
	private TextField userName;
	
	@FXML
	private TextField password;
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoginController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        
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
    
    public void handleLogin() {
    	boolean f=false;
    	
    	try {
			Registry reg = LocateRegistry.getRegistry("127.0.0.1",1094);
			LoginInterface i = (LoginInterface) reg.lookup("login");
			f=i.getLogin(userName.getText(), password.getText());
			
			mainApp.showLoginMessage(f);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}