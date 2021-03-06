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
public class AddTA_Transaction implements jTPS_Transaction {
    
    private boolean taUndergrad;
    private String taEmail;
    private String taName;
    private TAData data;
            
    public AddTA_Transaction(boolean undergrad, String name, String email, CSGManager initApp){
        taUndergrad = undergrad;
        taName = name;
        taEmail = email;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
    }
    
    public void doTransaction() {
        data.addTA(taUndergrad, taName, taEmail);
    }
    public void undoTransaction() {
        data.removeTA(taName);
    }
    
}
