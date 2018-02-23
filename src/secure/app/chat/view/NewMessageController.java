package secure.app.chat.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import secure.app.chat.MainApp;
import secure.app.chat.model.KeyPair;
import secure.app.chat.model.Message;
import secure.app.chat.model.User;

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

    
    private Stage dialogStage;
    private MainApp mainApp;

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
        
        userList.setItems(mainApp.getUserNames());
    }
  
    @FXML
    private void handleDelete() {
        toField.setText("");
        subjectField.setText("");
        contentArea.setText("");
    }
    
    //dummy
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
	    	Message message = new Message("fs1g15", toField.getText(), 
	    			subjectField.getText(), contentArea.getText());
	        
	    	mainApp.addMessage(message);
	    	
	    	for(Message test : mainApp.getMessageData()) {
	    		System.out.println(test.getContents());
	    		System.out.println("-----------------------");
	    	}
    	}
    }
    
    
}