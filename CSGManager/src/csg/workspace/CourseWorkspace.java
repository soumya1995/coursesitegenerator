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
import csg.data.Page;
import csg.data.CourseData;
import csg.data.WorkspaceData;
import java.io.File;
import java.util.Collection;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya Das
 */
public class CourseWorkspace{
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManager app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CourseController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    Tab courseTab;
    BorderPane workspacePane;
    
    //ALL THE THREE COURSE PANES GOES INSIDE A VBox
    VBox coursePane;
    
    
    //COURSE INFO PANE
    VBox courseInfoPane;
    // FOR THE HEADER 
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
    Button changeButton1;
    
    //THE SITE TEMPLATE PANE 
    VBox template;
    
    HBox siteTemplateBox;
    Label siteTemplateLabel;
    
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
    Button changeButton2;
    
    HBox leftFooterBox;
    Label leftFooterLabel;
    ImageView leftFooterImage;
    Button changeButton3;
    
    
    HBox rightFooterBox;
    Label rightFooterLabel;
    ImageView rightFooterImage;
    Button changeButton4;
    
    HBox stylesheetBox;
    Label stylesheetLabel;
    ObservableList<String> stylesheets;
    ComboBox stylesheetComboBox;
    
    Label styleNoteLabel;
    HBox styleNoteBox;
    
    
    public CourseWorkspace(CSGManager initApp, MasterWorkspace masterWorkspace){
        app = initApp;
        
         // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        
        
        //THE COURSE INFO HEADER
        String courseInfoText = props.getProperty(CSGManagerProp.COURSE_INFO_HEADER_TEXT.toString());
        courseInfoLabel = new Label(courseInfoText);
        courseInfoBox = new HBox();
        courseInfoBox.getChildren().add(courseInfoLabel);
        
        //THESE WILL HAVE SUBJECT AND SUBJECT NUMBER
        String subjectText = props.getProperty(CSGManagerProp.SUBJECT_TEXT.toString());
        subjectLabel = new Label(subjectText);
        String cse = props.getProperty(CSGManagerProp.CSE_TEXT.toString());
        String ise = props.getProperty(CSGManagerProp.ISE_TEXT.toString());
        String ece = props.getProperty(CSGManagerProp.ECE_TEXT.toString());
        ObservableList<String> subjects = FXCollections.observableArrayList(cse,ise,ece);
        subjectComboBox = new ComboBox(subjects);
        subjectComboBox.getSelectionModel().selectFirst();
        String numberText = props.getProperty(CSGManagerProp.NUMBER_TEXT.toString());
        numberLabel = new Label(numberText);
        String txt219 = props.getProperty(CSGManagerProp.TEXT_219.toString());
        String txt308 = props.getProperty(CSGManagerProp.TEXT_308.toString());
        String txt373 = props.getProperty(CSGManagerProp.TEXT_373.toString());
        ObservableList<String> numbers = FXCollections.observableArrayList(txt219,txt373,txt308);
        numberComboBox = new ComboBox(numbers);
        numberComboBox.getSelectionModel().selectFirst();
        HBox sub = new HBox();
        sub.setSpacing(40);
        sub.getChildren().addAll(subjectLabel, subjectComboBox);
        HBox num = new HBox();
        num.setSpacing(39);
        num.getChildren().addAll(numberLabel, numberComboBox);
        subjectBox = new HBox();
        subjectBox.setSpacing(45);
        subjectBox.getChildren().addAll(sub, num);
        
        //THESE WILL HAVE SUBJECT AND SUBJECT NUMBER
        String semesterText = props.getProperty(CSGManagerProp.SEMESTER_TEXT.toString());
        semesterLabel = new Label(semesterText);
        String fall = props.getProperty(CSGManagerProp.FALL_TEXT.toString());
        String spring = props.getProperty(CSGManagerProp.SPRING_TEXT.toString());
        ObservableList<String> sem = FXCollections.observableArrayList(fall, spring);
        semesterComboBox = new ComboBox(sem);
        semesterComboBox.getSelectionModel().selectFirst();
        String yearText = props.getProperty(CSGManagerProp.YEAR_TEXT.toString());
        yearLabel = new Label(yearText);
        String txt2016 = props.getProperty(CSGManagerProp.TEXT_2016.toString());
        String txt2017 = props.getProperty(CSGManagerProp.TEXT_2017.toString());
        String txt2018 = props.getProperty(CSGManagerProp.TEXT_2018.toString());
        System.out.println(txt2018);
        
        ObservableList<String> years = FXCollections.observableArrayList(txt2016, txt2017, txt2018);
        yearComboBox = new ComboBox(years);
        yearComboBox.getSelectionModel().selectFirst();
        HBox sems = new HBox();
        sems.setSpacing(45);
        sems.getChildren().addAll(semesterLabel, semesterComboBox);
        HBox ys = new HBox();
        ys.setSpacing(50);
        ys.getChildren().addAll(yearLabel, yearComboBox);
        semesterBox = new HBox();
        semesterBox.setSpacing(25);
        semesterBox.getChildren().addAll(sems, ys);
        
        //THE COURSE TITLE
        String titleText = props.getProperty(CSGManagerProp.TITLE_TEXT.toString());
        titleLabel = new Label(titleText);
        titleField = new TextField();
        titleField.setPrefWidth(800);
        titleBox = new HBox();
        titleBox.setSpacing(70);
        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(titleField);
        
        //THE INSTRUCTOR NAME
        String nameText = props.getProperty(CSGManagerProp.NAME_TEXT.toString());
        instructorLabel = new Label(nameText);
        instructorField = new TextField();
        instructorField.setPrefWidth(800);
        instructorBox = new HBox();
        instructorBox.getChildren().add(instructorLabel);
        instructorBox.getChildren().add(instructorField);
        
        //THE INSTRUCTOR HOMEPAGE
        String homeText = props.getProperty(CSGManagerProp.HOME_TEXT.toString());
        homeLabel = new Label(homeText);
        homeField = new TextField();
        homeField.setPrefWidth(800);
        homeBox = new HBox();
        homeBox.getChildren().add(homeLabel);
        homeBox.getChildren().add(homeField);
        
        //THE CHANGE BUTTON
        String changeText = props.getProperty(CSGManagerProp.CHANGE_TEXT.toString());
        changeButton1 = new Button(changeText);
        
        //THE EXPORT DIRECTORY
        String exportText = props.getProperty(CSGManagerProp.EXPORT_TEXT.toString());
        exportLabel = new Label(exportText);
        String exportDirText = props.getProperty(CSGManagerProp.EXPORT_DIR_TEXT.toString());
        exportDirLabel = new Label(exportDirText);
        exportBox = new HBox();
        exportBox.setSpacing(16);
        exportBox.getChildren().addAll(exportLabel, exportDirLabel, changeButton1);
        
        //ORGANIZE THE COURSE INFO PANE
        courseInfoPane = new VBox();
        courseInfoPane.getChildren().add(courseInfoBox);
        courseInfoPane.getChildren().add(subjectBox);
        courseInfoPane.getChildren().add(semesterBox);
        courseInfoPane.getChildren().add(titleBox);
        courseInfoPane.getChildren().add(instructorBox);
        courseInfoPane.getChildren().add(homeBox);
        courseInfoPane.getChildren().add(exportBox);
        
        //THE SITE TEMPLATE HEADER
        String dirHeaderText = props.getProperty(CSGManagerProp.DIR_HEADER_TEXT.toString());
        siteTemplateLabel = new Label(dirHeaderText);
        siteTemplateBox = new HBox();
        siteTemplateBox.getChildren().add(siteTemplateLabel);
        
        //THE INFO TEXT
        String dirInfoText = props.getProperty(CSGManagerProp.DIR_INFO_TEXT.toString());
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
        CourseData data = ((WorkspaceData)app.getDataComponent()).getCourseData();
        ObservableList<Page> tableData = data.getPages();
        ObservableList<Boolean> checkBoxes = FXCollections.observableArrayList();
        for(Page p: tableData)
            checkBoxes.add(p.isUsed());
        table.setItems(tableData);
        String useColumnText = props.getProperty(CSGManagerProp.USE_COLUMN_TEXT.toString());
        String navbarColumnText = props.getProperty(CSGManagerProp.NAVBAR_COLUMN_TEXT.toString());
        String fileColumnText = props.getProperty(CSGManagerProp.FILE_COLUMN_TEXT.toString());
        String scriptColumnText = props.getProperty(CSGManagerProp.SCRIPT_COLUMN_TEXT.toString());
        useColumn = new TableColumn(useColumnText);
        navbarColumn = new TableColumn(navbarColumnText);
        fileColumn = new TableColumn(fileColumnText);
        scriptColumn = new TableColumn(scriptColumnText);
        
        useColumn.setCellValueFactory( new Callback<CellDataFeatures<Page, CheckBox>, ObservableValue<CheckBox>>() {

             @Override
             public ObservableValue<CheckBox> call(
                     CellDataFeatures<Page, CheckBox> arg) {
                 Page user = arg.getValue();
                 CheckBox checkBox = new CheckBox();
                 
                 for (Boolean value : checkBoxes) {
                    if(value==true){
                        checkBox.selectedProperty().setValue(Boolean.TRUE);
                    }
                }
                 
                 
                 return new SimpleObjectProperty<CheckBox>(checkBox);
             }
         
         
         });
        
        navbarColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("title")
        );
        fileColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("fileName")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<Page, String>("script")
        );
        table.getColumns().add(useColumn);
        table.getColumns().add(navbarColumn);
        table.getColumns().add(fileColumn);
        table.getColumns().add(scriptColumn);
        
        //EXTEND THE TABLE SO THAT IT ONLY SHOWS THE FIVE PAGES
       
        //ORGANIZE THE SITE TEMPLATE PANE
        template = new VBox();
        template.getChildren().add(siteTemplateBox);
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
        changeButton2 = new Button(changeText);
        schoolBannerBox = new HBox();
        schoolBannerBox.setSpacing(9);
        schoolBannerBox.getChildren().add(schoolBannerLabel);
        schoolBannerBox.getChildren().add(schoolBannerImage);
        schoolBannerBox.getChildren().add(changeButton2);
        
        //THE LEFT FOOTER IMAGE
        String leftFooterText = props.getProperty(CSGManagerProp.LEFT_FOOTER_TEXT.toString());
        leftFooterLabel = new Label(leftFooterText);
        leftFooterImage = new ImageView();
        changeButton3 = new Button(changeText);
        leftFooterBox = new HBox();
        leftFooterBox.setSpacing(15);
        leftFooterBox.getChildren().add(leftFooterLabel);
        leftFooterBox.getChildren().add(leftFooterImage);
        leftFooterBox.getChildren().add(changeButton3);
        
        //THE RIGHT FOOTER IMAGE
        String rightFooterText = props.getProperty(CSGManagerProp.RIGHT_FOOTER_TEXT.toString());
        rightFooterLabel = new Label(rightFooterText);
        rightFooterImage = new ImageView();
        changeButton4 = new Button(changeText);
        rightFooterBox = new HBox();
        rightFooterBox.setSpacing(10);
        rightFooterBox.getChildren().add(rightFooterLabel);
        rightFooterBox.getChildren().add(rightFooterImage);
        rightFooterBox.getChildren().add(changeButton4);
        
        //THE STYLESHEET CHOOSER COMBOBOX
        String stylesheetText = props.getProperty(CSGManagerProp.STYLESHEET_TEXT.toString());
        stylesheetLabel = new Label(stylesheetText);
        String extCss[] = {"css"};
        String PATH_WORK_CSS = "./work/css";
        File cssPath = new File(PATH_WORK_CSS);
        Collection<File> cssFiles = FileUtils.listFiles(cssPath, extCss, true);
        stylesheets = FXCollections.observableArrayList();
        for(File f: cssFiles)
            stylesheets.add(f.getName());
        stylesheetComboBox = new ComboBox(stylesheets);
        stylesheetComboBox.getSelectionModel().select(0);
        stylesheetBox = new HBox();
        stylesheetBox.setSpacing(25);
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
        coursePane = new VBox();
        
        coursePane.getChildren().add(courseInfoPane);
        coursePane.getChildren().add(template);
        coursePane.getChildren().add(style);
        
        ScrollPane pane = new ScrollPane(coursePane);
        pane.setFitToWidth(true);
        
        //PUT THE WHOLE COURSE PANE IN A TAB
        String courseTabText = props.getProperty(CSGManagerProp.COURSE_TAB_TEXT.toString());
        courseTab = new Tab(courseTabText);
        courseTab.setContent(pane);
        
        //PUT THE COURSE TAB IN THE TAB PANE
        masterWorkspace.getTabPane().getTabs().add(courseTab);
        
        CourseController controller = new  CourseController(app);
        
        // CONTROLS FOR ADDING TAs
        changeButton1.setOnAction(e -> {
            controller.handleExportDir();
        });
        
        selectTemplateButton.setOnAction(e -> {
            controller.handleTemplate();
        });
        
        changeButton2.setOnAction(e -> {
            controller.handleBannerImageImport();
        });
        
        changeButton3.setOnAction(e -> {
            controller.handleLeftImageImport();
        });
       
        changeButton3.setOnAction(e -> {
            controller.handleRightImageImport();
        });
    }
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT


    public Tab getCourseTab() {
        return courseTab;
    }

    public BorderPane getWorkspacePane() {
        return workspacePane;
    }

    public VBox getCoursePane() {
        return coursePane;
    }

    public VBox getCourseInfoPane() {
        return courseInfoPane;
    }

    public HBox getCourseInfoBox() {
        return courseInfoBox;
    }

    public Label getCourseInfoLabel() {
        return courseInfoLabel;
    }

    public HBox getSubjectBox() {
        return subjectBox;
    }

    public Label getSubjectLabel() {
        return subjectLabel;
    }

    public ComboBox getSubjectComboBox() {
        return subjectComboBox;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public ComboBox getNumberComboBox() {
        return numberComboBox;
    }

    public HBox getSemesterBox() {
        return semesterBox;
    }

    public Label getSemesterLabel() {
        return semesterLabel;
    }

    public ComboBox getSemesterComboBox() {
        return semesterComboBox;
    }

    public Label getYearLabel() {
        return yearLabel;
    }

    public ComboBox getYearComboBox() {
        return yearComboBox;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public HBox getInstructorBox() {
        return instructorBox;
    }

    public Label getInstructorLabel() {
        return instructorLabel;
    }

    public TextField getInstructorField() {
        return instructorField;
    }

    public HBox getHomeBox() {
        return homeBox;
    }

    public Label getHomeLabel() {
        return homeLabel;
    }

    public TextField getHomeField() {
        return homeField;
    }

    public HBox getExportBox() {
        return exportBox;
    }

    public Label getExportLabel() {
        return exportLabel;
    }

    public Label getExportDirLabel() {
        return exportDirLabel;
    }

    public Button getChangeButton1() {
        return changeButton1;
    }

    public VBox getTemplate() {
        return template;
    }

    public HBox getSiteTemplateBox() {
        return siteTemplateBox;
    }

    public Label getSiteTemplateLabel() {
        return siteTemplateLabel;
    }

    public HBox getDirInfoBox() {
        return dirInfoBox;
    }

    public Label getDirInfoLabel() {
        return dirInfoLabel;
    }

    public HBox getDirBox() {
        return dirBox;
    }

    public Label getDirLabel() {
        return dirLabel;
    }

    public HBox getSelectTemplateBox() {
        return selectTemplateBox;
    }

    public Button getSelectTemplateButton() {
        return selectTemplateButton;
    }

    public HBox getPagesBox() {
        return pagesBox;
    }

    public Label getPagesLabel() {
        return pagesLabel;
    }

    public TableView<Page> getTable() {
        return table;
    }

    public TableColumn<Page, CheckBox> getUseColumn() {
        return useColumn;
    }

    public TableColumn<Page, String> getNavbarColumn() {
        return navbarColumn;
    }

    public TableColumn<Page, String> getFileColumn() {
        return fileColumn;
    }

    public TableColumn<Page, String> getScriptColumn() {
        return scriptColumn;
    }

    public VBox getStyle() {
        return style;
    }

    public HBox getPageStyleBox() {
        return pageStyleBox;
    }

    public Label getPageStyleLabel() {
        return pageStyleLabel;
    }

    public HBox getSchoolBannerBox() {
        return schoolBannerBox;
    }

    public Label getSchoolBannerLabel() {
        return schoolBannerLabel;
    }

    public ImageView getSchoolBannerImage() {
        return schoolBannerImage;
    }

    public Button getChangeButton2() {
        return changeButton2;
    }

    public HBox getLeftFooterBox() {
        return leftFooterBox;
    }

    public Label getLeftFooterLabel() {
        return leftFooterLabel;
    }

    public ImageView getLeftFooterImage() {
        return leftFooterImage;
    }

    public Button getChangeButton3() {
        return changeButton3;
    }

    public HBox getRightFooterBox() {
        return rightFooterBox;
    }

    public Label getRightFooterLabel() {
        return rightFooterLabel;
    }

    public ImageView getRightFooterImage() {
        return rightFooterImage;
    }

    public Button getChangeButton4() {
        return changeButton4;
    }

    public HBox getStylesheetBox() {
        return stylesheetBox;
    }

    public Label getStylesheetLabel() {
        return stylesheetLabel;
    }

    public ObservableList<String> getStylesheets() {
        return stylesheets;
    }

    public ComboBox getStylesheetComboBox() {
        return stylesheetComboBox;
    }

    public Label getStyleNoteLabel() {
        return styleNoteLabel;
    }

    public HBox getStyleNoteBox() {
        return styleNoteBox;
    }
    
    
    
    public void resetWorkspace(){
        
        //RESET COMBO BOXES
        subjectComboBox.getSelectionModel().selectFirst();
        numberComboBox.getSelectionModel().selectFirst();
        semesterComboBox.getSelectionModel().selectFirst();
        yearComboBox.getSelectionModel().selectFirst();
        
        //CLEAR TEXT FIELDS AND LABELS
        titleField.clear();
        instructorField.clear();
        homeField.clear();
        exportDirLabel.setText("");
        dirLabel.setText("");
        
        //CLEAR IMAGES
        schoolBannerImage.setImage(null);
        leftFooterImage.setImage(null);
        rightFooterImage.setImage(null);
        
        stylesheetComboBox.getSelectionModel().selectFirst();
        
    }
    
    public void reloadWorkspace(AppDataComponent data){
        CourseData courseData = ((WorkspaceData)data).getCourseData();
        
        //RELAOD ALL COMPONENTS WITH NEWLY RELOADED DATA
        subjectComboBox.getSelectionModel().select(courseData.getSubject());
        numberComboBox.getSelectionModel().select(courseData.getNumber());
        semesterComboBox.getSelectionModel().select(courseData.getSemester());
        yearComboBox.getSelectionModel().select(courseData.getYear());
        subjectComboBox.getSelectionModel().select(courseData.getSubject());
        titleField.setText(courseData.getTitle());
        instructorField.setText(courseData.getInstructorName());
        homeField.setText(courseData.getInstructorHome());
        exportDirLabel.setText(courseData.getExportDir());
        dirLabel.setText(courseData.getTemplateDir());
        
       /* Image bannerImage = new Image(courseData.getSchoolImage());
        schoolBannerImage.setImage(bannerImage);
        schoolBannerImage.setFitHeight(50);
        schoolBannerImage.setFitWidth(150);
        
        Image leftImage = new Image(courseData.getLeftFooterImage());
        leftFooterImage.setImage(leftImage);
        leftFooterImage.setFitHeight(50);
        leftFooterImage.setFitWidth(150);
        
        Image rightImage = new Image(courseData.getRightFooterImage());
        rightFooterImage.setImage(rightImage);
        rightFooterImage.setFitHeight(50);
        rightFooterImage.setFitWidth(150);
        
        stylesheets.add(courseData.getStylesheet());
        stylesheetComboBox.getSelectionModel().select(0);*/
    }
}
    
