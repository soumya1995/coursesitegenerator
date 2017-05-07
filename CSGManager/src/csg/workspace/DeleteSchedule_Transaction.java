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
import java.util.logging.Level;
import java.util.logging.Logger;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class DeleteSchedule_Transaction implements jTPS_Transaction{
    
    private String type;
    private String date;
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    private ScheduleData data;
    
    public DeleteSchedule_Transaction(String type, String date, String time, String title, String topic, String link, String criteria, CSGManager initApp) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.title = title;
        this.topic = topic;
        this.link = link;
        this.criteria = criteria;
        data = ((WorkspaceData)initApp.getDataComponent()).getScheduleData();
    }
    
    public void doTransaction(){
        data.removeSchedule(type, date);
    }
    
    public void undoTransaction(){
        try {
            data.addSchedule(type, new SimpleDateFormat("MM/dd/yyyy").parse(date), time, title, topic, link, criteria);
        } catch (ParseException ex) {
            Logger.getLogger(DeleteSchedule_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
