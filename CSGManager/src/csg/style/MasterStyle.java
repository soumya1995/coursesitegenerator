/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManager;
import csg.workspace.MasterWorkspace;
import djf.AppTemplate;
import djf.components.AppStyleComponent;

/**
 *
 * @author Soumya
 */
public class MasterStyle extends AppStyleComponent {
    
    private CSGManager app;
    private CourseStyle courseStyle;
    private TAStyle taStyle;
    private RecitationStyle recStyle;
    private ScheduleStyle scheduleStyle;
    private ProjectStyle projectStyle;
    
    
    public static String CLASS_TAB_PANE = "tab_pane";
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public MasterStyle(CSGManager initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        courseStyle = new CourseStyle(app);
        taStyle = new TAStyle(app);
        recStyle = new RecitationStyle(app);
        scheduleStyle = new ScheduleStyle(app);
        projectStyle = new ProjectStyle(app);
        initMasterStyle();
    }

    public CourseStyle getCourseStyle() {
        return courseStyle;
    }

    public TAStyle getTAStyle() {
        return taStyle;
    }
    
    public RecitationStyle getRecitationStyle() {
        return recStyle;
    }
    
    public ScheduleStyle getScheduleStyle() {
        return scheduleStyle;
    }
    
    public ProjectStyle getProjectStyle() {
        return projectStyle;
    }

    private void initMasterStyle() {
        MasterWorkspace workspaceComponent = (MasterWorkspace)app.getWorkspaceComponent();
         workspaceComponent.getTabPane().getStyleClass().add(CLASS_TAB_PANE);
    }
    
    

}
