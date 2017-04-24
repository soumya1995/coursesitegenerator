package csg.file;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import csg.data.TAData;

/**
 * This class stores infor for a single TA-Day/Time mapping
 * for our office hours grid. It's useful to provide a list
 * of these objects for file I/O.
 * 
 * @author Richard McKenna
 */
public class TimeSlot <E extends Comparable<E>> implements Comparable<E>{
    private String day;
    private String time;
    private String name;
    
    public TimeSlot(String initDay, String initTime, String initName) {
        day = initDay;
        time = initTime;
        name = initName;
    }
    
    // ACCESSORS AND MUTATORS
    
    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getName() { return name; }
    public String getKey() {
        return day + " " + time + " " + name;
    }    
    
    public void setDay(String initDay) {
        day = initDay;
    }
    public void setTime(String initTime) {
        time = initTime;
    }
    public void setName(String initName) {
        name = initName;
    }
    
    @Override
    public int compareTo(E t){
        if(this.getDay().compareTo(((TimeSlot)t).getDay())==0 && this.getTime().compareTo(((TimeSlot)t).getTime())==0 && this.getTime().compareTo(((TimeSlot)t).getTime())==0)
            return 0;
        
        return -1;
    }
    
    /**
     * This function builds a list of all the office hours stored in 
     * the grid excluding empty time slots. This helps us do our file
     * input since it reflects how office hours are stored in the 
     * JSON file.
     */
    public static ArrayList<TimeSlot> buildOfficeHoursList(TAData data) {
        // BUILD AND RETURN THIS LIST
        ArrayList<TimeSlot> officeHoursList = new ArrayList();
        ArrayList<String> gridHeaders = data.getGridHeaders();
        HashMap<String, StringProperty> officeHours = data.getOfficeHours();
        if(officeHours == null)
            return null;
        for (int row = 1; row < data.getNumRows(); row++) {
            for (int col = 2; col < 7; col++) {
                // WE ONLY WANT THE DATA, NOTE THE HEADERS
                String cellKey = data.getCellKey(col, row);
                StringProperty timeSlotProp = officeHours.get(cellKey);
                String timeSlotText = timeSlotProp.getValue();
                String[] taNames = timeSlotText.split("\n");
                String day = gridHeaders.get(col);
                String timeCellKey = data.getCellKey(0, row);
                String time = officeHours.get(timeCellKey).getValue().replace(":", "_");
                for (int i = 0; i < taNames.length; i++) {
                    String taName = taNames[i];
                    if (taName.length() > 0) {
                        // ADD A TIME SLOT FOR EACH TA FOR EACH GRID CELL
                        TimeSlot ts = new TimeSlot(day, time, taName);
                        officeHoursList.add(ts);
                    }
                }
            }
        }
        return officeHoursList;
    }
}