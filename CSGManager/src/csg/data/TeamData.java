/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Soumya
 */
public class TeamData {
    
     CSGManager app;
     ObservableList<Team> teams;
     
     public TeamData() {
        teams = FXCollections.observableArrayList();
    }
     
     public TeamData(CSGManager initApp) {
        app = initApp;
        teams = FXCollections.observableArrayList();
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }
    
    public void addTeam(String initName,String initColor, String initTextColor, String initLink){
        
        // MAKE THE TEAM
        Team team = new Team(initName, initColor, initTextColor, initLink);

        // ADD THE TEAM
         if (!containsTeam(initName, initColor, initTextColor, initLink)) {
            teams.add(team);
         }

        // SORT THE TEAMS
        Collections.sort(teams);
    }
    
    public boolean containsTeam(String initName,String initColor, String initTextColor, String initLink){
        
    for (Team t : teams) {
            if (t.getName().equals(initName) || t.getColor().equals(initColor))
                return true;
        }
        return false;
    }    
    
    public void removeTeam(String initName,String initColor, String initTextColor, String initLink, StudentData studentData){
        for(Team t:teams){
            if (t.getName().equals(initName) && t.getColor().equals(initColor)){
                List<Student> students = studentData.getStudents();
                List<Student> studentsCopy = new ArrayList<>();
                for(Student s:students)
                    studentsCopy.add(s);
      
                for(Student s: studentsCopy){
                    if(s.getTeam().equals(initName))
                        students.remove(s);
                }
                teams.remove(t);   
                return;
            }
        }
        
    }
    
    public void removeTeam(String initName,String initColor, String initTextColor, String initLink){
        for(Team t:teams){
            if (t.getName().equals(initName) && t.getColor().equals(initColor)){
                teams.remove(t); 
                return;
            }
        }
        
        
    }
    
    public void replaceTeam(String oldName, String newName, StudentData studentData){
        List<Student> students = studentData.getStudents();
        for(Student s: students){
            if(s.getTeam().equals(oldName))
                s.setTeam(newName);
        }
    }
    
    public ArrayList<Student> getRemovedStudents(String name, StudentData studentData){
        List<Student> students = studentData.getStudents();
        ArrayList<Student> removedStudents = new ArrayList<>();
        for(Student s: students){
            if(s.getTeam().equals(name))
                removedStudents.add(s);
        }
       return removedStudents;    
    }
    
    public void addRemovedStudents(ArrayList<Student> removedStudents, StudentData studentData){
        List<Student> students = studentData.getStudents();
        for(Student s: removedStudents) 
            students.add(s);
    }
    
    public void reset(){
        teams.clear();
    }
}
