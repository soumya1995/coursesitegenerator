/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import static csg.CSGManagerProp.*;
import csg.data.Recitation;
import csg.data.Schedule;
import csg.data.ScheduleData;
import csg.data.TAData;
import csg.data.WorkspaceData;
import static csg.workspace.RecitationController.jTPS;
import static csg.workspace.TAController.jTPS;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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
public class ScheduleController {
    
    static jTPS jTPS = new jTPS(); 
    CSGManager app;

    ScheduleController(CSGManager initApp) {
        app=initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }

    public void handleStartEndDate(Date start, Date end) throws ParseException {
         ScheduleData data = ((WorkspaceData)app.getDataComponent()).getScheduleData();
         ScheduleWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
         
         if(start.compareTo(end)<=0){
             
             //CHECK IF START DATE IS GREATER THAN EXISTING START DATE OR END DATE IS LESS THAN EXISTING END HOUR
                if(start.compareTo(data.getStartDate())>0 || end.compareTo(data.getEndDate())<0){
                    if(AppFileController.promptToConfirmDates()!= true)
                        return;
                }
                
                 // THESE ARE VALID HOURS SO KEEP THEM
             
             ComboDateBox_Transaction comboBox_Transaction = new ComboDateBox_Transaction(start, end, data.getExcessSchedules(start, end), app);
             jTPS.addTransaction(comboBox_Transaction);

            markWorkAsEdited();

           }
           else{
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                dialog.show(props.getProperty(START_END_TIME_ERROR), props.getProperty(START_END_TIME_ERROR_MESSAGE));
           }
         }
    
    public void handleUndo(){
        jTPS.undoTransaction();
    }
    
    public void handleRedo(){
        jTPS.doTransaction();
    }

    public void handleAddSchedule() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        ScheduleWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        ComboBox typeComboBox = workspace.getTypeComboBox();
        DatePicker datePicker = workspace.getDatePickerBox();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();
        
        String type =(String)typeComboBox.getSelectionModel().getSelectedItem();
        Date date= Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String time = timeTextField.getText();
        String title = titleTextField.getText();
        String topic =topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        ScheduleData data = ((WorkspaceData)app.getDataComponent()).getScheduleData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (type.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (date.compareTo(data.getStartDate())<0 || date.compareTo(data.getEndDate())>0) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INS_NAME_TITLE), props.getProperty(MISSING_INS_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (time.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_NAME_TITLE), props.getProperty(MISSING_DAY_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (title.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        else if (topic.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        
        else if (data.containsSchedule(date, type)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SECTION_DAY_NOT_UNIQUE_TITLE), props.getProperty(SECTION_DAY_NOT_UNIQUE_MESSAGE));                                    
        }
        
        else{
            // ADD THE NEW TA TO THE DATA
            AddSchedule_Transaction addSchedule_Transaction = new AddSchedule_Transaction(type, date, time, title, topic, link, criteria, app);
            jTPS.addTransaction(addSchedule_Transaction);
            
            //CLEAR  FIELDS
            timeTextField.setText("");
            titleTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    public void handleClearSchedule() {
        //WE WILL NEED TO RETRIVE THE FIELDS
        ScheduleWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        ComboBox typeComboBox = workspace.getTypeComboBox();
        DatePicker datePicker = workspace.getDatePickerBox();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();
        
            //CLEAR  FIELDS
            typeComboBox.getSelectionModel().selectFirst();
            timeTextField.setText("");
            titleTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");
    }

    public void handleUpdateSchedule() {
        ScheduleWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        TableView table = workspace.getTable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            Schedule r = (Schedule)selectedItem;
            String type = r.getType();
            String date = r.getDate();
            String time = r.getTime();
            String title = r.getTitle();
            String topic = r.getTopic();
            String link = r.getLink();
            String criteria = r.getCriteria();
            
        ComboBox typeComboBox = workspace.getTypeComboBox();
        DatePicker datePicker = workspace.getDatePickerBox();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();
        Button button = workspace.getAddButton();
        
            
            //SET TEXT FIELDS WITH APPROPRIATE NAME AND EMAIL
            typeComboBox.getSelectionModel().select(type);
            datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            timeTextField.setText(time);
            titleTextField.setText(title);
            topicTextField.setText(topic);
            linkTextField.setText(link);
            criteriaTextField.setText(criteria);
            
            button.setOnAction(e -> {
                updateSchedule();
        });
            
        }
      }
    
    public void updateSchedule(){
        ScheduleWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        TableView table = workspace.getTable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        
        ComboBox typeComboBox = workspace.getTypeComboBox();
        DatePicker datePicker = workspace.getDatePickerBox();
        TextField timeTextField = workspace.getTimeTextField();
        TextField titleTextField = workspace.getTitleTextField();
        TextField topicTextField = workspace.getTopicTextField();
        TextField linkTextField = workspace.getLinkTextField();
        TextField criteriaTextField = workspace.getCriteriaTextField();
        
        String type =(String)typeComboBox.getSelectionModel().getSelectedItem();
        Date date= Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String time = timeTextField.getText();
        String title = titleTextField.getText();
        String topic =topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();
        // GET THE SCHEDULE
            Schedule s = (Schedule)selectedItem;
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        ScheduleData data = ((WorkspaceData)app.getDataComponent()).getScheduleData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (type.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SECTION_NAME_TITLE), props.getProperty(MISSING_SECTION_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (date.compareTo(data.getStartDate())<0 || date.compareTo(data.getEndDate())>0) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_INS_NAME_TITLE), props.getProperty(MISSING_INS_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (time.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_DAY_NAME_TITLE), props.getProperty(MISSING_DAY_NAME_MESSAGE));                        
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (title.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        else if (topic.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        else if (link.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        else if (criteria.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_LOC_NAME_TITLE), props.getProperty(MISSING_LOC_NAME_MESSAGE));                        
        }
        
        else if (data.containsSchedule(date, type)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SECTION_DAY_NOT_UNIQUE_TITLE), props.getProperty(SECTION_DAY_NOT_UNIQUE_MESSAGE));                                    
        }
        
        else{
            // ADD THE NEW TA TO THE DATA
            UpdateSchedule_Transaction updateSchedule_Transaction = new UpdateSchedule_Transaction(s.getType(), s.getDate(), s.getTime(), s.getTitle(), s.getTopic(), s.getLink(), s.getCriteria(), type, new SimpleDateFormat("MM/dd/yyyy").format(date), time, title, topic, link, criteria, app);
            jTPS.addTransaction(updateSchedule_Transaction);
            
            //CLEAR  FIELDS
            timeTextField.setText("");
            titleTextField.setText("");
            topicTextField.setText("");
            linkTextField.setText("");
            criteriaTextField.setText("");
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
        
        
    }

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
                Schedule s = (Schedule)selectedItem;
                ScheduleData data = ((WorkspaceData)app.getDataComponent()).getScheduleData();
                
                DeleteSchedule_Transaction DeleteSchedule_Transaction = new DeleteSchedule_Transaction(s.getType(),s.getDate(), s.getTime(), s.getTitle(), s.getTopic(), s.getLink(), s.getCriteria(), app);
                jTPS.addTransaction(DeleteSchedule_Transaction);
                
                
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
 }
    
    

    

