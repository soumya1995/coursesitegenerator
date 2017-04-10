/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGManagerApp;
import djf.AppTemplate;
import djf.components.AppStyleComponent;

/**
 *
 * @author Soumya
 */
public class MasterStyle extends AppStyleComponent {
    
    private CSGManagerApp app;
    private CourseStyle courseStyle;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public MasterStyle(CSGManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        courseStyle = new CourseStyle(app);
    }

}
