/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.CSGManagerProp;
import static csg.CSGManagerProp.MISSING_DAY_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_DAY_NAME_TITLE;
import static csg.CSGManagerProp.MISSING_INS_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_INS_NAME_TITLE;
import static csg.CSGManagerProp.MISSING_LOC_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_LOC_NAME_TITLE;
import static csg.CSGManagerProp.MISSING_SECTION_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_SECTION_NAME_TITLE;
import static csg.CSGManagerProp.SAME_TA_NAME_MESSAGE;
import static csg.CSGManagerProp.SAME_TA_NAME_TITLE;
import static csg.CSGManagerProp.SECTION_DAY_NOT_UNIQUE_MESSAGE;
import static csg.CSGManagerProp.SECTION_DAY_NOT_UNIQUE_TITLE;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.WorkspaceData;
import static csg.workspace.TAController.jTPS;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class RecitationController {
    
    CSGManager app;
    static jTPS jTPS = new jTPS(); 

    public RecitationController(CSGManager initApp) {
        app = initApp;
    }

   public void handleAddRecitation() {
        
       // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        RecitationWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
        TextField sectionTextField = workspace.getSectionTextField();
        TextField instructorTextField = workspace.getInstructorTextField();
        TextField dayTextField = workspace.getDayTextField();
        TextField locationTextField = workspace.getLocationTextField();
        ComboBox ta1ComboBox =workspace.getTa1ComboBox();
        ComboBox ta2ComboBox =workspace.getTa2ComboBox();
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String day = dayTextField.getText();
        String location = locationTextField.getText();
        String ta1 = (String)ta1ComboBox.getSelectionModel().getSelectedItem();
        String ta2 = (String)ta2ComboBox.getSelectionModel().getSelectedItem();
        
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        RecitationData data = ((WorkspaceData)app.getDataComponent()).getRecitationData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (section.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (instructor.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INS_NAME_TITLE), props.getProperty(MISSING_INS_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (day.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_NAME_TITLE), props.getProperty(MISSING_DAY_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (location.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        else if (ta1.equals(ta2)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAME_TA_NAME_TITLE), props.getProperty(SAME_TA_NAME_MESSAGE));            
        }
       
        
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsRecitation(section, instructor, day, location, ta1, ta2)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SECTION_DAY_NOT_UNIQUE_TITLE), props.getProperty(SECTION_DAY_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            AddRecitation_Transaction addRecitation_Transaction = new AddRecitation_Transaction(section, instructor, day, location, ta1, ta2, app);
            jTPS.addTransaction(addRecitation_Transaction);
            
            //data.addTA(true, name, email);
            
            
            
            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTextField.setText("");
            locationTextField.setText("");
            ta1ComboBox.getSelectionModel().selectFirst();
            ta2ComboBox.getSelectionModel().selectFirst();
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
   public void handleUpdateRecitation(){
   
        RecitationWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
        TableView table = workspace.getTable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            Recitation r = (Recitation)selectedItem;
            String section = r.getSection();
            String instructor = r.getInstructor();
            String day = r.getDay();
            String location = r.getLocation();
            String ta1 = r.getTa1();
            String ta2 = r.getTa2();
            
            TextField sectionTextField = workspace.getSectionTextField();
            TextField instructorTextField = workspace.getInstructorTextField();
            TextField dayTextField = workspace.getDayTextField();
            TextField locationTextField = workspace.getLocationTextField();
            ComboBox ta1ComboBox =workspace.getTa1ComboBox();
            ComboBox ta2ComboBox =workspace.getTa2ComboBox();
            Button button = workspace.getAddButton();
            
            //SET TEXT FIELDS WITH APPROPRIATE NAME AND EMAIL
            sectionTextField.setText(section);
            instructorTextField.setText(instructor);
            dayTextField.setText(day);
            locationTextField.setText(location);
            ta1ComboBox.getSelectionModel().select(ta1);
            ta2ComboBox.getSelectionModel().select(ta2);
            
            button.setOnAction(e -> {
                updateRecitation();
        });
            
        }
   }   
   
   public void updateRecitation(){
        RecitationWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
        TableView table = workspace.getTable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        
            TextField sectionTextField = workspace.getSectionTextField();
            TextField instructorTextField = workspace.getInstructorTextField();
            TextField dayTextField = workspace.getDayTextField();
            TextField locationTextField = workspace.getLocationTextField();
            ComboBox ta1ComboBox = workspace.getTa1ComboBox();
            ComboBox ta2ComboBox = workspace.getTa2ComboBox();
            
            String section = sectionTextField.getText();
            String instructor = instructorTextField.getText();
            String day = dayTextField.getText();
            String location = locationTextField.getText();
            String ta1 = (String)ta1ComboBox.getSelectionModel().getSelectedItem();
            String ta2 = (String)ta2ComboBox.getSelectionModel().getSelectedItem();
            // GET THE TA
            Recitation rec = (Recitation)selectedItem;
            String oldSection = rec.getSection();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        RecitationData data = ((WorkspaceData)app.getDataComponent()).getRecitationData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
         // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (section.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (instructor.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INS_NAME_TITLE), props.getProperty(MISSING_INS_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (day.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_NAME_TITLE), props.getProperty(MISSING_DAY_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (location.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        else if (ta1.equals(ta2)){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAME_TA_NAME_TITLE), props.getProperty(SAME_TA_NAME_MESSAGE));            
        }
        else {
            // ADD THE NEW TA TO THE DATA
            UpdateRecitation_Transaction updateRecitation_Transaction = new UpdateRecitation_Transaction(oldSection, rec.getInstructor(), rec.getDay(), rec.getLocation(), rec.getTa1(), rec.getTa2(),section, instructor, day, location, ta1, ta2, app);
            jTPS.addTransaction(updateRecitation_Transaction);
            
            //data.addTA(true, name, email);
            
            
            
            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTextField.setText("");
            locationTextField.setText("");
            ta1ComboBox.getSelectionModel().selectFirst();
            ta2ComboBox.getSelectionModel().selectFirst();
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
       
   }
   
   public void handleClearRecitation(){
        RecitationWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
            TextField sectionTextField = workspace.getSectionTextField();
            TextField instructorTextField = workspace.getInstructorTextField();
            TextField dayTextField = workspace.getDayTextField();
            TextField locationTextField = workspace.getLocationTextField();
            ComboBox ta1ComboBox = workspace.getTa1ComboBox();
            ComboBox ta2ComboBox = workspace.getTa2ComboBox();
        
       
            // CLEAR THE TEXT FIELDS
            sectionTextField.setText("");
            instructorTextField.setText("");
            dayTextField.setText("");
            locationTextField.setText("");
            ta1ComboBox.getSelectionModel().selectFirst();
            ta2ComboBox.getSelectionModel().selectFirst();
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionTextField.requestFocus();
           
    }
   
   /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyEvent event) {
        // DID THE USER PRESS THE DELETE KEY?
        KeyCode code = event.getCode();
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
            TableView taTable = workspace.getTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Recitation r = (Recitation)selectedItem;
                RecitationData data = ((WorkspaceData)app.getDataComponent()).getRecitationData();
                
                DeleteRecitation_Transaction DeleteRecitation_Transaction = new DeleteRecitation_Transaction(r.getSection(),r.getInstructor(),r.getDay(),r.getLocation(),r.getTa1(),r.getTa2(), app);
                jTPS.addTransaction(DeleteRecitation_Transaction);
                
               // data.removeRecitation(r.getSection());
               
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
        
        //DID THE USER PRESS CTRL+Z??
        final KeyCombination CtrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
        final KeyCombination CtrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
        if (code == KeyCode.Z && event.isShortcutDown()) {
            jTPS.undoTransaction();
        }
        if (CtrlY.match(event)) {
            jTPS.doTransaction();
        }
    }
   
   /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
}
