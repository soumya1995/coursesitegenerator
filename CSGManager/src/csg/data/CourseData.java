/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import csg.CSGManagerProp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class CourseData {
    
    CSGManager app;
    
     ObservableList<Page> pages;
    
    public CourseData(CSGManager initApp){
        
        app = initApp;
        pages = FXCollections.observableArrayList();
        addRequiredPages();
    }
    
    
    public ObservableList<Page> getPages(){
        return pages;
    }
    
    public void addPage(boolean used, String title, String file, String script){
        
        pages.add(new Page(used, title, file, script));
    }
    
    public void addRequiredPages(){
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String homeText = props.getProperty(CSGManagerProp.COURSE_HOME_TEXT.toString());
        String syllabusText = props.getProperty(CSGManagerProp.SYLLABUS_TEXT.toString());
        String scheduleText = props.getProperty(CSGManagerProp.SCHEDULE_TEXT.toString());
        String hwsText = props.getProperty(CSGManagerProp.HWS_TEXT.toString());
        String projectsText = props.getProperty(CSGManagerProp.PROJECTS_TEXT.toString());
        
        addPage(false, "Home", "", "");
        addPage(false, "Sylabus", "", "");
        addPage(false, "Schedule", "", "");
        addPage(false, "HWs", "", "");
        addPage(false, "Projects", "", "");
    }
    
    public void resetData(){
        pages.clear();
    }
    
}
