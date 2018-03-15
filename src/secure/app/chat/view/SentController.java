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
import secure.app.chat.model.ObservableMessage;

/**
 * Dialog to edit details of a KeyPair.
 * 
 */
public class SentController {

	@FXML
    private TableView<ObservableMessage> sentTable;
    @FXML
    private TableColumn<ObservableMessage, String> recipientColumn;
    @FXML
    private TableColumn<ObservableMessage, String> subjectColumn;

    @FXML
    private TextField toField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextArea contentArea;

    
    private Stage dialogStage;
    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	recipientColumn.setCellValueFactory(cellData -> cellData.getValue().recipientProperty());
    	subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
    	toField.setEditable(false);
    	subjectField.setEditable(false);
    	
    	 // Clear message details.
        showMessageDetails(null);

        // Listen for selection changes and show the message details when changed.
        sentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMessageDetails(newValue));
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
        
        sentTable.setItems(mainApp.getSent());
        
    }
  
    
    private void showMessageDetails(ObservableMessage message) {
        if (message == null) {
        	
        	toField.setText("");
        	subjectField.setText("");
        	contentArea.setText("");

        } else {
            
        	toField.setText(message.getSender());
        	subjectField.setText(message.getSubject());
        	contentArea.setText(message.getContents());
        }
    }
}