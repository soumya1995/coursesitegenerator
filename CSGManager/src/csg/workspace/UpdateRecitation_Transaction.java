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
public class UpdateRecitation_Transaction implements jTPS_Transaction{
    
    private String oldSection;
    private String oldInstructor;
    private String oldDay;
    private String oldLocation;
    private String oldTA1;
    private String oldTA2;
    private String newSection;
    private String newInstructor;
    private String newDay;
    private String newLocation;
    private String newTA1;
    private String newTA2;
    private RecitationData data;
    private RecitationController controller;
    
    public UpdateRecitation_Transaction(String oldSection, String oldInstructor, String oldDay, String oldLocation, String oldTA1, String oldTA2, String newSection, String newInstructor, String newDay, String newLocation, String newTA1, String newTA2, CSGManager initApp){
        this.oldSection = oldSection;
        this.oldInstructor = oldInstructor;
        this.oldDay = oldDay;
        this.oldLocation = oldLocation;
        this.oldTA1 = oldTA1;
        this.oldTA2 = oldTA2;
        this.newSection = newSection;
        this.newInstructor = newInstructor;
        this.newDay = newDay;
        this.newLocation = newLocation;
        this.newTA1 = newTA1;
        this.newTA2 = newTA2;
        data = ((WorkspaceData)initApp.getDataComponent()).getRecitationData();
        controller = new RecitationController(initApp);
        
    }
    
    public void doTransaction() {
        data.removeRecitation(oldSection);
        data.addRecitation(newSection, newInstructor, newDay, newLocation, newTA1, newTA2);
    }
    public void undoTransaction(){
        data.removeRecitation(newSection);
        data.addRecitation(oldSection, oldInstructor, oldDay, oldLocation, oldTA1, oldTA2);
    }
}
