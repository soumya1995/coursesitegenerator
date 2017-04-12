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
    
    public WorkspaceData(CSGManager initApp){
        
        app = initApp;
        courseData = new CourseData(app);
        taData = new TAData(app);
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
    
}
