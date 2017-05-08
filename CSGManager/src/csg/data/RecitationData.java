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

public class RecitationData {
    
     CSGManager app;
     ObservableList<Recitation> recitations;
    
    public RecitationData(){
        recitations = FXCollections.observableArrayList();
    } 
     
    public RecitationData(CSGManager initApp){
        app = initApp;
        recitations = FXCollections.observableArrayList();
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }
    
    public void addRecitation(String initSection,String initInstructor, String initDay, String initLocation, String initTA1, String initTA2){
        
        // MAKE THE RECITATION
        Recitation rec = new Recitation(initSection, initInstructor, initDay, initLocation, initTA1, initTA2);

        // ADD THE RECITATION
         if (!containsRecitation(initSection, initInstructor, initDay, initLocation, initTA1, initTA2)) {
            recitations.add(rec);
         }

        // SORT THE RECITATIONS
        Collections.sort(recitations);
    }

    public boolean containsRecitation(String section, String instructor, String day, String location, String ta1, String ta2) {
        
        for (Recitation rec : recitations) {
            if (rec.getSection().equals(section)) {
                return true;
            }
            if (rec.getDay().equals(day)) {
                return true;
            }
      
        }
        return false;
    }
    
    public void removeRecitation(String section) {
        for (Recitation r : recitations) {
            if (section.equals(r.getSection())) {
                recitations.remove(r);
                return;
            }
        }
    }
    
    public void reset(){
        recitations.clear();
    }
    
}
