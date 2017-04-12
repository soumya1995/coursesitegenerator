/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
