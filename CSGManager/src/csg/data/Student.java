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
public class Student<E extends Comparable<E>> implements Comparable<E> {
    
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty team;
    private StringProperty role;
    
    public Student(String initFirstName, String initLastName, String initTeam, String initRole){
        
        firstName = new SimpleStringProperty(initFirstName);
        lastName = new SimpleStringProperty(initLastName);
        team = new SimpleStringProperty(initTeam);
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

    public String getTeam() {
        return team.get();
    }

    public void setTeam(Team team) {
        this.team.set(team.getName());
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
