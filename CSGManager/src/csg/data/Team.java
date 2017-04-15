/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class Team<E extends Comparable<E>> {
    
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
   
    
    public Team(String initName, String initColor, String initTextColor, String initLink){
        
        name = new SimpleStringProperty(initName);
        color = new SimpleStringProperty(initColor);
        textColor = new SimpleStringProperty(initTextColor);
        link = new SimpleStringProperty(initLink);
        
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setName(String initName){
        name.set(initName);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getTextColor() {
        return textColor.get();
    }

    public void setTextColor(String textColor) {
        this.textColor.set(textColor);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }
    
    public int compareTo(E otherTA) {
        return getName().compareTo(((Team)otherTA).getName());
    }
    
}
