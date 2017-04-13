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
    
    public WorkspaceData(CSGManager initApp){
        
        app = initApp;
        courseData = new CourseData(app);
        taData = new TAData(app);
        recitationData = new RecitationData(app);
        scheduleData = new ScheduleData(app);
    }

    public CourseData getCourseData() {
        return courseData;
    }
    
    public TAData getTAData(){
        return taData;
    }
    
    public void resetData(){
      //  courseData.resetData();
        taData.resetData();
    }

    public RecitationData getRecitationData() {
        return recitationData;
    }

    public ScheduleData getScheduleData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
