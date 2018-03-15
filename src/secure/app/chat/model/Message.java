package secure.app.chat.model;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a KeyPair.
 *
 * 
 */
public class Message implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 20120731125419L;
	
	private String sender;
    private String recipient;
    private String subject;
    private String contents;

    /**
     * Default constructor.
     */
    public Message() {
        this(null, null, null, null);
    }

    
    public Message(String sender, String recipient, String subject, String contents) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.contents = contents;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getContents() {
        return contents;
    }

    
}