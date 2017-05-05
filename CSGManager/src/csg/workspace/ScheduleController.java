/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import jtps.jTPS;

/**
 *
 * @author Soumya
 */
public class ScheduleController {
    
    static jTPS jTPS = new jTPS(); 
    CSGManager app;

    ScheduleController(CSGManager initApp) {
        app=initApp;
    }
    
}
