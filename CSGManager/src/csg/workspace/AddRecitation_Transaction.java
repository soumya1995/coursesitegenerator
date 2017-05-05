/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.RecitationData;
import csg.data.WorkspaceData;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class AddRecitation_Transaction implements jTPS_Transaction{
    
    private String section;
    private String instructor;
    private String day;
    private String location;
    private String ta1;
    private String ta2;
    private RecitationData data;
            
    public AddRecitation_Transaction(String initSection, String initInstructor, String initDay, String initLocation, String initTA1, String initTA2, CSGManager initApp){
        section = initSection;
        instructor = initInstructor;
        day = initDay;
        location = initLocation;
        ta1 = initTA1;
        ta2 = initTA2;
        data = ((WorkspaceData)initApp.getDataComponent()).getRecitationData();
    }
    
    public void doTransaction() {
        data.addRecitation(section, instructor, day, location, ta1, ta2);
    }
    public void undoTransaction() {
        data.removeRecitation(section);
}
}
