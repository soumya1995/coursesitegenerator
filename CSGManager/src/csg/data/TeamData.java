/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Soumya
 */
public class TeamData {
    
     CSGManager app;
     ObservableList<Team> teams;
     
     public TeamData() {
        teams = FXCollections.observableArrayList();
    }
     
     public TeamData(CSGManager initApp) {
        app = initApp;
        teams = FXCollections.observableArrayList();
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }
    
    public void addTeam(String initName,String initColor, String initTextColor, String initLink){
        
        // MAKE THE TEAM
        Team team = new Team(initName, initColor, initTextColor, initLink);

        // ADD THE TEAM
         if (!containsTeam(initName, initColor, initTextColor, initLink)) {
            teams.add(team);
         }

        // SORT THE TEAMS
        Collections.sort(teams);
    }
    
    public boolean containsTeam(String initName,String initColor, String initTextColor, String initLink){
        
    for (Team t : teams) {
            if (t.getName().equals(initName))
                return true;
        }
        return false;
    }    
    
}
