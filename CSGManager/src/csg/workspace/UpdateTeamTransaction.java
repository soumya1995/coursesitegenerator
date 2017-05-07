/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.StudentData;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class UpdateTeamTransaction implements jTPS_Transaction{
    
    private String oldName;
    private String oldColor;
    private String oldTextColor;
    private String oldLink;
    private String newName;
    private String newColor;
    private String newTextColor;
    private String newLink;
    private TeamData teamData;
    private StudentData studentData;
    
    public UpdateTeamTransaction(String oldName, String oldColor, String oldTextColor, String oldLink, String newName, String newColor, String newTextColor, String newLink, CSGManager app) {
        this.oldName = oldName;
        this.oldColor = oldColor;
        this.oldTextColor = oldTextColor;
        this.oldLink = oldLink;
        this.newName = newName;
        this.newColor = newColor;
        this.newTextColor = newTextColor;
        this.newLink = newLink;            
        teamData = ((WorkspaceData)app.getDataComponent()).getTeamData();
        studentData = ((WorkspaceData)app.getDataComponent()).getStudentData();
    }
    
    public void doTransaction(){
        teamData.removeTeam(oldName, oldColor, oldTextColor, oldLink);
        teamData.addTeam(newName, newColor, newTextColor, newLink);
        teamData.replaceTeam(oldName, newName, studentData);
    }
    
    public void undoTransaction(){
        teamData.removeTeam(newName, newColor, newTextColor, newLink);
        teamData.addTeam(oldName, oldColor, oldTextColor, oldLink);
        teamData.replaceTeam(newName, oldName, studentData);
    }
    
}
