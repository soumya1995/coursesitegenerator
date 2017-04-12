/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class Page {
    
    private BooleanProperty used;
    private StringProperty title;
    private StringProperty fileName;
    private StringProperty script;
    
    public Page(boolean used, String title, String file, String script){
        this.used = new SimpleBooleanProperty(used);
        this.title = new SimpleStringProperty(title);
        this.fileName = new SimpleStringProperty(file);
        this.script = new SimpleStringProperty(script);
    }

    public BooleanProperty isUsed() {
        return used;
    }

    public void setUsed(BooleanProperty used) {
        this.used = used;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName); 
    }

    public String getScript() {
        return script.get();
    }

    public void setScript(String script) {
        this.script.set(script);
    }
    
    
}