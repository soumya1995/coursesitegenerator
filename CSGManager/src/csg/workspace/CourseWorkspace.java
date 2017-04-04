/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import csg.CSGManagerApp;
import csg.CSGManagerProp;
import csg.data.Page;
import csg.data.CourseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya Das
 */
public class CourseWorkspace extends AppWorkspaceComponent{
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManagerApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CourseController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    TabPane tabPane;
    Tab courseTab;
    BorderPane workspacePane;
    
    //ALL THE THREE COURSE PANES GOES INSIDE A VBox
    VBox coursePane;
    
    
    //COURSE INFO PANE
    VBox courseInfoPane;
    // FOR THE HEADER ON THE LEFT
    HBox courseInfoBox;
    Label courseInfoLabel;
    
    HBox subjectBox;
    Label subjectLabel;
    ComboBox subjectComboBox;
    Label numberLabel;
    ComboBox numberComboBox;
    
    HBox semesterBox;
    Label semesterLabel;
    ComboBox semesterComboBox;
    Label yearLabel;
    ComboBox yearComboBox;
    
    HBox titleBox;
    Label titleLabel;
    TextField titleField;
    
    HBox instructorBox;
    Label instructorLabel;
    TextField instructorField;
    
    HBox homeBox;
    Label homeLabel;
    TextField homeField;
    
    HBox exportBox;
    Label exportLabel;
    Label exportDirLabel;
    Button changeButton;
    
    //THE SITE TEMPLATE PANE 
    VBox template;
    
    HBox dirInfoBox;
    Label dirInfoLabel;
    
    HBox dirBox;
    Label dirLabel;
    
    HBox selectTemplateBox;
    Button selectTemplateButton;
    
    HBox pagesBox;
    Label pagesLabel;    
    
    //TABLE FOR THE PAGES TABLE
    TableView<Page> table;
    TableColumn<Page, CheckBox> useColumn;
    TableColumn<Page, String> navbarColumn;
    TableColumn<Page, String> fileColumn;
    TableColumn<Page, String> scriptColumn;
    
    //THE PAGE STYLE PANE
    VBox style;
    
    HBox pageStyleBox;
    Label pageStyleLabel;
    
    HBox schoolBannerBox;
    Label schoolBannerLabel;
    ImageView schoolBannerImage;
    
    HBox leftFooterBox;
    Label leftFooterLabel;
    ImageView leftFooterImage;
    
    
    HBox rightFooterBox;
    Label rightFooterLabel;
    ImageView rightFooterImage;
    
    HBox stylesheetBox;
    Label stylesheetLabel;
    ObservableList<String> stylesheets;
    ComboBox stylesheetComboBox;
    
    Label styleNoteLabel;
    HBox styleNoteBox;
    
    
    public CourseWorkspace(CSGManagerApp initApp){
        app = initApp;
        
         // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // INITILIZE THE TAB PANE
        tabPane = new TabPane();
        
        //INITILIZE BORDER PANE WHERE TAB PANE IS SET
        workspacePane = new BorderPane();
        
        //THE COURSE INFO HEADER
        String courseInfoText = props.getProperty(CSGManagerProp.COURSE_INFO_HEADER_TEXT.toString());
        courseInfoLabel = new Label(courseInfoText);
        courseInfoBox = new HBox();
        courseInfoBox.getChildren().add(courseInfoLabel);
        
        //THESE WILL HAVE SUBJECT AND SUBJECT NUMBER
        String subjectText = props.getProperty(CSGManagerProp.SUBJECT_TEXT.toString());
        subjectLabel = new Label(subjectText);
        ObservableList<String> subjects = FXCollections.observableArrayList(CSGManagerProp.CSE_TEXT.toString(),CSGManagerProp.ISE_TEXT.toString(),CSGManagerProp.ECE_TEXT.toString());
        subjectComboBox = new ComboBox(subjects);
        String numberText = props.getProperty(CSGManagerProp.NUMBER_TEXT.toString());
        numberLabel = new Label(numberText);
        ObservableList<String> numbers = FXCollections.observableArrayList(CSGManagerProp.TEXT_219.toString(),CSGManagerProp.TEXT_308.toString(),CSGManagerProp.TEXT_373.toString());
        subjectComboBox = new ComboBox(numbers);
        subjectBox = new HBox();
        subjectBox.getChildren().add(subjectLabel);
        subjectBox.getChildren().add(subjectComboBox);
        subjectBox.getChildren().add(numberLabel);
        subjectBox.getChildren().add(numberComboBox);
        
        //THESE WILL HAVE SUBJECT AND SUBJECT NUMBER
        String semesterText = props.getProperty(CSGManagerProp.SEMESTER_TEXT.toString());
        semesterLabel = new Label(semesterText);
        ObservableList<String> sem = FXCollections.observableArrayList(CSGManagerProp.FALL_TEXT.toString(),CSGManagerProp.SPRING_TEXT.toString());
        semesterComboBox = new ComboBox(sem);
        String yearText = props.getProperty(CSGManagerProp.YEAR_TEXT.toString());
        yearLabel = new Label(yearText);
        ObservableList<String> years = FXCollections.observableArrayList(CSGManagerProp.TEXT_2016.toString(),CSGManagerProp.TEXT_2017.toString(),CSGManagerProp.TEXT_2018.toString());
        yearComboBox = new ComboBox(years);
        semesterBox = new HBox();
        semesterBox.getChildren().add(semesterLabel);
        semesterBox.getChildren().add(semesterComboBox);
        semesterBox.getChildren().add(yearLabel);
        semesterBox.getChildren().add(yearComboBox);
        
        //THE COURSE TITLE
        String titleText = props.getProperty(CSGManagerProp.TITLE_TEXT.toString());
        titleLabel = new Label(titleText);
        titleField = new TextField();
        titleBox = new HBox();
        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(titleField);
        
        //THE INSTRUCTOR NAME
        String nameText = props.getProperty(CSGManagerProp.NAME_TEXT.toString());
        instructorLabel = new Label(nameText);
        instructorField = new TextField();
        instructorBox = new HBox();
        instructorBox.getChildren().add(instructorLabel);
        instructorBox.getChildren().add(instructorField);
        
        //THE INSTRUCTOR HOMEPAGE
        String homeText = props.getProperty(CSGManagerProp.HOME_TEXT.toString());
        homeLabel = new Label(homeText);
        homeField = new TextField();
        homeBox = new HBox();
        homeBox.getChildren().add(homeLabel);
        homeBox.getChildren().add(homeField);
        
        //THE CHANGE BUTTON
        String changeText = props.getProperty(CSGManagerProp.CHANGE_TEXT.toString());
        changeButton = new Button(changeText);
        
        //THE EXPORT DIRECTORY
        String exportText = props.getProperty(CSGManagerProp.EXPORT_TEXT.toString());
        exportLabel = new Label(exportText);
        exportDirLabel = new Label();
        exportBox = new HBox();
        exportBox.getChildren().add(exportLabel);
        exportBox.getChildren().add(exportDirLabel);
        exportBox.getChildren().add(changeButton);
        
        //ORGANIZE THE COURSE INFO PANNE
        courseInfoPane = new VBox();
        courseInfoPane.getChildren().add(courseInfoBox);
        courseInfoPane.getChildren().add(subjectBox);
        courseInfoPane.getChildren().add(semesterBox);
        courseInfoPane.getChildren().add(titleBox);
        courseInfoPane.getChildren().add(instructorBox);
        courseInfoPane.getChildren().add(homeBox);
        courseInfoPane.getChildren().add(exportBox);
        
        //THE SITE TEMPLATE HEADER
        String dirInfoText = props.getProperty(CSGManagerProp.DIR_INFO_HEADER_TEXT.toString());
        dirInfoLabel = new Label(dirInfoText);
        dirInfoBox = new HBox();
        dirInfoBox.getChildren().add(dirInfoLabel);
        
        //DIR INFO LABEL
        dirLabel = new Label();
        dirBox = new HBox();
        dirBox.getChildren().add(dirLabel);
        
        //SELECT DIRECTORY TEMPLATE BUTTON
        String templateButtonText = props.getProperty(CSGManagerProp.TEMPLATE_BUTTON_TEXT.toString());
        selectTemplateButton = new Button(templateButtonText);
        selectTemplateBox = new HBox();
        selectTemplateBox.getChildren().add(selectTemplateButton);
        
        //SITE PAGES HEADER
        String pagesText = props.getProperty(CSGManagerProp.PAGES_TEXT.toString());
        pagesLabel = new Label(pagesText);
        pagesBox = new HBox();
        pagesBox.getChildren().add(pagesLabel);
        
        //INITILIZE THE SITE PAGES TABLE
        table = new TableView();
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CourseData data = (CourseData) app.getDataComponent();
        ObservableList<Page> tableData = data.getPages();
        table.setItems(tableData);
        String useColumnText = props.getProperty(CSGManagerProp.USE_COLUMN_TEXT.toString());
        String navbarColumnText = props.getProperty(CSGManagerProp.NAVBAR_COLUMN_TEXT.toString());
        String fileColumnText = props.getProperty(CSGManagerProp.FILE_COLUMN_TEXT.toString());
        String scriptColumnText = props.getProperty(CSGManagerProp.SCRIPT_COLUMN_TEXT.toString());
        useColumn = new TableColumn(useColumnText);
        navbarColumn = new TableColumn(navbarColumnText);
        fileColumn = new TableColumn(fileColumnText);
        scriptColumn = new TableColumn(scriptColumnText);
        useColumn.setCellValueFactory(
                new PropertyValueFactory<Page, CheckBox>("use")
        );
        navbarColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("navbar")
        );
        fileColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("file")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("script")
        );
        table.getColumns().add(useColumn);
        table.getColumns().add(navbarColumn);
        table.getColumns().add(fileColumn);
        table.getColumns().add(scriptColumn);
        
        //ORGANIZE THE SITE TEMPLATE PANE
        template = new VBox();
        template.getChildren().add(dirInfoBox);
        template.getChildren().add(dirBox);
        template.getChildren().add(selectTemplateBox);
        template.getChildren().add(pagesBox);
        template.getChildren().add(table);
        
        //THE PAGE STYLE HEADER
        String styleText = props.getProperty(CSGManagerProp.STYLE_TEXT.toString());
        pageStyleLabel = new Label(styleText);
        pageStyleBox = new HBox();
        pageStyleBox.getChildren().add(pageStyleLabel);
        
        //THE BANNER SCHOOL IMAGE
        String schoolBannerText = props.getProperty(CSGManagerProp.BANNER_TEXT.toString());
        schoolBannerLabel = new Label(schoolBannerText);
        schoolBannerImage = new ImageView();
        schoolBannerBox = new HBox();
        schoolBannerBox.getChildren().add(schoolBannerLabel);
        schoolBannerBox.getChildren().add(schoolBannerImage);
        schoolBannerBox.getChildren().add(changeButton);
        
        //THE LEFT FOOTER IMAGE
        String leftFooterText = props.getProperty(CSGManagerProp.LEFT_FOOTER_TEXT.toString());
        leftFooterLabel = new Label(leftFooterText);
        leftFooterImage = new ImageView();
        leftFooterBox = new HBox();
        leftFooterBox.getChildren().add(leftFooterLabel);
        leftFooterBox.getChildren().add(leftFooterImage);
        leftFooterBox.getChildren().add(changeButton);
        
        //THE RIGHT FOOTER IMAGE
        String rightFooterText = props.getProperty(CSGManagerProp.RIGHT_FOOTER_TEXT.toString());
        rightFooterLabel = new Label(rightFooterText);
        rightFooterImage = new ImageView();
        rightFooterBox = new HBox();
        rightFooterBox.getChildren().add(rightFooterLabel);
        rightFooterBox.getChildren().add(rightFooterImage);
        rightFooterBox.getChildren().add(changeButton);
        
        //THE STYLESHEET CHOOSER COMBOBOX
        String stylesheetText = props.getProperty(CSGManagerProp.STYLESHEET_TEXT.toString());
        stylesheetLabel = new Label(stylesheetText);
        stylesheets = FXCollections.observableArrayList();
        stylesheetComboBox = new ComboBox(stylesheets);
        stylesheetBox = new HBox();
        stylesheetBox.getChildren().add(stylesheetLabel);
        stylesheetBox.getChildren().add(stylesheetComboBox);
        
        //STYLESHEET NOTES
        String styleNoteText = props.getProperty(CSGManagerProp.STYLESHEET_NOTE_TEXT.toString());
        styleNoteLabel = new Label(styleNoteText);
        styleNoteBox = new HBox();
        styleNoteBox.getChildren().add(styleNoteLabel);
        
        //ORGANIZING THE STYLE PANE
        style = new VBox();
        style.getChildren().add(pageStyleBox);
        style.getChildren().add(schoolBannerBox);
        style.getChildren().add(leftFooterBox);
        style.getChildren().add(rightFooterBox);
        style.getChildren().add(stylesheetBox);
        style.getChildren().add(styleNoteBox);
        
        //THE THREE PANES GOES INTO A SINGLE VBOX
        coursePane.getChildren().add(courseInfoPane);
        coursePane.getChildren().add(template);
        coursePane.getChildren().add(style);
        
        //PUT THE WHOLE COURSE PANE IN A TAB
        courseTab = new Tab();
        courseTab.setContent(coursePane);
        
        //PUT THE COURSE TAB IN THE TAB PANE
        tabPane.getTabs().add(courseTab);
        
        //PUT TAB PANE IN A BORDER PANE
        workspacePane.setCenter(tabPane);
        
       
    }
    
    public void resetWorkspace(){
        
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent){
        
    }
}
    
