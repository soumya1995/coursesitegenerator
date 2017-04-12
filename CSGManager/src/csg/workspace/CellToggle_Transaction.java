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
public class CellToggle_Transaction implements jTPS_Transaction {
    
    private String taEmail;
    private String taName;
    private String Key;
    private TAData data;
            
    public CellToggle_Transaction(String name, String email, String key, CSGManager initApp){
        taName = name;
        taEmail = email;
        Key = key;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
    }
    
    public void doTransaction() {
       data.toggleTAOfficeHours(Key, taName);
    }
    public void undoTransaction() {
       data.toggleTAOfficeHours(Key, taName);
    }
    
}
