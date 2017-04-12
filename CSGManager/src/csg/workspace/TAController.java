package csg.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import djf.ui.AppGUI;
import static csg.CSGManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import properties_manager.PropertiesManager;
import csg.CSGManager;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.style.TAStyle;
import static csg.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import csg.workspace.TAWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.Event;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.CSGManagerProp;
import static csg.data.TAData.MAX_END_HOUR;
import static csg.data.TAData.MIN_START_HOUR;
import csg.data.WorkspaceData;
import csg.file.TimeSlot;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @co-author Soumya Das
 * @version 1.0
 */


public class TAController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    static jTPS jTPS = new jTPS(); 
    CSGManager app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(CSGManager initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        else if(validateEmail(email)==false){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_INVALID), props.getProperty(TA_EMAIL_INVALID_MESSAGE)); 
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            AddTA_Transaction addTA_Transaction = new AddTA_Transaction(name, email, app);
            jTPS.addTransaction(addTA_Transaction);
            
            data.addTA(name, email);
            
            
            
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    public boolean validateEmail(String email){
        final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return (Pattern.compile(EMAIL_REGEX)).matcher(email).matches();
        
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
            TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
                
                DeleteTA_Transaction DeleteTA_Transaction = new DeleteTA_Transaction(taName, ta.getEmail(), app);
                jTPS.addTransaction(DeleteTA_Transaction);
                
                data.removeTA(taName);
                
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                removeTAOfficeHours(taName);
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
    
    public void removeTAOfficeHours(String taName){
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(taName)
                    || (label.getText().contains(taName + "\n"))
                    || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                    }
                }
                markWorkAsEdited();
    }

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
            String cellKey = pane.getId();
            
                        
             CellToggle_Transaction CellToggle_Transaction = new CellToggle_Transaction(taName, ta.getEmail(), cellKey, app);
             jTPS.addTransaction(CellToggle_Transaction);
            
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
         //   data.toggleTAOfficeHours(cellKey, taName);
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    public void handleClearTA(){
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        Button button = workspace.getAddButton();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        
        //CHANGING THE BUTTON TEXT
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
            button.setText(addButtonText);
        
         //CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            nameTextField.requestFocus();
           
    }
    public void handleUpdateTA(){
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            String email = ta.getEmail();
            
            
            TextField nameTextField = workspace.getNameTextField();
            TextField emailTextField = workspace.getEmailTextField();
            Button button = workspace.getAddButton();
            
            //SET TEXT FIELDS WITH APPROPRIATE NAME AND EMAIL
            nameTextField.setText(taName);
            emailTextField.setText(email);
            
            //CHANGING THE BUTTON TEXT
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String updateButtonText = props.getProperty(CSGManagerProp.UPDATE_BUTTON_TEXT.toString());
            button.setText(updateButtonText);
            
            button.setOnAction(e -> {
                updateTA();
        });
            
        }
    }
    
    public void updateTA(){
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String oldTAName = ta.getName();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        else if(validateEmail(email)==false){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_EMAIL_INVALID), props.getProperty(TA_EMAIL_INVALID_MESSAGE)); 
        }
        //UPDATE THE TA NAME AND EMAIL
        else{
            
                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");
            
                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();
            
                // WE'VE CHANGED STUFF
                 markWorkAsEdited();
            
            UpdateTA_Transaction updateTA_Transaction = new UpdateTA_Transaction(oldTAName, ta.getEmail(), name, email, app);
            jTPS.addTransaction(updateTA_Transaction);
            
           // data.removeTA(oldTAName);
            //data.addTA(name, email);
            
            //updateTAInOfficeHoursGrid(oldTAName, name);
            
            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                
                //taTable.requestFocus();
                //taTable.getSelectionModel().select(rowIndex);
                //taTable.getFocusModel().focus(rowIndex);
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
        }
    }
    
    void updateTAInOfficeHoursGrid(String oldTAName, String name){
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
         TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(oldTAName)
                    || (label.getText().contains(oldTAName + "\n"))
                    || (label.getText().contains("\n" + oldTAName))) {
                        data.replaceTAFromCell(label.textProperty(), oldTAName, name);
                    }
                }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }
    
    public void handleStartEndTime(int startTime, int endTime){
         TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
         TAWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
       
           // if(startTime.substring(startTime.indexOf(":")+1).equals("00") && endTime.substring(endTime.indexOf(":")+1).equals("00"))
           ArrayList<TimeSlot> officeHoursList = TimeSlot.buildOfficeHoursList(data);
           if ((startTime >= MIN_START_HOUR)
                && (endTime <= MAX_END_HOUR)
                && (startTime < endTime)) {
               
              
                //CHECK IF START HOUR IS GREATER THAN EXISTING START HOUR OR END HOUR IS LESS THAN EXISTING END HOUR
                if(startTime>data.getStartHour() || endTime<data.getEndHour()){
                    if(AppFileController.promptToConfirm()!= true)
                        return;
                }
            // THESE ARE VALID HOURS SO KEEP THEM
            
             ComboBox_Transaction comboBox_Transaction = new ComboBox_Transaction(startTime, endTime, officeHoursList, app, workspace);
             jTPS.addTransaction(comboBox_Transaction);
            
            
            
            markWorkAsEdited();
                
           
           }
           else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                dialog.show(props.getProperty(START_END_TIME_ERROR), props.getProperty(START_END_TIME_ERROR_MESSAGE));
                
                
                
           }
           
                
        
    }
    
    void reAssignOfficeHoursDueToStartEndTimeChange(ArrayList<TimeSlot> officeHoursList){
         TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
         for (TimeSlot p: officeHoursList){
                  data.addOfficeHoursReservation(p.getDay(),p.getTime(),p.getName());
                   
               }
         
    }
    
}