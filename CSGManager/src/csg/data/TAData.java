package csg.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import properties_manager.PropertiesManager;
import csg.CSGManager;
import csg.CSGManagerProp;
import csg.file.TimeSlot;
import csg.workspace.MasterWorkspace;
import csg.workspace.TAWorkspace;
import djf.controller.AppFileController;

/**
 * This is the data component for CSGManager. It has all the data needed
 * to be set by the user via the User Interface and file I/O can set and get
 * all the data from this object
 * 
 * @author Richard McKenna
 * @co-author Soumya Das
 */
public class TAData {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CSGManager app;
    
    //NOTE THIS ARRAYLIST IS ONLY FOR JUNIT TESTING FOR OFFICE HOURS GRID DATA
    ArrayList<TimeSlot> testOfficeHours;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    
    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public final static int MIN_START_HOUR = 0;
    public final static int MAX_END_HOUR = 23;

    /**
     * This constructor will setup the required data structures for
     * use, but will have to wait on the office hours grid, since
     * it receives the StringProperty objects from the Workspace.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public TAData() {
        

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        
        testOfficeHours = new ArrayList<>();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
      //  officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = new ArrayList<>();
        timeHeaders.add("Start Time");
        timeHeaders.add("End Time");
        ArrayList<String> dowHeaders = new ArrayList<>();
        dowHeaders.add("MONDAY");
        dowHeaders.add("TUESDAY");
        dowHeaders.add("WEDNESDAY");
        dowHeaders.add("THURSDAY");
        dowHeaders.add("FRIDAY");
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
    public TAData(CSGManager initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGManagerProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGManagerProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
    }
    
    // ACCESSOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    
    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }
    

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }
    
    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }
    
    public ArrayList<TimeSlot> getTestOfficeHours(){
        return testOfficeHours;
    }
    
    public int getNumRows() {
        return (endHour - startHour)*2 + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = 0;
        if(militaryHour==0)
            hour = 12;
        else
            hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
       // if (hour < startHour)
       if(hour<startHour && time.contains("pm") && hour!=12)
            milHour += 12;
       if(hour>=startHour && hour!=12 && time.contains("pm"))
           row +=24;
        row += (milHour - startHour)*2;
        if (time.contains("_30"))
            row += 1;
        if(milHour>=endHour)
            return "";
        if(row>0)
            return getCellKey(col, row);
        else
            return "";
        
    }
    
    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    /**
     * This method is for giving this data manager the string property
     * for a given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }    
    
    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
                                int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    

    
    public void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;
        
        // EMPTY THE CURRENT OFFICE HOURS VALUES
        if(officeHours!=null)
            officeHours.clear();
            
        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        TAWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        workspaceComponent.resetWorkspace();
         workspaceComponent.reloadOfficeHoursGrid(this);
    }
    
    
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
        
    }
    
  


    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }



    public void addTA(Boolean use, String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(use, initName, initEmail);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                return;
            }
        }
    }
    
    
    public void addOfficeHoursReservation(String day, String time, String taName) {
        if(officeHours == null)
            addOfficeHoursJUitTest(day, time, taName);
        else{
        String cellKey = getCellKey(day, time);
        if(cellKey!="" && taName!= "")
            toggleTAOfficeHours(cellKey, taName);
        }

    }
    
    //THIS METHOD IS EXCLUSIVE FOR JUNIT TESTING
    public void addOfficeHoursJUitTest(String day, String time, String taName){
        
        testOfficeHours.add(new TimeSlot(day, time, taName));
    }

    /**
     * This function toggles the taName in the cell represented
     * by cellKey. Toggle means if it's there it removes it, if
     * it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }
    
    /**
     * This method removes taName from the office grid cell
     * represented by cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        }
        // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        }
        // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }
    public void replaceTAFromCell(StringProperty cellProp, String oldTAName, String newTAName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(oldTAName)) {
            cellProp.setValue(newTAName);
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(oldTAName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellText = newTAName+"\n"+cellText;
            cellProp.setValue(cellText);
        }
        // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(oldTAName) < cellText.indexOf("\n", cellText.indexOf(oldTAName))) {
            int startIndex = cellText.indexOf("\n" + oldTAName);
            int endIndex = startIndex + oldTAName.length() + 1;
            cellText = cellText.substring(0, startIndex) + "\n"+newTAName+cellText.substring(endIndex);
            cellProp.setValue(cellText);
        }
        // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + oldTAName);
            cellText = cellText.substring(0, startIndex);
            cellText = cellText+"\n"+newTAName;
            cellProp.setValue(cellText);
        }
    }
}