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
public class User {

    private StringProperty userName;
    private StringProperty password;
    

    /**
     * Default constructor.
     */
    public User() {
        this(null, null);
    }

    
    public User(String userName, String password) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);

    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty passwordProperty() {
        return password;
    }

    
}