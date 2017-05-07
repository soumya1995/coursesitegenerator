/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import csg.workspace.MasterWorkspace;
import csg.workspace.ScheduleWorkspace;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Soumya
 */
public class ScheduleData {
    
     CSGManager app;
     ObservableList<Schedule> schedules;
     
     Date startDate;
     Date endDate;
    
    public ScheduleData() {
        schedules = FXCollections.observableArrayList();
    } 
     
    public ScheduleData(CSGManager initApp) {
        app = initApp;
        schedules = FXCollections.observableArrayList();
         try {
             startDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2000");
         } catch (ParseException ex) {
             Logger.getLogger(ScheduleData.class.getName()).log(Level.SEVERE, null, ex);
         }
        //startDate = new Date(100, 1, 1);
        endDate = new Date();
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
    }
    
    public Date getStartDate(){
        return startDate;
    }
    
    public Date getEndDate(){
        return endDate;
    }
    
    public void initDate(String startDate, String endDate) throws ParseException{
        
        Date initStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
        Date initEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
        
        if(initStartDate.compareTo(initEndDate)<=0){
            //THESE ARE VALID DATES
            initScheduleDate(initStartDate, initEndDate);
        }
    }
    
    public void initScheduleDate(Date initStartDate, Date initEndDate){
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startDate = initStartDate;
        endDate = initEndDate;
        
        
    }
    
    public void addSchedule(String initType, Date initDate, String initTitle, String initTopic, String initTime, String initLink, String initCriteria){
        
        if(initDate.compareTo(startDate)>=0 && initDate.compareTo(endDate)<=0){
        
        // MAKE THE SCHEDULE
        Schedule schedule = new Schedule(initType, initDate, initTitle, initTopic, initTime, initLink, initCriteria);

        // ADD THE SCHEDULE
         if (!containsSchedule(initDate, initType)) {
            schedules.add(schedule);
         }

        // SORT THE SCHEDULES
        Collections.sort(schedules);
        }
    }
    
    public boolean containsSchedule(Date initDate, String initType){
        
        for(Schedule s:schedules){
            Format df = new SimpleDateFormat("MM/dd/yyyy");
            if(s.getDate().compareTo(df.format(initDate))==0 && s.getType().equals(initType))
                return true;
        }
        
        return false;
    }
    
    public void removeSchedule(String type, String date) {
        for (Schedule r : schedules) {
            if (type.equals(r.getType()) && date.equals(r.getDate())) {
                schedules.remove(r);
                return;
            }
        }
    }
    
    public ArrayList<Schedule> getExcessSchedules(Date start, Date end) throws ParseException{
        ArrayList<Schedule> excessSchedules = new ArrayList<>();
        for(Schedule s: schedules){
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(s.getDate());
            if(date.compareTo(start)<0 || date.compareTo(end)>0)
                excessSchedules.add(s);
        }
        
        return excessSchedules;
    }
    
    public void deleteFromSchedules(Date start, Date end) throws ParseException{
        for(Schedule s: schedules){
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(s.getDate());
            if(date.compareTo(start)<0 || date.compareTo(end)>0)
                schedules.remove(s);
        }
    }
    
    public void addToSchedules(ArrayList<Schedule> list){
        for(Schedule s: list){
            schedules.add(s);
        }
         Collections.sort(schedules);
    }
}
