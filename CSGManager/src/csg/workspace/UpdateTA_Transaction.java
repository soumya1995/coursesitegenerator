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
    
    private boolean oldTAUndergrad;
    private String oldTAEmail;
    private String newTAEmail;
    private String oldTAName;
    private boolean newTAUndergrad;
    private String newTAName;
    private TAData data;
    private TAController controller;
            
    public UpdateTA_Transaction(boolean oldUndergrad, String oldName, String oldEmail, boolean newUndergrad, String newName, String newEmail, CSGManager initApp){
        oldTAUndergrad = oldUndergrad;
        oldTAEmail = oldEmail;
        newTAEmail = newEmail;
        oldTAName = oldName;
        newTAUndergrad = newUndergrad;
        newTAName = newName;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
        controller = new TAController(initApp);
    }
    
    public void doTransaction() {
        data.removeTA(oldTAName);
        data.addTA(newTAUndergrad, newTAName, newTAEmail);
        controller.updateTAInOfficeHoursGrid(oldTAName, newTAName);
    }
    public void undoTransaction() {
        data.removeTA(newTAName);
        data.addTA(oldTAUndergrad, oldTAName, oldTAEmail);
        controller.updateTAInOfficeHoursGrid(newTAName, oldTAName);
    }
    
}
