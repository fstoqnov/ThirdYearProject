package secure.app.chat;

import java.io.Serializable;
import java.math.BigInteger;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import javafx.util.Pair;
import secure.app.chat.model.Message;

public class NotificationImplement extends UnicastRemoteObject implements NotificationInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 20120731125400L;
	
	private Authentication auth = new Authentication();
	private PublicExponentGenerator pubKeyGen = new PublicExponentGenerator();
	
	private final static SecureRandom random = new SecureRandom(); //secure full random generator
	private BigInteger p,q;
	private byte[] encP,encQ;
	
	//bits of modulus (think about giving users access to setting it depending on message length
	private int N = 2048; 
	
	private HashMap<String,String> usersPubKeys;
	
	private HashMap<String, String> senderMessages;
	private HashMap<String, String> recipientMessages;
	private HashMap<String, String> subjectMessages;
	
	private ArrayList<String> users =  new ArrayList<String>();
	
	public NotificationImplement() throws RemoteException {
		setUsers();
		System.out.println("generating keys");
		generatePubKeys();
		
		p = BigInteger.probablePrime(N/2, random);
		q = BigInteger.probablePrime(N/2, random);
		
		try {
			encP = encrypt(p);
			encQ = encrypt(q);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	@Override
	public boolean getLogin(String user, String pass) {
		//boolean found = auth.authenticateUser(user, pass);
		boolean found = true;
		return found;
	}

	
		
	@Override
	public void setUsers() {
		users.add("fs1g15");
    	/*users.add("yi1u15");
    	users.add("dsk1u14");
    	users.add("nmg");
    	users.add("ofb1u14");
    	users.add("ejz");
    	users.add("su1b12");*/
	}
		
	@Override
	public ArrayList<String> getUsers(){
		return users;
	}
	
	
	@Override
	public void generatePubKeys() {
		try {
			dropAndRecreateTable();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String user : this.getUsers()) {
			System.out.println("generating key for user: " + user);
			try {
				insertPubKeys(user,pubKeyGen.generateExponent(user));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Connection getConnection() throws RemoteException {
		// SQLite connection string
    	String url = "jdbc:sqlite:C://My Things/SQLite/secureApplication.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
	}
	
	public void dropAndRecreateTable() throws RemoteException {
		String tableDrop = "DROP TABLE IF EXISTS 'people' ";
		
		String sqlPeople = "CREATE TABLE IF NOT EXISTS people (\n"
                + "	id varchar PRIMARY KEY,\n"
                + "	publicKey varchar NOT NULL\n"
                + ");";
		
		try (Connection conn = this.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(tableDrop)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        try (Connection conn = this.getConnection();
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sqlPeople);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	@Override
	public void insertPubKeys(String id, String pubKey) throws RemoteException {
		String sql = "INSERT INTO people(id,publicKey) VALUES(?,?)";
        
        try (Connection conn = this.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, pubKey);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	@Override
	public String getPublicKey(String id) throws RemoteException {
	     String sql = "SELECT publicKey "
	    		 	+ "FROM people WHERE id = ?";
 
		 try (Connection conn = this.getConnection();
		      PreparedStatement pstmt  = conn.prepareStatement(sql)){
		     
		     // set the value
		     pstmt.setString(1,id);
		     //
		     ResultSet rs  = pstmt.executeQuery();
		     while (rs.next()) {
		         return rs.getString("publicKey");
		     }
		 } catch (SQLException e) {
		     System.out.println(e.getMessage());
		 }
		 return null;
	}

	@Override
	public String getUserFromKey(String pubKey) throws RemoteException {
		String sql = "SELECT id "
	    		   + "FROM people WHERE id = ?";
	
		try (Connection conn = this.getConnection();
		     PreparedStatement pstmt  = conn.prepareStatement(sql)){
		     
		    // set the value
		    pstmt.setString(1,pubKey);
		    //
		    ResultSet rs  = pstmt.executeQuery();
		    while (rs.next()) {
		        return rs.getString("username");
		    }
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
		return null;
	}

	
	

	@Override
	public Pair<byte[], byte[]> getPQ() throws RemoteException {
		return new Pair<byte[], byte[]>(encP,encQ);
	}
	
	@Override
	public void insertMessage(String message, String sender, String recipient, String subject) {
        String sql = "INSERT INTO messages(message,sender,recipient,subject) VALUES(?,?,?,?)";
 
        try (Connection conn = this.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, message);
            pstmt.setString(2, sender);
            pstmt.setString(3, recipient);
            pstmt.setString(4, subject);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// public Message(String sender, String recipient, String subject, String contents)
	
	@Override
	public ArrayList<Message> retreiveInbox(String user) throws RemoteException {
		ArrayList<Message> inbox = new ArrayList<Message>();
		
		String sql = "SELECT message, sender, recipient, subject "
				+ "FROM messages WHERE recipient = ?";
        
		try (Connection conn = this.getConnection();
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            // set the value
	            pstmt.setString(1,user);
	            //
	            ResultSet rs  = pstmt.executeQuery();
	            
	            // loop through the result set
	            while (rs.next()) {
	            	Message message = new Message(rs.getString("sender"), 
	            								  rs.getString("recipient"),
	            								  rs.getString("subject"),
	            								  rs.getString("message"));
	            	
	            	inbox.add(message);
	            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return inbox;
	}
	
	@Override
	public ArrayList<Message> retreiveSent(String user) throws RemoteException {
		ArrayList<Message> inbox = new ArrayList<Message>();
		
		String sql = "SELECT message, sender, recipient, subject "
				+ "FROM messages WHERE sender = ?";
        
		try (Connection conn = this.getConnection();
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            // set the value
	            pstmt.setString(1,user);
	            //
	            ResultSet rs  = pstmt.executeQuery();
	            
	            // loop through the result set
	            while (rs.next()) {
	            	Message message = new Message(rs.getString("sender"), 
	            								  rs.getString("recipient"),
	            								  rs.getString("subject"),
	            								  rs.getString("message"));
	            	
	            	inbox.add(message);
	            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return inbox;
	}
	
	@Override
	public byte[] encrypt(BigInteger pq) throws RemoteException, NoSuchAlgorithmException, NoSuchPaddingException, 
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String keyStr = "Y:« !^K¾S;˜ƒj	ØâÉyÙ÷ùÉ«xp¬#";
		byte[] key = keyStr.getBytes();
		
		byte[] dataToSend = pq.toByteArray();
		
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec k = new SecretKeySpec(key, "AES");
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] encryptedData = c.doFinal(dataToSend);
		
		return encryptedData;
		// now send encryptedData to Bob..
	}
	
	//used to testing TODO remove it
	public void look(ArrayList<Message> inbox) {
		System.out.println(inbox.size());
		for(Message message : inbox) {
			System.out.println(message.getContents());
		}
	}

	
	
}
