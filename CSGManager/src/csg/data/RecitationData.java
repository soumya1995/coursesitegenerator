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

public class RecitationData {
    
     CSGManager app;
     ObservableList<Recitation> recitations;
    
    public RecitationData(CSGManager initApp){
        app = initApp;
        recitations = FXCollections.observableArrayList();
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }
    
}
