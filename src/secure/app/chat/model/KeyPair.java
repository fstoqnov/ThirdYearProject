package secure.app.chat.model;

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
public class KeyPair {

    private StringProperty pubKey;
    private StringProperty privKey;

    /**
     * Default constructor.
     */
    public KeyPair() {
        this(null, null);
    }

    
    public KeyPair(String pubKey, String privKey) {
        this.pubKey = new SimpleStringProperty(pubKey);
        this.privKey = new SimpleStringProperty(privKey);

    }

    public String getPubKey() {
        return pubKey.get();
    }

    public void setPubKey(String pubKey) {
        this.pubKey.set(pubKey);
    }

    public StringProperty firstNameProperty() {
        return pubKey;
    }

    public String getPrivKey() {
        return privKey.get();
    }

    public void setPrivKey(String privKey) {
        this.privKey.set(privKey);
    }

    
}