package secure.app.chat;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import secure.app.chat.view.InboxController;
import secure.app.chat.view.NewMessageController;
import secure.app.chat.view.SecurePanelController;
import secure.app.chat.view.SentController;
import secure.app.chat.view.UserListController;
import secure.app.chat.view.LoginController;
import secure.app.chat.model.Message;
import secure.app.chat.model.ObservableMessage;

public class MainApp extends Application implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 20120731125411L;
	private Stage primaryStage;
    private BorderPane rootLayout;
    private final static BigInteger one = new BigInteger("1");
    
    //get currentuser from LDAP ?!
    private String currentUser;
    
    
    public MainApp() {}
    
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
            primaryStage.getIcons().add(new Image("file:message.png"));
           
            
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
    
    public void setCurrentUser(String user) {
    	this.currentUser = user;
    }
    
    public String getCurrentUser() {
    	return currentUser;
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
            dialogStage.getIcons().add(new Image("file:message.png"));

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
            dialogStage.getIcons().add(new Image("file:message.png"));
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSentDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Sent.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sent Messages");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the person into the controller.
            SentController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            dialogStage.getIcons().add(new Image("file:message.png"));

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
            dialogStage.getIcons().add(new Image("file:message.png"));
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginMessage(boolean f, String user) {
    	if(f == false) {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.getPrimaryStage());
            alert.setTitle("Invlaid login");
            alert.setHeaderText("No such credentials are registered to the University of Southampton !");
            alert.setContentText("Please enter valid user credentials");
            //MAKE THIS MORE VAGUE ?
            alert.showAndWait();
    	} else {
            
            
    		initRootLayout();
    		showSecurePanel();
    		setCurrentUser(user);
    		System.out.print(currentUser);
    		//primaryStage.setTitle(currentUser);
    		//placeholder
    	}
    }
    
    public ObservableList<ObservableMessage> getInbox() {
    	NotificationInterface i = this.getServerCommunication();
    	ObservableList<ObservableMessage> messages = FXCollections.observableArrayList();
		try {
			for(Message messa : i.retreiveInbox(currentUser)) {
				Message mes = decryptMessage(messa);
				ObservableMessage obs = new ObservableMessage(mes.getSender(),
															  mes.getRecipient(),
															  mes.getSubject(),
															  mes.getContents());
				messages.add(obs);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return messages;
    }
    
    public ObservableList<ObservableMessage> getSent() {
    	NotificationInterface i = this.getServerCommunication();
    	ObservableList<ObservableMessage> messages = FXCollections.observableArrayList();
		try {
			for(Message messa : i.retreiveSent(currentUser)) {
				Message mes = decryptSentMessage(messa);
				ObservableMessage obs = new ObservableMessage(mes.getSender(),
															  mes.getRecipient(),
															  mes.getSubject(),
															  mes.getContents());
				messages.add(obs);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return messages;
    }
    
    public static BigInteger decrypt(byte[] pq, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec k = new SecretKeySpec(key, "AES");
		c.init(Cipher.DECRYPT_MODE, k);
		byte[] data = c.doFinal(pq);
		
		return new BigInteger(data);
		// do something with da
		
	
		// now send encryptedData to Bob..
	}
    
    
    public Pair<BigInteger, BigInteger> decryptPQ(Pair<byte[], byte[]> pqPair) {
    	String keyStr = "Y:« !^K¾S;˜ƒj	ØâÉyÙ÷ùÉ«xp¬#";
		byte[] key = keyStr.getBytes();
		Pair<BigInteger, BigInteger> pair = null;
    	
    	try {
			BigInteger p = decrypt(pqPair.getKey(), key);
			BigInteger q = decrypt(pqPair.getValue(), key);
			
			pair = new Pair<BigInteger, BigInteger>(p,q);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return pair;
    }
    
    public Message decryptMessage(Message message) {
    	NotificationInterface i = this.getServerCommunication();
		BigInteger p = null;
		BigInteger q = null;
		BigInteger modulus = null;
		BigInteger phi = null;
		BigInteger privateKey = null;
		try {
			Pair<byte[], byte[]> pqPair = i.getPQ();
			System.out.println(new String(pqPair.getValue()));
			Pair<BigInteger, BigInteger> pq = decryptPQ(pqPair);
			
			p = pq.getKey();
			q = pq.getValue();
			BigInteger publicKey = new BigInteger(i.getPublicKey(currentUser));
			phi = (p.subtract(one)).multiply(q.subtract(one));
			modulus    = p.multiply(q);
			privateKey = publicKey.modInverse(phi);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		byte[] decryptedSubject = null;
		BigInteger subjectBigInt = new BigInteger(message.getSubject());
		BigInteger decryptedSub = subjectBigInt.modPow(privateKey, modulus);
		decryptedSubject = decryptedSub.toByteArray();
		String ogSubject = new String(decryptedSubject);
		
		byte[] decryptedContent = null;
		BigInteger contentBigInt = new BigInteger(message.getContents());
		BigInteger decryptedCont = contentBigInt.modPow(privateKey, modulus);
		decryptedContent = decryptedCont.toByteArray();
		String ogContent = new String(decryptedContent);
		
		
		return new Message(message.getSender(), message.getRecipient(), ogSubject, ogContent);
    }
    
    public Message decryptSentMessage(Message message) {
    	NotificationInterface i = this.getServerCommunication();
		BigInteger p = null;
		BigInteger q = null;
		BigInteger modulus = null;
		BigInteger phi = null;
		BigInteger privateKey = null;
		try {
			Pair<byte[], byte[]> pqPair = i.getPQ();
			System.out.println(new String(pqPair.getValue()));
			Pair<BigInteger, BigInteger> pq = decryptPQ(pqPair);
			
			p = pq.getKey();
			q = pq.getValue();
			BigInteger publicKey = new BigInteger(i.getPublicKey(message.getRecipient()));
			phi = (p.subtract(one)).multiply(q.subtract(one));
			modulus    = p.multiply(q);
			privateKey = publicKey.modInverse(phi);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		byte[] decryptedSubject = null;
		BigInteger subjectBigInt = new BigInteger(message.getSubject());
		BigInteger decryptedSub = subjectBigInt.modPow(privateKey, modulus);
		decryptedSubject = decryptedSub.toByteArray();
		String ogSubject = new String(decryptedSubject);
		
		byte[] decryptedContent = null;
		BigInteger contentBigInt = new BigInteger(message.getContents());
		BigInteger decryptedCont = contentBigInt.modPow(privateKey, modulus);
		decryptedContent = decryptedCont.toByteArray();
		String ogContent = new String(decryptedContent);
		
		
		return new Message(message.getSender(), message.getRecipient(), ogSubject, ogContent);
    }
    
    public NotificationInterface getServerCommunication() {
    	Registry reg;
		try {
			reg = LocateRegistry.getRegistry("127.0.0.1",1089);
			NotificationInterface i = (NotificationInterface) reg.lookup("server");
			return i;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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