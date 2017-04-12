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
public class ComboBox_Transaction implements jTPS_Transaction {
    
    private int oldStartTime;
    private int oldEndTime;
    private int newStartTime;
    private int newEndTime;
    private TAData data;
    private TAController controller;
    private TAWorkspace workspace;
    ArrayList<TimeSlot> officeHoursList;
            
    public ComboBox_Transaction(int start, int end, ArrayList<TimeSlot> officeHours, CSGManager initApp, TAWorkspace work){
        newStartTime = start;
        newEndTime = end;
        data = ((WorkspaceData)initApp.getDataComponent()).getTAData();
        oldStartTime = data.getStartHour();
        oldEndTime = data.getEndHour();
        controller = new TAController(initApp);
        officeHoursList = officeHours;
        workspace = work;
    }
    
    public void doTransaction() {
        
         data.initOfficeHours(newStartTime, newEndTime);
         controller.reAssignOfficeHoursDueToStartEndTimeChange(officeHoursList);
         workspace.getStartTimeBox().getSelectionModel().select(data.getStartHour());
         workspace.getEndTimeBox().getSelectionModel().select(data.getEndHour());
    }
    public void undoTransaction() {
       
       data.initOfficeHours(oldStartTime, oldEndTime);
       controller.reAssignOfficeHoursDueToStartEndTimeChange(officeHoursList);
       workspace.getStartTimeBox().getSelectionModel().select(data.getStartHour());
       workspace.getEndTimeBox().getSelectionModel().select(data.getEndHour());
    }
    
}
