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
public class AddSchedule_Transaction implements jTPS_Transaction{
    
    private String type;
    private String date;
    private String time;
    private String title;
    private String topic;
    private String link;
    private String criteria;
    private ScheduleData data;
    
    public AddSchedule_Transaction(String initType, Date initDate, String initTime, String initTitle, String initTopic, String initLink, String initCriteria, CSGManager initApp){
        type = initType;
        date = new SimpleDateFormat("MM/dd/yyyy").format(initDate);
        time = initTime;
        title = initTitle;
        topic = initTopic;
        link = initLink;
        criteria = initCriteria;
        data = ((WorkspaceData)initApp.getDataComponent()).getScheduleData();
    }
    
    public void doTransaction(){
        try {
            Date scheduleDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
            data.addSchedule(type, scheduleDate, title, topic, time, link, criteria);
        } catch (ParseException ex) {
            Logger.getLogger(AddSchedule_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void undoTransaction(){
        data.removeSchedule(type, date);
    }
}

