package secure.app.chat;

import java.math.BigInteger;
import java.rmi.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.util.Pair;
import secure.app.chat.model.Message;

public interface NotificationInterface extends Remote {
	
	public boolean getLogin(String user, String pass) throws RemoteException;
	
	public void setUsers() throws RemoteException;
	
	public ArrayList<String> getUsers() throws RemoteException;
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public Connection getConnection() throws RemoteException;
	
	public void generatePubKeys() throws RemoteException;
	
	public void insertPubKeys(String id, String pubKey) throws RemoteException;
	
	public String getPublicKey(String username) throws RemoteException;
	
	public String getUserFromKey(String pubKey) throws RemoteException;
	
	//public String getMessageContents(String messageContents) throws RemoteException;
	
	//retreive p and q (still have to encrypt them later)
	
	public Pair<byte[], byte[]> getPQ() throws RemoteException;

	public void insertMessage(String message, String sender, String recipient, String subject) 
			throws RemoteException;
	
	public ArrayList<Message> retreiveInbox(String user) throws RemoteException;
	
	public ArrayList<Message> retreiveSent(String user) throws RemoteException;
	
	public byte[] encrypt(BigInteger pq) throws RemoteException, NoSuchAlgorithmException, NoSuchPaddingException, 
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
}
