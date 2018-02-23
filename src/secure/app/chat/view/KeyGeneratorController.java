package secure.app.chat.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import secure.app.chat.MainApp;
import secure.app.chat.model.KeyPair;

/**
 * Dialog to edit details of a KeyPair.
 * 
 */
public class KeyGeneratorController {

    @FXML
    private TextField uniIdField;
    @FXML
    private TextField pubKeyField;
    @FXML
    private TextField privKeyField;
    @FXML
    private Button generateButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;


    private Stage dialogStage;
    private KeyPair keyPair;
    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	pubKeyField.setEditable(false);
    	privKeyField.setEditable(false);
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
    @FXML
    private void handleGenerate() {
        if (uniIdField.getText().equals("")) {
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No ID entered");
            alert.setHeaderText("No valid University ID entered");
            alert.setContentText("Please enter a valid Univeresity ID before attempting "
            		+ "to generate key pair");

            alert.showAndWait();
        } else {
        	int dummy = Integer.parseInt(uniIdField.getText());
        	int pub = dummy - 1;
        	int priv = dummy + 200000;
        	
        	pubKeyField.setText(String.valueOf(pub));
        	privKeyField.setText(String.valueOf(priv));
        }
    }
    
    @FXML
    private void handleSave() {
        if (pubKeyField.getText().equals("") || privKeyField.getText().equals("")) {
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("An error has occured");
            alert.setContentText("The key pair has not been generated Please try again");

            alert.showAndWait();
        } else {
        	  try {
        		File pubKeyFile = new File("C:\\MyThings\\KeyPair\\PublicKey.txt");  
        		File privKeyFile = new File("C:\\MyThings\\KeyPair\\PrivateKey.txt"); 
        		
				BufferedWriter writerPub = new BufferedWriter(new FileWriter(pubKeyFile));
				BufferedWriter writerPriv = new BufferedWriter(new FileWriter(privKeyFile));
				
				writerPub.write(pubKeyField.getText());
				writerPriv.write(privKeyField.getText());
				
				writerPub.close();
				writerPriv.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    @FXML
    private void handleReset() {
        pubKeyField.setText("");
        privKeyField.setText("");
        uniIdField.setText("");
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        
    }
}