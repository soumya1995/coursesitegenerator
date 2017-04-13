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
public class ScheduleData {
    
     CSGManager app;
     ObservableList<Schedule> schedules;

    public ScheduleData(CSGManager initApp) {
        app = initApp;
         schedules = FXCollections.observableArrayList();
    }

    public ObservableList<Schedule> getSchedules() {
        return schedules;
    }
    
}
