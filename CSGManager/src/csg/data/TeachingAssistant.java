package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 * @co-author Soumya Das
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private BooleanProperty undergrad;
    private final StringProperty name;
    private final StringProperty email;

    /**
     * Constructor initializes both the TA name and email.
     */
    public TeachingAssistant(Boolean initUndergrad,String initName, String initEmail) {
        undergrad = new SimpleBooleanProperty(initUndergrad);
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES
    
    public BooleanProperty isUndergrad(){
        return undergrad;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}