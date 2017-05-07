/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.data.Schedule;
import csg.data.ScheduleData;
import csg.data.TAData;
import csg.data.WorkspaceData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jtps.jTPS_Transaction;

/**
 *
 * @author Soumya
 */
public class ComboDateBox_Transaction implements jTPS_Transaction  {
    
    private String oldStartDate;
    private String oldEndDate;
    private String newStartDate;
    private String newEndDate;
    private ScheduleData data;
    private ScheduleWorkspace workspace;
    private ArrayList<Schedule> schedules;
    
    public ComboDateBox_Transaction(Date start, Date end, ArrayList<Schedule>schedules ,CSGManager initApp){
        newStartDate = new SimpleDateFormat("MM/dd/yyyy").format(start);
        newEndDate = new SimpleDateFormat("MM/dd/yyyy").format(end);
        data = ((WorkspaceData)initApp.getDataComponent()).getScheduleData();
        oldStartDate = new SimpleDateFormat("MM/dd/yyyy").format(data.getStartDate());
        oldEndDate = new SimpleDateFormat("MM/dd/yyyy").format(data.getEndDate());
        workspace = ((MasterWorkspace)initApp.getWorkspaceComponent()).getScheduleWorkspace();
        this.schedules = schedules;
    }
    
    public void doTransaction(){
        try{
        data.initDate(newStartDate, newEndDate);
        data.deleteFromSchedules(new SimpleDateFormat("MM/dd/yyyy").parse(newStartDate), new SimpleDateFormat("MM/dd/yyyy").parse(newEndDate));
        }
        catch(Exception e){
            
        }
        LocalDate startDate = data.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = data.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        workspace.getStartBox().setValue(startDate);
        workspace.getEndBox().setValue(endDate);
    }
    
    public void undoTransaction(){
        try{
        data.initDate(oldStartDate, oldEndDate);
        data.addToSchedules(schedules);
        }
        catch(Exception e){
            
        }
        LocalDate startDate = data.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = data.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        workspace.getStartBox().setValue(startDate);
        workspace.getEndBox().setValue(endDate);
    }
}
