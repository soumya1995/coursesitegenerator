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
public class StudentData {
    
     CSGManager app;
     ObservableList<Student> students;
     
     public StudentData(CSGManager initApp) {
        app = initApp;
        students = FXCollections.observableArrayList();
    }

    public ObservableList<Student> getStudents() {
        return students;
    }
    
}
