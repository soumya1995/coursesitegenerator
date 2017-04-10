/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManagerApp;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Soumya
 */

 


public class MasterWorkspace extends AppWorkspaceComponent{

    
    CSGManagerApp app;
    private TabPane tabPane;
    private CourseWorkspace courseWorkspace;
    
    public MasterWorkspace(CSGManagerApp initApp){
        app = initApp;
        tabPane =new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        //PUT THE TABPANE IN THE WORKSPACE
          workspace = new BorderPane();
         ((BorderPane)workspace).setCenter(tabPane);    
        
        courseWorkspace = new CourseWorkspace(app, this);
}
    
    public TabPane getTabPane(){
        return tabPane;
    }
    
    public CourseWorkspace getCourseWorkspace(){
        return courseWorkspace;
    }
    
     public void resetWorkspace(){
         courseWorkspace.resetWorkspace();
     }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        courseWorkspace.reloadWorkspace(dataComponent);
    }

  
}
