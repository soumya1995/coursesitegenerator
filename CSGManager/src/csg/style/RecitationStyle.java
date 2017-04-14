/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManager;
import csg.workspace.CourseWorkspace;
import csg.workspace.MasterWorkspace;
import csg.workspace.RecitationWorkspace;

/**
 *
 * @author Soumya
 */
public class RecitationStyle {
    
    CSGManager app;
    
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_SUB_HEADER_LABEL = "subheader_label";
    public static String CLASS_TAGS = "tags";
    public static String CLASS_BUTTON = "button";
    public static String CLASS_OUTER_VBOX_PANE = "outer_plain_pane";
    public static String CLASS_INNER_VBOX_PANE = "inner_plain_pane";
    public static String CLASS_TAB = "tab";
    
    public RecitationStyle(CSGManager initApp){
        app = initApp;
        initRecitationWorkspaceStyle();
    }
    
    public void initRecitationWorkspaceStyle(){
        RecitationWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
        ((MasterWorkspace)app.getWorkspaceComponent()).getTabPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getRecitationTab().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getRecitationPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getAddEditBox().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getRecitationHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getAddEditLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getSectionLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getInstructorLabel().getStyleClass().add(CLASS_TAGS); 
        workspaceComponent.getDayLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getLocationLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getTa1Label().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getTa2Label().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getAddButton().getStyleClass().add(CLASS_BUTTON);
        workspaceComponent.getClearButton().getStyleClass().add(CLASS_BUTTON);
    }
}
