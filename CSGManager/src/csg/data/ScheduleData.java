/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import csg.workspace.MasterWorkspace;
import csg.workspace.ScheduleWorkspace;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

    public ScheduleData(CSGManager initApp) {
        app = initApp;
        schedules = FXCollections.observableArrayList();
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
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
        
        ScheduleWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        workspaceComponent.resetWorkspace();
    }
    
    public void addSchedule(String initType, Date initDate, String initTitle, String initTopic, String initTime, String initLink, String initCriteria){
        
        if(initDate.compareTo(startDate)>=0 && initDate.compareTo(endDate)<=0){
        
        // MAKE THE SCHEDULE
        Schedule schedule = new Schedule(initType, initDate, initTitle, initTopic, initTime, initLink, initCriteria);

        // ADD THE RECITATION
         if (!containsSchedule(initDate)) {
            schedules.add(schedule);
         }

        // SORT THE RECITATIONS
        Collections.sort(schedules);
        }
    }
    
    public boolean containsSchedule(Date initDate){
        
        for(Schedule s:schedules){
            if(s.getDate().compareTo(initDate)==0)
                return true;
        }
        
        return false;
    }
    
}
