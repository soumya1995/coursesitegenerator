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
import csg.data.TAData;
import csg.data.WorkspaceData;
import djf.components.AppDataComponent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ScheduleWorkspace {
    
     // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManager app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    ScheduleController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    Tab scheduleTab;
    BorderPane workspacePane;
    
    //ALL THE THREE COURSE PANES GOES INSIDE A VBox
    VBox schedulePane;
    
    //ALL THE ELEMENTS IN THE WORKSPACE ARE PLACED IN A PANE
    HBox scheduleHeaderBox;
    Label scheduleHeader;
    
    VBox calendarBox;
    Label calendarLabel;
    HBox startEndBox;
    Label startLabel;
    DatePicker startBox;
    Label endLabel;
    DatePicker endBox;
    
    
    VBox itemsBox;
    Label itemsLabel;
    HBox tableBox;
    TableView<Schedule> table;
    TableColumn<Schedule, String> typeColumn;
    TableColumn<Schedule, String> dateColumn;
    TableColumn<Schedule, String> titleColumn;
    TableColumn<Schedule, String> topicColumn;

    Label addEditLabel;
    
    HBox typeBox;
    Label typeLabel;
    ComboBox typeComboBox;
    
    HBox dateBox;
    Label dateLabel;
    DatePicker datePickerBox;
    
    HBox timeBox;
    Label timeLabel;
    TextField timeTextField;
    
    HBox titleBox;
    Label titleLabel;
    TextField titleTextField;
    
    HBox topicBox;
    Label topicLabel;
    TextField topicTextField;
    
    HBox linkBox;
    Label linkLabel;
    TextField linkTextField;
    
    HBox criteriaBox;
    Label criteriaLabel;
    TextField criteriaTextField;
    
    HBox buttonBox;
    Button addButton;
    Button clearButton;
    
    public ScheduleWorkspace(CSGManager initApp, MasterWorkspace masterWorkspace){
        //INITILIZE THE APP
        app = initApp;
        //INITILIZE THE PROPERTIES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //INITILIZE THE HEADER
        scheduleHeaderBox = new HBox();
        String scheduleHeaderText = props.getProperty(CSGManagerProp.SCHEDULE_HEADER_TEXT.toString());
        scheduleHeader = new Label(scheduleHeaderText);
        scheduleHeaderBox.getChildren().add(scheduleHeader);
        
        String calendarText = props.getProperty(CSGManagerProp.CALENDAR_TEXT.toString());
        calendarLabel = new Label(calendarText);
        
        String startDayText = props.getProperty(CSGManagerProp.START_DAY_TEXT.toString());
        startLabel = new Label(startDayText);
        startBox = new DatePicker();
        HBox start = new HBox();
        start.setSpacing(6);
        start.getChildren().addAll(startLabel, startBox);
        String endDayText = props.getProperty(CSGManagerProp.END_DAY_TEXT.toString());
        endLabel = new Label(endDayText);
        endBox = new DatePicker();
        HBox end = new HBox();
        end.setSpacing(6);
        end.getChildren().addAll(endLabel, endBox);
        startEndBox = new HBox();
        startEndBox.setSpacing(60);
        startEndBox.getChildren().addAll(start, end);
        
        calendarBox = new VBox();
        calendarBox.getChildren().addAll(calendarLabel, startEndBox);
        
        String itemsText = props.getProperty(CSGManagerProp.ITEMS_TEXT.toString());
        itemsLabel = new Label(itemsText);
        
        //INITILIZE THE TABLE
        table = new TableView();
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ScheduleData data = ((WorkspaceData)app.getDataComponent()).getScheduleData();
        ObservableList<Schedule> tableData = data.getSchedules();
        table.setItems(tableData);
        String typeColumnText = props.getProperty(CSGManagerProp.TYPE_COLUMN_TEXT.toString());
        String dateColumnText = props.getProperty(CSGManagerProp.DATE_COLUMN_TEXT.toString());
        String titleColumnText = props.getProperty(CSGManagerProp.TITLE_COLUMN_TEXT.toString());
        String topicColumnText = props.getProperty(CSGManagerProp.TOPIC_COLUMN_TEXT.toString());
        typeColumn = new TableColumn(typeColumnText);
        dateColumn = new TableColumn(dateColumnText);
        titleColumn = new TableColumn(titleColumnText);
        topicColumn = new TableColumn(topicColumnText);
        
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("type")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("date")
        );
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("title")
        );
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("topic")
        );
        
        table.getColumns().add(typeColumn);
        table.getColumns().add(dateColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(topicColumn);

        tableBox = new HBox();
        tableBox.getChildren().add(table);
        tableBox.setPadding(new Insets(5, 0, 25, 0));
        
        String addEditText = props.getProperty(CSGManagerProp.ADD_EDIT_TEXT.toString());
        addEditLabel = new Label(addEditText);
        
        String typeText = props.getProperty(CSGManagerProp.TYPE_TEXT.toString());
        typeLabel = new Label(typeText);
        String holiday = props.getProperty(CSGManagerProp.HOLIDAY_TEXT.toString());
        String lecture = props.getProperty(CSGManagerProp.LECTURE_TEXT.toString());
        String recitation = props.getProperty(CSGManagerProp.RECITATION_TEXT.toString());
        String reference = props.getProperty(CSGManagerProp.REFERENCE_TEXT.toString());
        String hw = props.getProperty(CSGManagerProp.HW_TEXT.toString());
        ObservableList<String> subjects = FXCollections.observableArrayList(holiday,lecture,recitation,hw,reference);
        typeComboBox = new ComboBox(subjects);
        typeComboBox.getSelectionModel().selectFirst();
        typeBox = new HBox();
        typeBox.setSpacing(50);
        typeBox.getChildren().addAll(typeLabel, typeComboBox);
        
        String dateText = props.getProperty(CSGManagerProp.DATE_TEXT.toString());
        dateLabel = new Label(dateText);
        datePickerBox = new DatePicker();
        datePickerBox.setValue((data.getEndDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateBox = new HBox();
        dateBox.setSpacing(50);
        dateBox.getChildren().addAll(dateLabel, datePickerBox);
        
        String timeText = props.getProperty(CSGManagerProp.TIME_TEXT.toString());
        timeLabel = new Label(timeText);
        timeTextField = new TextField();
        timeTextField.setPrefWidth(200);
        timeBox = new HBox();
        timeBox.setSpacing(50);
        timeBox.getChildren().addAll(timeLabel, timeTextField);
        
        String titleText = props.getProperty(CSGManagerProp.TITLE_TEXT.toString());
        titleLabel = new Label(titleText);
        titleTextField = new TextField();
        titleTextField.setPrefWidth(600);
        titleBox = new HBox();
        titleBox.setSpacing(44);
        titleBox.getChildren().addAll(titleLabel, titleTextField);
        
        String topicText = props.getProperty(CSGManagerProp.TOPIC_TEXT.toString());
        topicLabel = new Label(topicText);
        topicTextField = new TextField();
        topicTextField.setPrefWidth(600);
        topicBox = new HBox();
        topicBox.setSpacing(44);
        topicBox.getChildren().addAll(topicLabel, topicTextField);
        
        String linkText = props.getProperty(CSGManagerProp.LINK_TEXT.toString());
        linkLabel = new Label(linkText);
        linkTextField = new TextField();
        linkTextField.setPrefWidth(600);
        linkBox = new HBox();
        linkBox.setSpacing(50);
        linkBox.getChildren().addAll(linkLabel, linkTextField);
        
        String criteriaText = props.getProperty(CSGManagerProp.CRITERIA_TEXT.toString());
        criteriaLabel = new Label(criteriaText);
        criteriaTextField = new TextField();
        criteriaTextField.setPrefWidth(600);
        criteriaBox = new HBox();
        criteriaBox.setSpacing(20);
        criteriaBox.getChildren().addAll(criteriaLabel, criteriaTextField);
        
        String addText = props.getProperty(CSGManagerProp.ADD_TEXT.toString());
        addButton = new Button(addText);
        String clearText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        clearButton = new Button(clearText);
        buttonBox = new HBox();
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(addButton, clearButton);
        
        //PUT EVERYTHING IN THE ITEMS BOX
        itemsBox = new VBox();
        itemsBox.getChildren().addAll(itemsLabel, tableBox, addEditLabel, typeBox, dateBox, timeBox, titleBox, topicBox, linkBox, criteriaBox, buttonBox);
        
        //PUT ALL THREE ELEMENTS IN A PANE
        schedulePane = new VBox();
        schedulePane.getChildren().addAll(scheduleHeaderBox, calendarBox, itemsBox);
        
        //PUT THE RECITATION PANE IN A SCROLL PANE
        ScrollPane pane = new ScrollPane(schedulePane);
        pane.setFitToWidth(true);
        
        table.prefWidthProperty().bind(itemsBox.widthProperty().multiply(1.9));
        
        //PUT THE WHOLE RECITATION PANE IN A TAB
        String scheduleTabText = props.getProperty(CSGManagerProp.SCHEDULE_TAB_TEXT.toString());
        scheduleTab = new Tab(scheduleTabText);
        scheduleTab.setContent(pane);
        
        //PUT THE COURSE TAB IN THE TAB PANE
        masterWorkspace.getTabPane().getTabs().add(scheduleTab);
        
        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new ScheduleController(app);
        
        //SETTING UP CONTROLS
        
        startBox.setOnAction(e -> {
            try {
                controller.handleStartEndDate(Date.from(startBox.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endBox.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (ParseException ex) {
                Logger.getLogger(ScheduleWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            LocalDate date = (data.getStartDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            startBox.setValue(date);
           // return;
        });
        
        endBox.setOnAction(e -> {
            try {
                controller.handleStartEndDate(Date.from(startBox.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endBox.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (ParseException ex) {
                Logger.getLogger(ScheduleWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            LocalDate date = (data.getEndDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            endBox.setValue(date);
        });
        
        timeTextField.setOnAction(e -> {
            controller.handleAddSchedule();
        });
        
        titleTextField.setOnAction(e -> {
            controller.handleAddSchedule();
        });
        
        linkTextField.setOnAction(e -> {
            controller.handleAddSchedule();
        });
        
        criteriaTextField.setOnAction(e -> {
            controller.handleAddSchedule();
        });
        
        addButton.setOnAction(e -> {
            controller.handleAddSchedule();
        });
        
        clearButton.setOnAction(e -> {
            controller.handleClearSchedule();
        });
        
        table.setOnMouseClicked(e -> {
            controller.handleUpdateSchedule();
        });
        
        table.setFocusTraversable(true);
        table.setOnKeyPressed(e -> {
            controller.handleKeyPress(e);
        });
        
        app.getGUI().getUndoButton().setOnAction(e -> {
            controller.handleUndo();
        });
        
        app.getGUI().getRedoButton().setOnAction(e -> {
            controller.handleRedo();
        });
        
        
}

    public Tab getScheduleTab() {
        return scheduleTab;
    }

    public VBox getSchedulePane() {
        return schedulePane;
    }

    public HBox getScheduleHeaderBox() {
        return scheduleHeaderBox;
    }

    public Label getScheduleHeader() {
        return scheduleHeader;
    }

    public VBox getCalendarBox() {
        return calendarBox;
    }

    public Label getCalendarLabel() {
        return calendarLabel;
    }

    public HBox getStartEndBox() {
        return startEndBox;
    }

    public Label getStartLabel() {
        return startLabel;
    }

    public DatePicker getStartBox() {
        return startBox;
    }

    public Label getEndLabel() {
        return endLabel;
    }

    public DatePicker getEndBox() {
        return endBox;
    }

    public VBox getItemsBox() {
        return itemsBox;
    }

    public Label getItemsLabel() {
        return itemsLabel;
    }

    public HBox getTableBox() {
        return tableBox;
    }

    public TableView<Schedule> getTable() {
        return table;
    }

    public TableColumn<Schedule, String> getTypeColumn() {
        return typeColumn;
    }

    public TableColumn<Schedule, String> getDateColumn() {
        return dateColumn;
    }

    public TableColumn<Schedule, String> getTitleColumn() {
        return titleColumn;
    }

    public TableColumn<Schedule, String> getTopicColumn() {
        return topicColumn;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public HBox getTypeBox() {
        return typeBox;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }

    public ComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public HBox getDateBox() {
        return dateBox;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public DatePicker getDatePickerBox() {
        return datePickerBox;
    }

    public HBox getTimeBox() {
        return timeBox;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public TextField getTimeTextField() {
        return timeTextField;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public HBox getTopicBox() {
        return topicBox;
    }

    public Label getTopicLabel() {
        return topicLabel;
    }

    public TextField getTopicTextField() {
        return topicTextField;
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

    public HBox getCriteriaBox() {
        return criteriaBox;
    }

    public Label getCriteriaLabel() {
        return criteriaLabel;
    }

    public TextField getCriteriaTextField() {
        return criteriaTextField;
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
    
    

    public void resetWorkspace() {
       typeComboBox.getSelectionModel().selectFirst();
       timeTextField.setText("");
       titleTextField.setText("");
       topicTextField.setText("");
       criteriaTextField.setText("");
       
    }

    public void reloadWorkspace(AppDataComponent dataComponent) {
        
        ScheduleData scheduleData = ((WorkspaceData)dataComponent).getScheduleData();
        reloadSchedules(scheduleData);
    }
    
    public void reloadSchedules(ScheduleData data){
        
        //SET THE START AND END DATE
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        startBox.setValue(localDate(df.format(data.getStartDate())));
        endBox.setValue(localDate(df.format(data.getEndDate())));

        
        
    }
    
    public LocalDate localDate(String date){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}
