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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import secure.app.chat.MainApp;
import secure.app.chat.model.KeyPair;
import secure.app.chat.model.User;

/**
 * Dialog to edit details of a KeyPair.
 * 
 */
public class UserListController {

    @FXML
    private ListView userList;
    

    private Stage dialogStage;
    private KeyPair keyPair;
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
}