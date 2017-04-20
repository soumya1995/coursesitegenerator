/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class Recitation<E extends Comparable<E>> implements Comparable<E>  {
    
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty day;
    private final StringProperty location;
    private final StringProperty ta1;
    private final StringProperty ta2;
    
    /**
     * Constructor initializes both the TA name and email.
     */
    public Recitation(String initSection,String initInstructor, String initDay, String initLocation, String initTA1, String initTA2) {
        section = new SimpleStringProperty(initSection);
        instructor = new SimpleStringProperty(initInstructor);
        day = new SimpleStringProperty(initDay);
        location = new SimpleStringProperty(initLocation);
        ta1 = new SimpleStringProperty(initTA1);
        ta2 = new SimpleStringProperty(initTA2);
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getInstructor() {
        return instructor.get();
    }

    public void setInstructor(String instructor) {
        this.instructor.set(instructor);
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getTa1() {
        return ta1.get();
    }

    public void setTa1(String ta1) {
        this.ta1.set(ta1);
    }

    public String getTa2() {
        return ta2.get();
    }

    public void setTa2(String ta2) {
        this.ta2.set(ta2);
    }
    
    public int compareTo(E otherTA) {
        return getSection().compareTo(((Recitation)otherTA).getSection());
    }
    
    
    
}
