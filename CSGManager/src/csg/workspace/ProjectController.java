/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import static csg.CSGManagerProp.MISSING_TA_EMAIL_MESSAGE;
import static csg.CSGManagerProp.MISSING_TA_EMAIL_TITLE;
import static csg.CSGManagerProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGManagerProp.MISSING_TA_NAME_TITLE;
import static csg.CSGManagerProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static csg.CSGManagerProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import csg.data.Student;
import csg.data.StudentData;
import csg.data.TAData;
import csg.data.Team;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import static csg.workspace.TAController.jTPS;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import jtps.jTPS;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class ProjectController {

    
    CSGManager app;
    static jTPS jTPS = new jTPS(); 
    
    public ProjectController(CSGManager initApp) {
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
    
    public void handleAddTeam(){
    
    // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField nameTextField = workspace.getNameTextField();
        ColorPicker colorPicker = workspace.getColorPicker();
        ColorPicker textColorPicker = workspace.getTextColorPicker();
        TextField linkTextField = workspace.getLinkTextField();
        
        String name = nameTextField.getText();
        Color c1 = colorPicker.getValue();
        String color = String.format( "%02X%02X%02X",(int)( c1.getRed() * 255 ),(int)( c1.getGreen() * 255 ),(int)( c1.getBlue() * 255 ) );
        Color c2 = textColorPicker.getValue();
        String textColor = String.format( "%02X%02X%02X",(int)( c2.getRed() * 255 ),(int)( c2.getGreen() * 255 ),(int)( c2.getBlue() * 255 ) );
        String link = linkTextField.getText();
                
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TeamData data = ((WorkspaceData)app.getDataComponent()).getTeamData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (color.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else if (textColor.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
        }
        
        else if (link.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
        }
        
       else if (data.containsTeam(name, color, textColor, link)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        
       else{
           AddTeamTransaction addTeamTransaction = new AddTeamTransaction(name, color, textColor, link, app);
           jTPS.addTransaction(addTeamTransaction);
           
           //CLEAR THE FIELDS
           nameTextField.setText("");
           colorPicker.setValue(Color.BLUE);
           textColorPicker.setValue(Color.WHITE);
           linkTextField.setText("");
           
           markWorkAsEdited();
       }
    }
    
    public void handleClearTeam(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField nameTextField = workspace.getNameTextField();
        ColorPicker colorPicker = workspace.getColorPicker();
        ColorPicker textColorPicker = workspace.getTextColorPicker();
        TextField linkTextField = workspace.getLinkTextField();
        
            //CLEAR THE FIELDS
           nameTextField.setText("");
           colorPicker.setValue(Color.BLUE);
           textColorPicker.setValue(Color.WHITE);
           linkTextField.setText("");
    }
    
    public void handleUpdateTeam(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TableView table = workspace.getTeamTable();
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            Team team = (Team)selectedItem;
            String name = team.getName();
            String color = team.getColor();
            String textColor = team.getTextColor();
            String link = team.getLink();
            
            TextField nameTextField = workspace.getNameTextField();
            ColorPicker colorPicker = workspace.getColorPicker();
            ColorPicker textColorPicker = workspace.getTextColorPicker();
            TextField linkTextField = workspace.getLinkTextField();
            Button addButton = workspace.getAddButton();
            
            nameTextField.setText(name);
            colorPicker.setValue(Color.web("#"+color));
            textColorPicker.setValue(Color.web("#"+textColor));
            linkTextField.setText(link);
            
            addButton.setOnAction(e -> {
                updateTeam();
        });  
            markWorkAsEdited();
        }
    }
    
    public void updateTeam(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField nameTextField = workspace.getNameTextField();
        ColorPicker colorPicker = workspace.getColorPicker();
        ColorPicker textColorPicker = workspace.getTextColorPicker();
        TextField linkTextField = workspace.getLinkTextField();
        
        String name = nameTextField.getText();
        Color c1 = colorPicker.getValue();
        String color = String.format( "%02X%02X%02X",(int)( c1.getRed() * 255 ),(int)( c1.getGreen() * 255 ),(int)( c1.getBlue() * 255 ) );
        Color c2 = textColorPicker.getValue();
        String textColor = String.format( "%02X%02X%02X",(int)( c2.getRed() * 255 ),(int)( c2.getGreen() * 255 ),(int)( c2.getBlue() * 255 ) );
        String link = linkTextField.getText();
                
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TeamData data = ((WorkspaceData)app.getDataComponent()).getTeamData();
        TableView table = workspace.getTeamTable();
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        Team team = (Team)selectedItem;
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (color.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else if (textColor.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
        }
        
        else if (link.isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));    
        }
        
       else if (data.containsTeam(name, color, textColor, link)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        
       else{
           UpdateTeamTransaction updateTeamTransaction = new UpdateTeamTransaction(team.getName(), team.getColor(), team.getTextColor(), team.getLink(), name, color, textColor, link, app);
           jTPS.addTransaction(updateTeamTransaction);
           
           //CLEAR THE FIELDS
           nameTextField.setText("");
           colorPicker.setValue(Color.BLUE);
           textColorPicker.setValue(Color.WHITE);
           linkTextField.setText("");
       }
    }
    
    public void handleTeamKeyPress(KeyEvent event) {
        // DID THE USER PRESS THE DELETE KEY?
        KeyCode code = event.getCode();
        if (code == KeyCode.DELETE) {
            
            //CONFIRM THAT THE USER WANTS TO DELETE THE TEAM BECAUSE THE STUDENTS IN THE TEAM WILL BE DELETED
            if(AppFileController.promptToConfirm()!= true)
                return;
            
            ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
            TableView teamTable = workspace.getTeamTable();
            
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Team t = (Team)selectedItem;
                
                DeleteTeam_Transaction DeleteTeam_Transaction = new DeleteTeam_Transaction(t.getName(), t.getColor(), t.getTextColor(), t.getLink(), app);
                jTPS.addTransaction(DeleteTeam_Transaction);
                
                markWorkAsEdited();
            }
        }
        
        final KeyCombination CtrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
        final KeyCombination CtrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
        if (code == KeyCode.Z && event.isShortcutDown()) {
            jTPS.undoTransaction();
        }
        if (CtrlY.match(event)) {
            jTPS.doTransaction();
        }
    }
    
    public void handleAddStudent(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamComboBox = workspace.getTeamComboBox();
        TextField roleTextField = workspace.getRoleTextField();
        
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String team = (String)(teamComboBox.getSelectionModel().getSelectedItem());
        String role = roleTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        StudentData data = ((WorkspaceData)app.getDataComponent()).getStudentData();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (firstName.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (lastName.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else if (role.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else if(data.containsStudent(firstName)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        
        else{
            AddStudent_Transaction addStudent_Transaction = new AddStudent_Transaction(firstName, lastName, team, role, app);
            jTPS.addTransaction(addStudent_Transaction);
            
            //CLEAR THE FIELDS
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            teamComboBox.getSelectionModel().selectFirst();
            roleTextField.setText("");
            
            markWorkAsEdited();
        }
    }
    
    public void handleClearStudent(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamComboBox = workspace.getTeamComboBox();
        TextField roleTextField = workspace.getRoleTextField();
        
            //CLEAR THE FIELDS
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            teamComboBox.getSelectionModel().selectFirst();
            roleTextField.setText("");
    }
    
    public void handleUpdateStudent(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TableView table = workspace.getStudentTable();
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            Student student = (Student)selectedItem;
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String team = student.getTeam();
            String role = student.getRole();
            
            TextField firstNameTextField = workspace.getFirstNameTextField();
            TextField lastNameTextField = workspace.getLastNameTextField();
            ComboBox teamComboBox = workspace.getTeamComboBox();
            TextField roleTextField = workspace.getRoleTextField();
            Button addButton = workspace.getAddButton2();
            
            firstNameTextField.setText(firstName);
            lastNameTextField.setText(lastName);
            teamComboBox.getSelectionModel().select(team);
            roleTextField.setText(role);
            
            addButton.setOnAction(e -> {
                updateStudent();
        });  
            markWorkAsEdited();
        }
    }
    
    public void updateStudent(){
        ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
        TextField firstNameTextField = workspace.getFirstNameTextField();
        TextField lastNameTextField = workspace.getLastNameTextField();
        ComboBox teamComboBox = workspace.getTeamComboBox();
        TextField roleTextField = workspace.getRoleTextField();
        
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String team = (String)(teamComboBox.getSelectionModel().getSelectedItem());
        String role = roleTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        StudentData data = ((WorkspaceData)app.getDataComponent()).getStudentData();
        TableView table = workspace.getStudentTable();
        Object selectedItem = table.getSelectionModel().getSelectedItem();
        Student student = (Student)selectedItem;
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (firstName.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (lastName.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else if (role.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        
        else{
           UpdateStudentTransaction updateStudentTransaction = new UpdateStudentTransaction(student.getFirstName(), student.getLastName(), student.getTeam(),student.getRole(),firstName, lastName, team, role, app);
           jTPS.addTransaction(updateStudentTransaction);
           
           //CLEAR THE FIELDS
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            teamComboBox.getSelectionModel().selectFirst();
            roleTextField.setText("");
        }
    }
    
    public void handleStudentKeyPress(KeyEvent event) {
        // DID THE USER PRESS THE DELETE KEY?
        KeyCode code = event.getCode();
        if (code == KeyCode.DELETE) {
            ProjectWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getProjectWorkspace();
            TableView studentTable = workspace.getStudentTable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Student s = (Student)selectedItem;
                
                DeleteStudent_Transaction DeleteStudent_Transaction = new DeleteStudent_Transaction(s.getFirstName(), s.getLastName(), s.getTeam(), s.getRole(), app);
                jTPS.addTransaction(DeleteStudent_Transaction);
                
                markWorkAsEdited();
            }
        }
        
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
