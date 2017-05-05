package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import java.util.HashMap;
import csg.CSGManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import properties_manager.PropertiesManager;
import csg.CSGManagerProp;
import csg.data.CourseData;
import csg.style.TAStyle;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.WorkspaceData;
import csg.style.MasterStyle;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 * @co-author Soumya Das
 */
public class TAWorkspace {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CSGManager app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;
    
    Tab taTab;
    SplitPane sPane;
    
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, Boolean> undergradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    Label startTimeLabel;
    Label endTimeLabel;
    
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    
    //COMBO BOXES
    ComboBox startTimeBox;
    ComboBox endTimeBox;

    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public TAWorkspace(CSGManager initApp, MasterWorkspace masterWorkspace) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        tasHeaderBox.setPadding(new Insets(5, 0, 5, 0));

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       // CourseData data1 = ((WorkspaceData)app.getDataComponent()).getCourseData();
        TAData data = ((WorkspaceData)app.getDataComponent()).getTAData();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGManagerProp.EMAIL_COLUMN_TEXT.toString());
        String undergradColumnText = props.getProperty(CSGManagerProp.UNDERGRAD_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        undergradColumn = new TableColumn(undergradColumnText);
        undergradColumn.setCellValueFactory(e -> e.getValue().isUndergrad());
        undergradColumn.setCellFactory(e -> new CheckBoxTableCell<>());            
        taTable.setEditable(true);
        
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(undergradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CSGManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGManagerProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(CSGManagerProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.3));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.setSpacing(6);
        addBox.setPadding(new Insets(15,1,3,0));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);
        

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        officeHoursHeaderBox.setSpacing(20);
        String officeHoursGridText = props.getProperty(CSGManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        HBox officeLabel = new HBox();
        officeLabel.getChildren().addAll(officeHoursHeaderLabel);
        String startTimeText = props.getProperty(CSGManagerProp. START_TIME_TEXT.toString());
        startTimeLabel = new Label(startTimeText);
        String endTimeText = props.getProperty(CSGManagerProp. END_TIME_TEXT.toString());
        endTimeLabel = new Label(endTimeText);
        
         //THESE WILL HAVE THE START AND END TIMES
        ObservableList<String> times = FXCollections.observableArrayList("12:00 am","1:00 am","2:00 am","3:00 am","4:00 am","5:00 am","6:00 am","7:00 am","8:00 am","9:00 am","10:00 am","11:00 am","12:00 pm","1:00 pm","2:00 pm","3:00 pm","4:00 pm","5:00 pm","6:00 pm","7:00 pm","8:00 pm","9:00 pm","10:00 pm","11:00 pm");      
        startTimeBox = new ComboBox(times);
        endTimeBox = new ComboBox(times);
        
       // System.out.println(data.getStartHour());
       // System.out.println(data.getEndHour());
        
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        HBox office = new HBox();
        office.setSpacing(20);
        office.getChildren().addAll(startTimeLabel, startTimeBox, endTimeLabel, endTimeBox);
        officeHoursHeaderBox.setSpacing(80);
        officeHoursHeaderBox.setPadding(new Insets(5,0, 10,10));
        officeHoursHeaderBox.getChildren().addAll(officeLabel, office);
        
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        
       
        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        ScrollPane pane = new ScrollPane(rightPane);
        pane.setFitToWidth(true);
        sPane = new SplitPane(leftPane, pane);
       // workspace = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
       // ((BorderPane) workspace).setCenter(sPane);
        
       //PUT THE WHOLE COURSE PANE IN A TAB
        String taTabText = props.getProperty(CSGManagerProp.TA_TAB_TEXT.toString());
        taTab = new Tab(taTabText);
        taTab.setContent(sPane);
       
        //PUT THE COURSE TAB IN THE TAB PANE
        masterWorkspace.getTabPane().getTabs().add(taTab);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(masterWorkspace.getWorkspace().heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });
        
        clearButton.setOnAction(e -> {
            controller.handleClearTA();
        });

        sPane.setFocusTraversable(true);
        sPane.setOnKeyPressed(e -> {
            controller.handleKeyPress(e);
        });
        
        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e);
        });
        
        taTable.setOnMouseClicked(e -> {
            controller.handleUpdateTA();
        });
        startTimeBox.setOnAction(e -> {
            controller.handleStartEndTime((startTimeBox.getSelectionModel().getSelectedIndex()), (endTimeBox.getSelectionModel().getSelectedIndex()));
            startTimeBox.getSelectionModel().select(data.getStartHour());
        });
        endTimeBox.setOnAction(e -> {
            controller.handleStartEndTime((startTimeBox.getSelectionModel().getSelectedIndex()), (endTimeBox.getSelectionModel().getSelectedIndex()));
            endTimeBox.getSelectionModel().select(data.getEndHour());
        });
        
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton(){
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }
    
    public Label getStartTimeLabel(){
        return startTimeLabel;
    }
    
    public Label getEndTimeLabel(){
        return endTimeLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public ComboBox getStartTimeBox(){
        return startTimeBox;
    }
    
    public ComboBox getEndTimeBox(){
        return endTimeBox;
    }
    
    public SplitPane getSplitPane(){
        return sPane;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = 0;
        if(militaryHour==0)
            hour = 12;
        else
        hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
                
    }
    
    public void reloadWorkspace(AppDataComponent dataComponent) {
        TAData taData = ((WorkspaceData)dataComponent).getTAData();
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();
        
        //LOAD THE COMBO BOXES
        
        startTimeBox.getSelectionModel().select(dataComponent.getStartHour());
        endTimeBox.getSelectionModel().select(dataComponent.getEndHour());
        
         //ADD TA NAME TO COMBOBOX IN RECITATION
            RecitationWorkspace recWorkspace = ((MasterWorkspace)app.getWorkspaceComponent()).getRecitationWorkspace();
            
            ObservableList<TeachingAssistant> teachingAssistants = dataComponent.getTeachingAssistants();
            
            for(TeachingAssistant t: teachingAssistants){
            recWorkspace.getTa1ComboBox().getItems().add(t.getName());
            }
            recWorkspace.getTa1ComboBox().getSelectionModel().selectFirst();
            recWorkspace.getTa2ComboBox().getSelectionModel().selectFirst();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.requestFocus();
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e);
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = ((MasterStyle)app.getStyleComponent()).getTAStyle();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
}
