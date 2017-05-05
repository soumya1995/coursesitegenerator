/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import csg.CSGManager;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.WorkspaceData;
import csg.file.TimeSlot;

/**
 *
 * @author Soumya
 */
public class DeleteTA_Transaction implements jTPS_Transaction {
    
    private Boolean taUndergrad;
    private String taEmail;
    private String taName;
    private TAData data;
    private TAController controller;
    private ArrayList<TimeSlot> officeHoursList;
    
    public DeleteTA_Transaction(Boolean undergrad, String name, String email, CSGManager initApp){
        taUndergrad = undergrad;
        taName = name;
        taEmail = email;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
        controller = new TAController(initApp);
        officeHoursList = TimeSlot.buildOfficeHoursList(data);
    }
    
    public void doTransaction() {
         data.removeTA(taName);
        controller.removeTAOfficeHours(taName);
    }
    public void undoTransaction() {
        data.addTA(taUndergrad, taName, taEmail);
        initOfficeHours();
    }
    private void initOfficeHours(){
        for(TimeSlot p: officeHoursList){
            if(p.getName().equals(taName)){
                data.addOfficeHoursReservation(p.getDay(), p.getTime(), p.getName());
            }
        }
    }
    
    
}