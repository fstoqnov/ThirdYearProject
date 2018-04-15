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
public class ObservableMessage{

	
	private StringProperty sender;
    private StringProperty recipient;
    private StringProperty subject;
    private StringProperty contents;
    

    /**
     * Default constructor.
     */
    public ObservableMessage() {
        this(null, null, null, null);
    }

    
    public ObservableMessage(String sender, String recipient, String subject, String contents) {
        this.sender = new SimpleStringProperty(sender);
        this.recipient = new SimpleStringProperty(recipient);
        this.subject = new SimpleStringProperty(subject);
        this.contents = new SimpleStringProperty(contents);
    }

    public String getSender() {
        return sender.get();
    }

    public void setSender(String sender) {
        this.sender.set(sender);
    }

    public StringProperty senderProperty() {
        return sender;
    }

    
    
    public String getRecipient() {
        return recipient.get();
    }

    public void setRecipient(String recipient) {
        this.recipient.set(recipient);
    }
    
    public StringProperty recipientProperty() {
        return recipient;
    }

    
    
    
    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }
    
    public StringProperty subjectProperty() {
        return subject;
    }
    
    
    
    
    public String getContents() {
        return contents.get();
    }

    public void setContents(String contents) {
        this.contents.set(contents);
    }
    
    public StringProperty contentsProperty() {
        return contents;
    }
    
}