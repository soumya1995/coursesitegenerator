/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.Student;
import csg.data.StudentData;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import java.util.ArrayList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class DeleteTeam_Transaction implements jTPS_Transaction{
    
    private String name;
    private String color;
    private String textColor;
    private String link;
    private TeamData teamData;
    private StudentData studentData;
    ArrayList<Student> students;
    
    public DeleteTeam_Transaction(String name, String color, String textColor, String link, CSGManager app) {
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        teamData = ((WorkspaceData)app.getDataComponent()).getTeamData();
        studentData = ((WorkspaceData)app.getDataComponent()).getStudentData();
        students = teamData.getRemovedStudents(name, studentData);
    }
    
    public void doTransaction(){
        teamData.removeTeam(name, color, textColor, link, studentData);
    }
    
    public void undoTransaction(){
        teamData.addTeam(name, color, textColor, link);
        teamData.addRemovedStudents(students, studentData);
    }
    
}
