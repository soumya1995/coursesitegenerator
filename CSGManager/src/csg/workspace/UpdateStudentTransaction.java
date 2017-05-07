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
public class UpdateStudentTransaction implements jTPS_Transaction{
    
    private String oldFirstName;
    private String oldLastName;
    private String oldTeam;
    private String oldRole;
    private String newFirstName;
    private String newLastName;
    private String newTeam;
    private String newRole;
    private StudentData data;
    
    public UpdateStudentTransaction(String oldFirstName, String oldLastName, String oldTeam, String oldRole, String newFirstName, String newLastName, String newTeam, String newRole, CSGManager app) {
        this.oldFirstName = oldFirstName;
        this.oldLastName = oldLastName;
        this.oldRole = oldRole;
        this.oldTeam = oldTeam;
        this.newFirstName = newFirstName;
        this.newLastName = newLastName;
        this.newTeam = newTeam;
        this.newRole = newRole;
        data = ((WorkspaceData)app.getDataComponent()).getStudentData();
    }
    
    public void doTransaction(){
        data.removeStudent(oldFirstName, oldLastName, oldTeam, oldRole);
        data.addStudent(newFirstName, newLastName, newTeam, newRole);
    }
    
    public void undoTransaction(){
        data.removeStudent(newFirstName, newLastName, newTeam, newRole);
        data.addStudent(oldFirstName, oldLastName, oldTeam, oldRole);
    }
    
}
