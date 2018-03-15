package secure.app.chat.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import secure.app.chat.MainApp;
import secure.app.chat.NotificationInterface;
import secure.app.chat.model.Message;

/**
 * Dialog to edit details of a KeyPair.
 * 
 */
public class NewMessageController {

	@FXML
    private ListView userList;

    @FXML
    private TextField toField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextArea contentArea;
    @FXML
    private Button deletButton;
    @FXML
    private Button sendButton;
    
    private ObservableList<String> userData = FXCollections.observableArrayList();
    
    private Stage dialogStage;
    private MainApp mainApp;
    
    private BigInteger modulus =null, publicKey = null;
    

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
        NotificationInterface notificationInt = mainApp.getServerCommunication();
        
        try {
			for(String user: notificationInt.getUsers()) {
				userData.add(user);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        userList.setItems(userData);
    }
  
    @FXML
    private void handleDelete() {
        toField.setText("");
        subjectField.setText("");
        contentArea.setText("");
        sendButton.setDisable(false);
    }
    
    BigInteger encrypt(BigInteger message) {
       return message.modPow(publicKey, modulus);
    }
    
   
    @FXML
    private void handleSend() {
    	if(toField.getText().equals("") || subjectField.getText().equals("") || 
    			contentArea.getText().equals("")) {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Not enough information entered");
            alert.setHeaderText("All fields required");
            alert.setContentText("Please enter intended recipient, subject and contents when sending!");

            alert.showAndWait();
    	} else {
    		NotificationInterface i = mainApp.getServerCommunication();
    		BigInteger p = null;
    		BigInteger q = null;
    		try {
    			Pair<byte[], byte[]> pqPair = i.getPQ();
    			Pair<BigInteger, BigInteger> pq = mainApp.decryptPQ(pqPair);
    			
    			p = pq.getKey();
    			q = pq.getValue();
				publicKey = new BigInteger(i.getPublicKey(toField.getText()));
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		modulus = p.multiply(q);
    		
    		byte[] subjectBytes = subjectField.getText().getBytes();
    		BigInteger subject = new BigInteger(subjectBytes);
    		BigInteger encryptedSub = encrypt(subject);
    		String encryptedSubject = encryptedSub.toString();
    		
    		byte[] contentBytes = contentArea.getText().getBytes();
    		BigInteger content = new BigInteger(contentBytes);
    		BigInteger encryptedCon = encrypt(content);
    		String encryptedContent = encryptedCon.toString();
    		
    		
    		try {
				i.insertMessage(encryptedContent,mainApp.getCurrentUser(), toField.getText(), encryptedSubject);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Success");
            alert.setHeaderText("Encrypted Message sent");
            alert.setContentText("Thank you for using the University of Southampton secure Messageing"
            		+ "Application !");

            alert.showAndWait();
    		sendButton.setDisable(true);
    	}
    }
}