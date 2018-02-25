package secure.app.chat;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import secure.app.chat.view.InboxController;
import secure.app.chat.view.KeyGeneratorController;
import secure.app.chat.view.NewMessageController;
import secure.app.chat.view.SecurePanelController;
import secure.app.chat.view.UserListController;
import secure.app.chat.view.LoginController;
import secure.app.chat.model.Message;
import secure.app.chat.model.User;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Message> messageData = FXCollections.observableArrayList();
    
    public MainApp() {
    	userData.add(new User("fs1g15", "abc"));
    	userData.add(new User("yi1u15", "abc"));
    	userData.add(new User("dsk1u14", "abc"));
    	userData.add(new User("nmg", "abc"));
    	userData.add(new User("ofb1u14", "abc"));
    	userData.add(new User("ejz", "abc"));
    	userData.add(new User("su1b12", "abc"));
    	
    	messageData.add(new Message("fs1g15" , "ejz" , "about meeting" , "I couldn't attend, sorry"));
    	messageData.add(new Message("yi1u15" , "su1b12" , "labs" , "we do have labs on wednesday!"));
    	messageData.add(new Message("ejz" , "fs1g15" , "new meeting" , "call me to schedule a new one"));
    	messageData.add(new Message("fs1g15" , "nmg" , "about coursework marks" , "I don't "
    			+ "think my marks are adequate"));
    	messageData.add(new Message("fs1g15" , "su1b12" , "I can make the time" , "yes"));
    	messageData.add(new Message("ofb1u14" , "fs1g15" , "Project ideas" , "I like them!"));
    	
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("University of Southampton Secure Messaging");
        primaryStage.setResizable(false);
        
        initLoginRootLayout();

        showLoginForm();
    }

    /**
     * Initializes the root layout.
     */
    
    public void initLoginRootLayout() {
    	try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginRootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<User> getUserData() {
        return userData;
    }
    
    public ObservableList<String> getUserNames(){
    	ObservableList<String> usernames = FXCollections.observableArrayList();
    	
    	for(User user : userData) {
    		usernames.add(user.getUserName());
    	}
    	
    	return usernames;
    }
    
    public ObservableList<Message> getMessageData() {
        return messageData;
    }
    
    public void addMessage(Message message) {
        messageData.add(message);
    }
    
   
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showSecurePanel() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SecurePanel.fxml"));
            AnchorPane securePanel = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(securePanel);
            
            
            // Give the controller access to the main app.
            SecurePanelController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginForm() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
            AnchorPane loginForm = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(loginForm);
            
            // Give the controller access to the main app.
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showKeyGenerator() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/KeyGenerator.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Key Generator");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            KeyGeneratorController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            dialogStage.setResizable(false);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showUserListDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UserList.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("List of Users");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the person into the controller.
            UserListController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showInboxDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Inbox.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inbox");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the person into the controller.
            InboxController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showNewMessageDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NewMessage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the person into the controller.
            NewMessageController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginMessage(boolean f) {
    	if(f == false) {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.getPrimaryStage());
            alert.setTitle("Not a valid acc");
            alert.setHeaderText("No valid University acc entered");
            alert.setContentText("Please enter a valid Univeresity acc before loging in");

            alert.showAndWait();
    	} else {
            
            
    		initRootLayout();
    		showSecurePanel();
    	}
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
