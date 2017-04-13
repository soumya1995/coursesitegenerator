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
public class TeamData {
    
     CSGManager app;
     ObservableList<Team> teams;
     
     public TeamData(CSGManager initApp) {
        app = initApp;
        teams = FXCollections.observableArrayList();
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }
    
}
