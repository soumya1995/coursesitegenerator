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
public class Student<E extends Comparable<E>> {
    
    private StringProperty firstName;
    private StringProperty lastName;
    private Team team;
    private StringProperty role;
    
    public Student(String initFirstName, String initLastName, Team initTeam, String initRole){
        
        firstName = new SimpleStringProperty(initFirstName);
        lastName = new SimpleStringProperty(initLastName);
        team = initTeam;
        role = new SimpleStringProperty(initRole);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }
    
    public int compareTo(E otherTA) {
        return getFirstName().compareTo(((Student)otherTA).getFirstName());
    }
    
}
