/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import djf.components.AppDataComponent;

/**
 *
 * @author Soumya
 */
public class WorkspaceData implements AppDataComponent{
    
    CSGManager app;
    private CourseData courseData;
    private TAData taData;
    private RecitationData recitationData;
    private ScheduleData scheduleData;
    private TeamData teamData;
    private StudentData studentData;
    
    public WorkspaceData(){
        
        courseData = new CourseData();
        taData = new TAData();
        recitationData = new RecitationData();
        scheduleData = new ScheduleData();
        teamData = new TeamData();
        studentData = new StudentData(this);
    }
    
    public WorkspaceData(CSGManager initApp){
        
        app = initApp;
        courseData = new CourseData(app);
        taData = new TAData(app);
        recitationData = new RecitationData(app);
        scheduleData = new ScheduleData(app);
        teamData = new TeamData(app);
        studentData = new StudentData(app);
    }
    

    public CourseData getCourseData() {
        return courseData;
    }
    
    public TAData getTAData(){
        return taData;
    }
    

    public RecitationData getRecitationData() {
        return recitationData;
    }
    
    public ScheduleData getScheduleData() {
        return scheduleData;
    }
    
    public void resetData(){
      //  courseData.resetData();
        taData.resetData();
    }
   

    public TeamData getTeamData() {
        return teamData;
    }

    public StudentData getStudentData() {
        return studentData;
    }

    
    
}
