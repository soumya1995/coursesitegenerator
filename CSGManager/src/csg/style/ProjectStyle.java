/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManager;
import static csg.style.ScheduleStyle.CLASS_HEADER_LABEL;
import static csg.style.ScheduleStyle.CLASS_INNER_VBOX_PANE;
import static csg.style.ScheduleStyle.CLASS_OUTER_VBOX_PANE;
import static csg.style.ScheduleStyle.CLASS_TAB;
import csg.workspace.MasterWorkspace;
import csg.workspace.ProjectWorkspace;
import csg.workspace.ScheduleWorkspace;

/**
 *
 * @author Soumya
 */
public class ProjectStyle {
    
    CSGManager app;
    
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_OUTER_VBOX_PANE = "outer_plain_pane";
    public static String CLASS_INNER_VBOX_PANE = "inner_plain_pane";
    public static String CLASS_TAB = "tab";
    public static String CLASS_TAGS = "tags";
    public static String CLASS_BUTTON = "button";
    public static String CLASS_SUB_HEADER_LABEL = "subheader_label";
    

    public ProjectStyle(CSGManager initApp) {
        app = initApp;
        initRecitationWorkspaceStyle();
    }

    private void initRecitationWorkspaceStyle() {
        ProjectWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        ((MasterWorkspace)app.getWorkspaceComponent()).getTabPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getProjectTab().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getProjectPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getTeamBox().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getStudentBox().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getProjectHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getTeamLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getStudentLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getAddEditLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getAddEditLabel2().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getTeamLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getNameLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getColorLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getTextColorLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getLinkLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getFirstNameLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getLastNameLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getTeamComboLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getRoleLabel().getStyleClass().add(CLASS_TAGS);
    }
}
