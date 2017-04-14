/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import csg.CSGManagerProp;
import csg.data.Schedule;
import csg.data.ScheduleData;
import csg.data.Student;
import csg.data.StudentData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class ProjectWorkspace {
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManager app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    ProjectController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    Tab projectTab;
    BorderPane workspacePane;
    
    //ALL THE THREE COURSE PANES GOES INSIDE A VBox
    VBox projectPane;
    
    //ALL THE ELEMENTS IN THE WORKSPACE ARE PLACED IN A PANE
    HBox projectHeaderBox;
    Label projectHeader;
    
    //TEAM PANE
    VBox teamBox;
    
    HBox teamLabelBox;
    Label teamLabel;
    
    HBox teamTableBox;
    TableView<Team> teamTable;
    TableColumn<Team, String> nameColumn;
    TableColumn<Team, String> colorColumn;
    TableColumn<Team, String> textColorColumn;
    TableColumn<Team, Hyperlink> linkColumn;
    
    Label addEditLabel;
    
    HBox nameBox;
    Label nameLabel;
    TextField nameTextField;
    
    HBox colorBox;
    Label colorLabel;
    ColorPicker colorPicker;
    Label textColorLabel;
    ColorPicker textColorPicker;
    
    HBox linkBox;
    Label linkLabel;
    TextField linkTextField;
    
    HBox buttonBox;
    Button addButton;
    Button clearButton;
    
    //STUDENT PANE
    VBox studentBox;
    
    HBox studentLabelBox;
    Label studentLabel;
    
    HBox studentTableBox;
    TableView<Student> studentTable;
    TableColumn<Student, String> firstNameColumn;
    TableColumn<Student, String> lastNameColumn;
    TableColumn<Student, String> teamColumn;
    TableColumn<Student, String> roleColumn;
    
    Label addEditLabel2;
    
    HBox firstNameBox;
    Label firstNameLabel;
    TextField firstNameTextField;
    
    HBox lastNameBox;
    Label lastNameLabel;
    TextField lastNameTextField;
    
    HBox teamFieldBox;
    Label teamComboLabel;
    ComboBox teamComboBox;
    
    HBox roleBox;
    Label roleLabel;
    TextField roleTextField;
    
    HBox buttonBox2;
    Button addButton2;
    Button clearButton2;
    
    
    public ProjectWorkspace(CSGManager initApp, MasterWorkspace masterWorkspace){
        //INITILIZE THE APP
        app = initApp;
        //INITILIZE THE PROPERTIES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //INITILIZE THE HEADER
        projectHeaderBox = new HBox();
        String projectHeaderText = props.getProperty(CSGManagerProp.PROJECT_HEADER_TEXT.toString());
        projectHeader = new Label(projectHeaderText);
        projectHeaderBox.getChildren().add(projectHeader);
       
        //THE TEAM PANE
        teamLabelBox = new HBox();
        String teamLabelText = props.getProperty(CSGManagerProp.TEAM_TEXT.toString());
        teamLabel = new Label(teamLabelText);
        teamLabelBox.getChildren().add(teamLabel);
        
        //INITILIZE THE TEAM TABLE
        teamTable = new TableView();
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TeamData data = ((WorkspaceData)app.getDataComponent()).getTeamData();
        ObservableList<Team> tableData = data.getTeams();
        teamTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGManagerProp.NAME_COLUMN_TEXT.toString());
        String colorColumnText = props.getProperty(CSGManagerProp.COLOR_COLUMN_TEXT.toString());
        String textColorColumnText = props.getProperty(CSGManagerProp.TEXT_COLOR_COLUMN_TEXT.toString());
        String linkColumnText = props.getProperty(CSGManagerProp.LINK_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        colorColumn = new TableColumn(colorColumnText);
        textColorColumn = new TableColumn(textColorColumnText);
        linkColumn = new TableColumn(linkColumnText);
        
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("name")
        );
        colorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
        textColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        linkColumn.setCellValueFactory(
                new PropertyValueFactory<Team, Hyperlink>("link")
        );
        
        teamTable.getColumns().add(nameColumn);
        teamTable.getColumns().add(colorColumn);
        teamTable.getColumns().add(textColorColumn);
        teamTable.getColumns().add(linkColumn);

        teamTableBox = new HBox();
        teamTableBox.getChildren().add(teamTable);
        
        String addEditText = props.getProperty(CSGManagerProp.ADD_EDIT_TEXT.toString());
        addEditLabel = new Label(addEditText);
        
        nameBox = new HBox();
        nameBox.setSpacing(38);
        String nameText = props.getProperty(CSGManagerProp.STUDENT_NAME_TEXT.toString());
        nameLabel = new Label(nameText);
        nameTextField = new TextField();
        nameTextField.prefWidth(200);
        nameBox.getChildren().addAll(nameLabel, nameTextField);
        
        String colorText = props.getProperty(CSGManagerProp.COLOR_TEXT.toString());
        colorLabel = new Label(colorText);
        colorPicker = new ColorPicker(Color.BLUE);
        HBox c1 = new HBox();
        c1.setSpacing(30);
        c1.getChildren().addAll(colorLabel, colorPicker);
        String textColorText = props.getProperty(CSGManagerProp.TEXT_COLOR_TEXT.toString());
        textColorLabel = new Label(textColorText);
        textColorPicker = new ColorPicker();
        HBox c2 = new HBox();
        c2.setSpacing(30);
        c2.getChildren().addAll(textColorLabel, textColorPicker);
        colorBox = new HBox();
        colorBox.setSpacing(20);
        colorBox.getChildren().addAll(c1, c2);
        
        linkBox = new HBox();
        linkBox.setSpacing(38);
        String linkText = props.getProperty(CSGManagerProp.HYPERLINK_TEXT.toString());
        linkLabel = new Label(linkText);
        linkTextField = new TextField();
        linkTextField.prefWidth(600);
        linkBox.getChildren().addAll(linkLabel, linkTextField);
        
        String addText = props.getProperty(CSGManagerProp.ADD_TEXT.toString());
        addButton = new Button(addText);
        String clearText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton = new Button(clearText);
        buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(addButton, clearButton);
        
        teamBox = new VBox();
        teamBox.getChildren().addAll(teamLabelBox, teamTableBox, addEditLabel, nameBox, colorBox, linkBox, buttonBox);
        
        //THE STUDENT PANE
        studentLabelBox = new HBox();
        String studentLabelText = props.getProperty(CSGManagerProp.STUDENT_TEXT.toString());
        studentLabel = new Label(studentLabelText);
        studentLabelBox.getChildren().add(studentLabel);
        
        //INITILIZE THE TEAM TABLE
        studentTable = new TableView();
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        StudentData data2 = ((WorkspaceData)app.getDataComponent()).getStudentData();
        ObservableList<Student> tableData2 = data2.getStudents();
        studentTable.setItems(tableData2);
        String firstNameColumnText = props.getProperty(CSGManagerProp.FIRSTNAME_COLUMN_TEXT.toString());
        String lastNameColumnText = props.getProperty(CSGManagerProp.LASTNAME_COLUMN_TEXT.toString());
        String teamColumnText = props.getProperty(CSGManagerProp.TEAM_COLUMN_TEXT.toString());
        String roleColumnText = props.getProperty(CSGManagerProp.ROLE_COLUMN_TEXT.toString());
        firstNameColumn = new TableColumn(firstNameColumnText);
        lastNameColumn = new TableColumn(lastNameColumnText);
        teamColumn = new TableColumn(teamColumnText);
        roleColumn = new TableColumn(roleColumnText);
        
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("team")
        );
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("role")
        );
        
        studentTable.getColumns().add(firstNameColumn);
        studentTable.getColumns().add(lastNameColumn);
        studentTable.getColumns().add(teamColumn);
        studentTable.getColumns().add(roleColumn);

        studentTableBox = new HBox();
        studentTableBox.getChildren().add(studentTable);
        
        String addEditText2 = props.getProperty(CSGManagerProp.ADD_EDIT_TEXT.toString());
        addEditLabel2 = new Label(addEditText2);
        
        firstNameBox = new HBox();
        firstNameBox.setSpacing(25);
        String firstNameText = props.getProperty(CSGManagerProp.FIRSTNAME_TEXT.toString());
        firstNameLabel = new Label(firstNameText);
        firstNameTextField = new TextField();
        firstNameTextField.prefWidth(200);
        firstNameBox.getChildren().addAll(firstNameLabel, firstNameTextField);
        
        lastNameBox = new HBox();
        lastNameBox.setSpacing(35);
        String lastNameText = props.getProperty(CSGManagerProp.LASTNAME_TEXT.toString());
        lastNameLabel = new Label(lastNameText);
        lastNameTextField = new TextField();
        lastNameTextField.prefWidth(200);
        lastNameBox.getChildren().addAll(lastNameLabel, lastNameTextField);
        
        //GET THE TEAMS FOR THE COMBO BOX
        TeamData teamData = ((WorkspaceData)app.getDataComponent()).getTeamData();
        ObservableList<Team> teams = teamData.getTeams();
        ObservableList<String> teamNames = FXCollections.observableArrayList();
        for(Team t: teams)
            teamNames.add(t.getName());
        
        
        String teamComboText = props.getProperty(CSGManagerProp.TEAM_COMOBO_TEXT.toString());
        teamComboLabel = new Label(teamComboText);
        teamComboBox = new ComboBox(teamNames);
        teamComboBox.getSelectionModel().selectFirst();
        teamFieldBox = new HBox();
        teamFieldBox.setSpacing(70);
        teamFieldBox.getChildren().addAll(teamComboLabel, teamComboBox);
        
        roleBox = new HBox();
        roleBox.setSpacing(70);
        String roleText = props.getProperty(CSGManagerProp.ROLE_TEXT.toString());
        roleLabel = new Label(roleText);
        roleTextField = new TextField();
        roleTextField.prefWidth(200);
        roleBox.getChildren().addAll(roleLabel, roleTextField);
        
        String addText2 = props.getProperty(CSGManagerProp.ADD_TEXT.toString());
        addButton2 = new Button(addText2);
        String clearText2 = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton2 = new Button(clearText2);
        buttonBox2 = new HBox();
        buttonBox2.setSpacing(20);
        buttonBox2.getChildren().addAll(addButton2, clearButton2);
        
        studentBox = new VBox();
        studentBox.getChildren().addAll(studentLabelBox, studentTableBox, addEditLabel2, firstNameBox, lastNameBox, teamFieldBox, roleBox, buttonBox2);
        
        //PUT ALL THREE ELEMENTS IN A PANE
        projectPane = new VBox();
        projectPane.getChildren().addAll(projectHeaderBox, teamBox, studentBox);
        
        //PUT THE PROJECT PANE IN A SCROLL PANE
        ScrollPane pane = new ScrollPane(projectPane);
        pane.setFitToWidth(true);
        
        teamTable.prefWidthProperty().bind(teamBox.widthProperty().multiply(1.9));
        studentTable.prefWidthProperty().bind(studentBox.widthProperty().multiply(1.9));

        
        //PUT THE WHOLE RECITATION PANE IN A TAB
        String projectTabText = props.getProperty(CSGManagerProp.PROJECT_TAB_TEXT.toString());
        projectTab = new Tab(projectTabText);
        projectTab.setContent(pane);
        
        //PUT THE COURSE TAB IN THE TAB PANE
        masterWorkspace.getTabPane().getTabs().add(projectTab);
        
    }

    public Tab getProjectTab() {
        return projectTab;
    }

    public VBox getProjectPane() {
        return projectPane;
    }

    public HBox getProjectHeaderBox() {
        return projectHeaderBox;
    }

    public Label getProjectHeader() {
        return projectHeader;
    }

    public VBox getTeamBox() {
        return teamBox;
    }

    public HBox getTeamLabelBox() {
        return teamLabelBox;
    }

    public Label getTeamLabel() {
        return teamLabel;
    }

    public HBox getTeamTableBox() {
        return teamTableBox;
    }

    public TableView<Team> getTeamTable() {
        return teamTable;
    }

    public TableColumn<Team, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<Team, String> getColorColumn() {
        return colorColumn;
    }

    public TableColumn<Team, String> getTextColorColumn() {
        return textColorColumn;
    }

    public TableColumn<Team, Hyperlink> getLinkColumn() {
        return linkColumn;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public HBox getNameBox() {
        return nameBox;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public HBox getColorBox() {
        return colorBox;
    }

    public Label getColorLabel() {
        return colorLabel;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public Label getTextColorLabel() {
        return textColorLabel;
    }

    public ColorPicker getTextColorPicker() {
        return textColorPicker;
    }

    public HBox getLinkBox() {
        return linkBox;
    }

    public Label getLinkLabel() {
        return linkLabel;
    }

    public TextField getLinkTextField() {
        return linkTextField;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public VBox getStudentBox() {
        return studentBox;
    }

    public HBox getStudentLabelBox() {
        return studentLabelBox;
    }

    public Label getStudentLabel() {
        return studentLabel;
    }

    public HBox getStudentTableBox() {
        return studentTableBox;
    }

    public TableView<Student> getStudentTable() {
        return studentTable;
    }

    public TableColumn<Student, String> getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn<Student, String> getLastNameColumn() {
        return lastNameColumn;
    }

    public TableColumn<Student, String> getTeamColumn() {
        return teamColumn;
    }

    public TableColumn<Student, String> getRoleColumn() {
        return roleColumn;
    }

    public Label getAddEditLabel2() {
        return addEditLabel2;
    }

    public HBox getFirstNameBox() {
        return firstNameBox;
    }

    public Label getFirstNameLabel() {
        return firstNameLabel;
    }

    public TextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public HBox getLastNameBox() {
        return lastNameBox;
    }

    public Label getLastNameLabel() {
        return lastNameLabel;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public HBox getTeamFieldBox() {
        return teamFieldBox;
    }

    public Label getTeamComboLabel() {
        return teamComboLabel;
    }

    public ComboBox getTeamComboBox() {
        return teamComboBox;
    }

    public HBox getRoleBox() {
        return roleBox;
    }

    public Label getRoleLabel() {
        return roleLabel;
    }

    public TextField getRoleTextField() {
        return roleTextField;
    }

    void resetWorkspace() {
      
    }

    void reloadWorkspace(AppDataComponent dataComponent) {
        
    }
    
    
    
}
