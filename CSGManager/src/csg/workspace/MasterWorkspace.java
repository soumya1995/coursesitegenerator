/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Soumya
 */

 


public class MasterWorkspace extends AppWorkspaceComponent{

    
    CSGManager app;
    private TabPane tabPane;
    private CourseWorkspace courseWorkspace;
    private TAWorkspace taWorkspace;
    private RecitationWorkspace recWorkspace;
    private ScheduleWorkspace scheduleWorkspace;
    private ProjectWorkspace projectWorkspace;
    
    public MasterWorkspace(CSGManager initApp){
        app = initApp;
        tabPane =new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        //PUT THE TABPANE IN THE WORKSPACE
        
          workspace = new BorderPane();
         ((BorderPane)workspace).setCenter(tabPane);    
        
        courseWorkspace = new CourseWorkspace(app, this);
        taWorkspace = new TAWorkspace(app, this);
        recWorkspace = new RecitationWorkspace(app, this);
        scheduleWorkspace = new ScheduleWorkspace(app, this);
        projectWorkspace = new ProjectWorkspace(app, this);
}
    
    public TabPane getTabPane(){
        return tabPane;
    }
    
    public CourseWorkspace getCourseWorkspace(){
        return courseWorkspace;
    }
    
    public TAWorkspace getTAWorkspace(){
        return taWorkspace;
    }
    
    public RecitationWorkspace getRecitationWorkspace(){
        return recWorkspace;
    }
    
    public ScheduleWorkspace getScheduleWorkspace(){
        return scheduleWorkspace;
    }
    
    public ProjectWorkspace getProjectWorkspace(){
        return projectWorkspace;
    }
    
    public Pane getWorkspace(){
        return workspace;
    }
    
     public void resetWorkspace(){
         courseWorkspace.resetWorkspace();
         taWorkspace.resetWorkspace();
         recWorkspace.resetWorkspace();
         scheduleWorkspace.resetWorkspace();
         projectWorkspace.resetWorkspace();
     }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        courseWorkspace.reloadWorkspace(dataComponent);
        taWorkspace.reloadWorkspace(dataComponent);
        recWorkspace.reloadWorkspace(dataComponent);
        scheduleWorkspace.reloadWorkspace(dataComponent);
        projectWorkspace.reloadWorkspace(dataComponent);
        
    }

  
}
