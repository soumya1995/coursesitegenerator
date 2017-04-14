/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManager;
import csg.workspace.CourseWorkspace;
import csg.workspace.MasterWorkspace;

/**
 *
 * @author Soumya
 */
public class CourseStyle {
    
     CSGManager app;
     
     // THESE ARE THE HEADERS
    public static String CLASS_HEADER_LABEL = "header_label";
    public static String CLASS_OUTER_VBOX_PANE = "outer_plain_pane";
    public static String CLASS_INNER_VBOX_PANE = "inner_plain_pane";
    public static String CLASS_TAB = "tab";
    public static String CLASS_SUBJECT_LABEL = "subject_label";
    public static String CLASS_SUBJECT_COMBOBOX = "subject_combobox";
    public static String CLASS_NUMBER_LABEL = "number_label";
    public static String CLASS_NUMBER_COMBOBOX = "number_combobox";
     public static String CLASS_SUB_HEADER_LABEL = "subheader_label";
    public static String CLASS_TAGS = "tags";
    
    public CourseStyle(CSGManager initApp){
        
        app = initApp;
        initCourseWorkspaceStyle();
    }

    private void initCourseWorkspaceStyle() {
        
        CourseWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        ((MasterWorkspace)app.getWorkspaceComponent()).getTabPane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getCourseTab().getStyleClass().add(CLASS_TAB);
        workspaceComponent.getCoursePane().getStyleClass().add(CLASS_OUTER_VBOX_PANE);
        workspaceComponent.getCourseInfoPane().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getCourseInfoLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getSubjectLabel().getStyleClass().add(CLASS_SUBJECT_LABEL);
        workspaceComponent.getSubjectComboBox().getStyleClass().add(CLASS_SUBJECT_COMBOBOX);
        workspaceComponent.getNumberLabel().getStyleClass().add(CLASS_NUMBER_LABEL);
        workspaceComponent.getNumberComboBox().getStyleClass().add(CLASS_NUMBER_COMBOBOX);
        workspaceComponent.getTemplate().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getSiteTemplateLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getStyle().getStyleClass().add(CLASS_INNER_VBOX_PANE);
        workspaceComponent.getPageStyleLabel().getStyleClass().add(CLASS_SUB_HEADER_LABEL);
        workspaceComponent.getSubjectLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getNumberLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getSemesterLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getYearLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getTitleLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getInstructorLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getHomeLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getExportLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getDirLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getPagesLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getSchoolBannerLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getLeftFooterLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getRightFooterLabel().getStyleClass().add(CLASS_TAGS);
        workspaceComponent.getStylesheetLabel().getStyleClass().add(CLASS_TAGS);
    }
    
}
