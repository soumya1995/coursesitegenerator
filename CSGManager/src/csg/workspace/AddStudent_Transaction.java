/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.StudentData;
import csg.data.WorkspaceData;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class AddStudent_Transaction implements jTPS_Transaction{
    
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    private StudentData data;
    
    public AddStudent_Transaction(String firstName, String lastName, String team, String role, CSGManager app) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
        data = ((WorkspaceData)app.getDataComponent()).getStudentData();
    }
    
    public void doTransaction(){
        data.addStudent(firstName, lastName, team, role);
    }
    
    public void undoTransaction(){
        data.removeStudent(firstName, lastName, team, role);
    }
}
        
