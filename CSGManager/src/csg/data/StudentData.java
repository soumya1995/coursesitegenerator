/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Soumya
 */
public class StudentData {
    
     CSGManager app;
     WorkspaceData workspaceData;
     ObservableList<Student> students;
     
     public StudentData(WorkspaceData data) {
        workspaceData = data; 
        students = FXCollections.observableArrayList();
    }
     
     public StudentData(CSGManager initApp) {
        app = initApp;
        students = FXCollections.observableArrayList();
    }

    public ObservableList<Student> getStudents() {
        return students;
    }
    
    public void addStudent(String initFirstName,String initLastName, String initTeam, String initRole){
        
        TeamData data;
        if(app == null)
            data = workspaceData.getTeamData();
        else
            data = ((WorkspaceData)app.getDataComponent()).getTeamData();
        //GET THE TEAM AFTER CHECKING IF THE TEAM EXISTS
        Team team = null;
        for(Team t:data.getTeams()){
            if(t.getName().equals(initTeam))
                team = t;
        }
        
        // MAKE THE STUDENT
        if(team!=null){
            Student student = new Student(initFirstName, initLastName, team.getName(), initRole);

        // ADD THE TEAM
         if (!containsStudent(initFirstName)) 
            students.add(student);
         }

        // SORT THE TEAMS
        Collections.sort(students);
    }
    
    public boolean containsStudent(String initFirstName){
        
    for (Student s : students) {
            if (s.getFirstName().equals(initFirstName))
                return true;
        }
        return false;
    }   
}
