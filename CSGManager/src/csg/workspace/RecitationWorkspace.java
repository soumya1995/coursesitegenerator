/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import csg.CSGManager;
import csg.CSGManagerProp;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.WorkspaceData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class RecitationWorkspace {
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManager app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CourseController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    Tab recitationTab;
    BorderPane workspacePane;
    
    //ALL THE THREE COURSE PANES GOES INSIDE A VBox
    VBox recitationPane;
    
    //ALL THE ELEMENTS IN THE WORKSPACE ARE PLACED IN A PANE
    HBox recitationHeaderBox;
    Label recitationHeader;
    
    HBox tableBox;
    TableView<Recitation> table;
    TableColumn<Recitation, String> sectionColumn;
    TableColumn<Recitation, String> instructorColumn;
    TableColumn<Recitation, String> dayColumn;
    TableColumn<Recitation, String> locationColumn;
    TableColumn<Recitation, String> ta1Column;
    TableColumn<Recitation, String> ta2Column;

    VBox addEditBox;
    
    Label addEditLabel;
    
    HBox sectionBox;
    Label sectionLabel;
    TextField sectionTextField;
    
    HBox instructorBox;
    Label instructorLabel;
    TextField instructorTextField;
    
    HBox dayBox;
    Label dayLabel;
    TextField dayTextField;
    
    HBox locationBox;
    Label locationLabel;
    TextField locationTextField;
    
    HBox ta1Box;
    Label ta1Label;
    ComboBox ta1ComboBox;
    
    HBox ta2Box;
    Label ta2Label;
    ComboBox ta2ComboBox;
    
    HBox buttonBox;
    Button addButton;
    Button clearButton;
    
    public RecitationWorkspace(CSGManager initApp, MasterWorkspace masterWorkspace){
        //INITILIZE THE APP
        app = initApp;
        //INITILIZE THE PROPERTIES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //INITILIZE THE HEADER
        recitationHeaderBox = new HBox();
        String recitationHeaderText = props.getProperty(CSGManagerProp.RECITATION_HEADER_TEXT.toString());
        recitationHeader = new Label(recitationHeaderText);
        recitationHeaderBox.getChildren().add(recitationHeader);
        
        //INITILIZE THE TABLE
        table = new TableView();
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       // CourseData data1 = ((WorkspaceData)app.getDataComponent()).getCourseData();
        RecitationData data = ((WorkspaceData)app.getDataComponent()).getRecitationData();
        ObservableList<Recitation> tableData = data.getRecitations();
        table.setItems(tableData);
        String sectionColumnText = props.getProperty(CSGManagerProp.SECTION_COLUMN_TEXT.toString());
        String instructorColumnText = props.getProperty(CSGManagerProp.INSTRUCTOR_COLUMN_TEXT.toString());
        String dayColumnText = props.getProperty(CSGManagerProp.DAY_COLUMN_TEXT.toString());
        String locationColumnText = props.getProperty(CSGManagerProp.LOCATION_COLUMN_TEXT.toString());
        String ta1ColumnText = props.getProperty(CSGManagerProp.TA1_COLUMN_TEXT.toString());
        String ta2ColumnText = props.getProperty(CSGManagerProp.TA2_COLUMN_TEXT.toString());
        sectionColumn = new TableColumn(sectionColumnText);
        instructorColumn = new TableColumn(instructorColumnText);
        dayColumn = new TableColumn(dayColumnText);
        locationColumn = new TableColumn(locationColumnText);
        ta1Column = new TableColumn(ta1ColumnText);
        ta2Column = new TableColumn(ta2ColumnText);
        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("section")
        );
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("instructor")
        );
        dayColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("day")
        );
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("location")
        );
        ta1Column.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta1")
        );
        ta2Column.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("ta2")
        );
        table.getColumns().add(sectionColumn);
        table.getColumns().add(instructorColumn);
        table.getColumns().add(dayColumn);
        table.getColumns().add(locationColumn);
        table.getColumns().add(ta1Column);
        table.getColumns().add(ta2Column);
        
        tableBox = new HBox();
        tableBox.getChildren().add(table);
        
        //THE ADD/EDIT RECITATION SECTION
        String addEditText = props.getProperty(CSGManagerProp.ADD_EDIT_TEXT.toString());
        addEditLabel = new Label(addEditText);
        
        String sectionText = props.getProperty(CSGManagerProp.SECTION_TEXT.toString());
        sectionLabel = new Label(sectionText);
        sectionTextField = new TextField();
        sectionBox = new HBox();
        sectionBox.getChildren().addAll(sectionLabel, sectionTextField);
        
        String instructorText = props.getProperty(CSGManagerProp.INSTRUCTOR_TEXT.toString());
        instructorLabel = new Label(instructorText);
        instructorTextField = new TextField();
        instructorBox = new HBox();
        instructorBox.getChildren().addAll(instructorLabel, instructorTextField);
        
        String dayText = props.getProperty(CSGManagerProp.DAY_TEXT.toString());
        dayLabel = new Label(dayText);
        dayTextField = new TextField();
        dayBox = new HBox();
        dayBox.getChildren().addAll(dayLabel, dayTextField);
        
        String locationText = props.getProperty(CSGManagerProp.LOCATION_TEXT.toString());
        locationLabel = new Label(locationText);
        locationTextField = new TextField();
        locationBox = new HBox();
        locationBox.getChildren().addAll(locationLabel, locationTextField);
        
        //GET THE TAS FOR THE COMBO BOX
        TAData taData = ((WorkspaceData)app.getDataComponent()).getTAData();
        ObservableList<TeachingAssistant> teachingAssistants = taData.getTeachingAssistants();
        ObservableList<String> taNames = FXCollections.observableArrayList();
        for(TeachingAssistant ta: teachingAssistants)
            taNames.add(ta.getName());
        
        //SETUP THE COMBO BOXES
        String ta1Text = props.getProperty(CSGManagerProp.TA1_TEXT.toString());
        ta1Label = new Label(ta1Text);
        ta1ComboBox = new ComboBox(taNames);
        ta1ComboBox.getSelectionModel().selectFirst();
        ta1Box = new HBox();
        ta1Box.getChildren().addAll(ta1Label, ta1ComboBox);
        
        String ta2Text = props.getProperty(CSGManagerProp.TA2_TEXT.toString());
        ta2Label = new Label(ta2Text);
        ta2ComboBox = new ComboBox(taNames);
        ta2ComboBox.getSelectionModel().selectFirst();
        ta2Box = new HBox();
        ta2Box.getChildren().addAll(ta2Label, ta2ComboBox);
        
        String addText = props.getProperty(CSGManagerProp.ADD_TEXT.toString());
        addButton = new Button(addText);
        String clearText = props.getProperty(CSGManagerProp.CLEAR_TEXT.toString());
        clearButton = new Button(clearText);
        buttonBox = new HBox();
        buttonBox.getChildren().addAll(addButton, clearButton);
        
        //PUT EVERYTHING IN THE ADD/EDIT SECTION IN A PANE
        addEditBox = new VBox();
        addEditBox.getChildren().addAll(addEditLabel, sectionBox, instructorBox, dayBox, locationBox, ta1Box, ta2Box);
        
        //PUT ALL THREE ELEMENTS IN A PANE
        recitationPane = new VBox();
        recitationPane.getChildren().addAll(recitationHeaderBox, tableBox, addEditBox);
        
        //PUT THE RECITATION PANE IN A SCROLL PANE
        ScrollPane pane = new ScrollPane(recitationPane);
        pane.setFitToWidth(true);
        
        //PUT THE WHOLE RECITATION PANE IN A TAB
        String recitationTabText = props.getProperty(CSGManagerProp.RECITATION_TAB_TEXT.toString());
        recitationTab = new Tab(recitationTabText);
        recitationTab.setContent(pane);
        
        //PUT THE COURSE TAB IN THE TAB PANE
        masterWorkspace.getTabPane().getTabs().add(recitationTab);
    }
}
