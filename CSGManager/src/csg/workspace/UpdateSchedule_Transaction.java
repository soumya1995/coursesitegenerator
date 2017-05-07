/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.ScheduleData;
import csg.data.WorkspaceData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class UpdateSchedule_Transaction implements jTPS_Transaction{
    
    private String oldType;
    private String oldDate;
    private String oldTime;
    private String oldTitle;
    private String oldTopic;
    private String oldLink;
    private String oldCriteria;
    private String newType;
    private String newDate;
    private String newTime;
    private String newTitle;
    private String newTopic;
    private String newLink;
    private String newCriteria;
    private ScheduleData data;
            
    public UpdateSchedule_Transaction(String oldType, String oldDate, String oldTime, String oldTitle, String oldTopic, String oldLink, String oldCriteria, String newType, String newDate, String newTime, String newTitle, String newTopic, String newLink, String newCriteria, CSGManager initApp) {
        this.oldType = oldType;
        this.oldDate = oldDate;
        this.oldTime = oldTime;
        this.oldTitle = oldTitle;
        this.oldTopic = oldTopic;
        this.oldCriteria = oldCriteria;
        this.newType = newType;
        this.newDate = newDate;
        this.newTime = newTime;
        this.newTitle = newTitle;
        this.newTopic = newTopic;
        this.newLink = newLink;
        this.newCriteria = newCriteria;
        data = ((WorkspaceData)initApp.getDataComponent()).getScheduleData();
    }
    
    public void doTransaction(){
        data.removeSchedule(oldType, oldDate);
        try {
            data.addSchedule(newType, new SimpleDateFormat("MM/dd/yyyy").parse(newDate), newTitle, newTopic, newTime, newLink, newCriteria);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateSchedule_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void undoTransaction(){
        data.removeSchedule(newType, newDate);
        try {
            data.addSchedule(oldType, new SimpleDateFormat("MM/dd/yyyy").parse(oldDate), newTitle, newTopic, newTime, newLink, newCriteria);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateSchedule_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
