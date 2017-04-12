/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import jtps.jTPS_Transaction;
import csg.CSGManager;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.WorkspaceData;

/**
 *
 * @author Soumya
 */
public class UpdateTA_Transaction implements jTPS_Transaction {
    
    private String oldTAEmail;
    private String newTAEmail;
    private String oldTAName;
    private String newTAName;
    private TAData data;
    private TAController controller;
            
    public UpdateTA_Transaction(String oldName, String oldEmail, String newName, String newEmail, CSGManager initApp){
        oldTAEmail = oldEmail;
        newTAEmail = newEmail;
        oldTAName = oldName;
        newTAName = newName;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
        controller = new TAController(initApp);
    }
    
    public void doTransaction() {
        data.removeTA(oldTAName);
        data.addTA(newTAName, newTAEmail);
        controller.updateTAInOfficeHoursGrid(oldTAName, newTAName);
    }
    public void undoTransaction() {
        data.removeTA(newTAName);
        data.addTA(oldTAName, oldTAEmail);
        controller.updateTAInOfficeHoursGrid(newTAName, oldTAName);
    }
    
}
