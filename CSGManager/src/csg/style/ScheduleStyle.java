/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManager;
import static csg.style.RecitationStyle.CLASS_HEADER_LABEL;
import static csg.style.RecitationStyle.CLASS_INNER_VBOX_PANE;
import static csg.style.RecitationStyle.CLASS_OUTER_VBOX_PANE;
import static csg.style.RecitationStyle.CLASS_TAB;
import csg.workspace.MasterWorkspace;
import csg.workspace.RecitationWorkspace;
import csg.workspace.ScheduleWorkspace;

/**
 *
 * @author Soumya
 */
public class ScheduleStyle {
    
    CSGManager app;
    
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_OUTER_VBOX_PANE = "outer_plain_pane";
    public static String CLASS_INNER_VBOX_PANE = "inner_plain_pane";
    public static String CLASS_TAB = "tab";

    public ScheduleStyle(CSGManager initApp) {
        app = initApp;
        initRecitationWorkspaceStyle();
    }

    private void initRecitationWorkspaceStyle() {
        ScheduleWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        ((MasterWorkspace)app.getWorkspaceComponent()).getTabPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getScheduleTab().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getSchedulePane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getItemsBox().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getCalendarBox().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getScheduleHeader().getStyleClass().add(CLASS_HEADER_LABEL);
    }
    
}
