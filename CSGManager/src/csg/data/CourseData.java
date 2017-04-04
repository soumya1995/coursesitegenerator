/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Soumya
 */
public class CourseData {
    
    ObservableList<Page> pages = FXCollections.observableArrayList();
    
    public ObservableList<Page> getPages(){
        return pages;
    }
    
}
