package csg.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import csg.CSGManager;
import csg.data.CourseData;
import csg.data.Page;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.Schedule;
import csg.data.ScheduleData;
import csg.data.Student;
import csg.data.StudentData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import csg.workspace.MasterWorkspace;
import csg.workspace.ScheduleWorkspace;
import csg.workspace.TAWorkspace;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObjectBuilder;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

/**
 * This class serves as the file component for the TA
 * manager app. It provides all saving and loading 
 * services for the application.
 * 
 * @author Soumya Das
 */
public class CSGFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    CSGManager app;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_SUBJECT = "subject";
    static final String JSON_NUMBER = "number";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_YEAR = "year";
    static final String JSON_TITLE = "title";
    static final String JSON_INSTRUCTOR_NAME = "instructor_name";
    static final String JSON_INSTRUCTOR_HOME = "instructor_home";
    static final String JSON_EXPORT_DIR = "export_dir";
    static final String JSON_TEMPLATE_DIR = "template_dir";
    static final String JSON_PAGES = "pages";
    static final String JSON_USED = "use";
    static final String JSON_BANNER = "banner_image";
    static final String JSON_LEFT_FOOTER = "left_footer_image";
    static final String JSON_RIGHT_FOOTER = "right_footer_image";
    static final String JSON_STYLESHEET = "stylesheet";
    
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta_1";
    static final String JSON_TA2 = "ta_2";
    static final String JSON_RECITATIONS = "recitations";
    
    static final String JSON_START_DATE = "startDate";
    static final String JSON_END_DATE = "endDate";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_SCHEDULE_TITLE = "scheduleTitle";
    static final String JSON_TOPIC = "topic";
    static final String JSON_LINK_SCHEDULE = "scheduleLink";
    static final String JSON_TIME_SCHEDULE = "scheduleTime";
    static final String JSON_CRITERIA = "criteria";
    static final String JSON_SCHEDULES = "schedules";
    
    static final String JSON_TEAM_NAME = "teamName";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXT_COLOR = "textColor";
    static final String JSON_LINK = "link";
    static final String JSON_TEAMS = "teams";
    
    static final String JSON_FIRST_NAME = "firstName";
    static final String JSON_LAST_NAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    static final String JSON_STUDENTS = "students";
    
    
    public CSGFiles(){
        
    }
    
    public CSGFiles(CSGManager initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        loadCourseData(data, json);
        loadRecitationData(data, json);
        loadTAData(data, json);
        try {
            loadScheduleData(data, json);
        } catch (ParseException ex) {
            Logger.getLogger(CSGFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadProjectData(data, json);
        
        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        if(app!=null)
            app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
    }
    
    public void loadCourseData(AppDataComponent data, JsonObject json) throws IOException{
        
        CourseData dataManager = ((WorkspaceData)data).getCourseData();
        
        // LOAD THE COURSE INFO
	String subject = json.getString(JSON_SUBJECT);
        dataManager.setSubject(subject);
        String number = json.getString(JSON_NUMBER);
        dataManager.setNumber(number);
        String semester = json.getString(JSON_SEMESTER);
        dataManager.setSemester(semester);
        String year = json.getString(JSON_YEAR);
        dataManager.setYear(year);
        String title = json.getString(JSON_TITLE);
        dataManager.setTitle(title);
        String instructor = json.getString(JSON_INSTRUCTOR_NAME);
        dataManager.setInstructorName(instructor);
        String instructorHome = json.getString(JSON_INSTRUCTOR_HOME);
        dataManager.setInstructorHome(instructorHome);
        String exportDir = json.getString(JSON_EXPORT_DIR);
        dataManager.setExportDir(exportDir);
        String templateDir = json.getString(JSON_TEMPLATE_DIR);
        dataManager.setTemplateDir(templateDir);
        String banner = json.getString(JSON_BANNER);
        dataManager.setSchoolImage(banner);
        String leftFooter = json.getString(JSON_LEFT_FOOTER);
        dataManager.setLeftFooterImage(leftFooter);
        String rightFooter = json.getString(JSON_RIGHT_FOOTER);
        dataManager.setRightFooterImage(rightFooter);
        String stylesheet = json.getString(JSON_STYLESHEET);
        dataManager.setStylesheet(stylesheet);
        
        // NOW LOAD ALL THE TEMPLATE PAGES
        ArrayList<Boolean> usedPages = new ArrayList<>();
        JsonArray jsonArray = json.getJsonArray(JSON_PAGES);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonPage = jsonArray.getJsonObject(i);
            String use = jsonPage.getString(JSON_USED);
            if(use.equals("true"))
                usedPages.add(true);
            else
                usedPages.add(false);
        }
        
        dataManager.setPages(usedPages);
    }
    
    public void loadRecitationData(AppDataComponent data, JsonObject json) throws IOException{
        
        RecitationData dataManager = ((WorkspaceData)data).getRecitationData();
        
        // LOAD ALL THE RECITATIONS
        JsonArray jsonArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonRec = jsonArray.getJsonObject(i);
            String sec = jsonRec.getString(JSON_SECTION);
            String ins = jsonRec.getString(JSON_INSTRUCTOR);
            String day = jsonRec.getString(JSON_DAY_TIME);
            String loc = jsonRec.getString(JSON_LOCATION);
            String ta1 = jsonRec.getString(JSON_TA1);
            String ta2 = jsonRec.getString(JSON_TA2);
            dataManager.addRecitation(sec, ins, day, loc, ta1, ta2);
        }
        
    }
    
    public void loadScheduleData(AppDataComponent data, JsonObject json) throws IOException, ParseException{
        
        ScheduleData dataManager = ((WorkspaceData)data).getScheduleData();
        
        // LOAD THE START AND END HOURS
	String startDate = json.getString(JSON_START_DATE);
        String endDate = json.getString(JSON_END_DATE);
        dataManager.initDate(startDate, endDate);
        
        if(app!=null){
        ScheduleWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getScheduleWorkspace();
        workspaceComponent.resetWorkspace();
        workspaceComponent.reloadSchedules(dataManager);
        }
        
        // LOAD ALL THE SCHEDULE
        JsonArray jsonArray = json.getJsonArray(JSON_SCHEDULES);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonSchedule = jsonArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_TYPE);
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(jsonSchedule.getString(JSON_DATE));
            String title = jsonSchedule.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonSchedule.getString(JSON_TOPIC);
            String time = jsonSchedule.getString(JSON_TIME_SCHEDULE);
            String link = jsonSchedule.getString(JSON_LINK_SCHEDULE);
            String criteria = jsonSchedule.getString(JSON_CRITERIA);
            dataManager.addSchedule(type, date, title, topic, time, link, criteria);
        }
    }
    
     public void loadProjectData(AppDataComponent data, JsonObject json){
        
        TeamData dataManager = ((WorkspaceData)data).getTeamData();
        StudentData studentData = ((WorkspaceData)data).getStudentData();
        
        //LOAD THE TEAMS
        JsonArray jsonArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonTeam = jsonArray.getJsonObject(i);
            String team = jsonTeam.getString(JSON_TEAM_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_LINK);
            dataManager.addTeam(team, color, textColor, link);
        }
        
        //LOAD THE STUDENTS
        JsonArray jsonArrayStudent = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonArrayStudent.size(); i++) {
            JsonObject jsonTeam = jsonArrayStudent.getJsonObject(i);
            String firstName = jsonTeam.getString(JSON_FIRST_NAME);
            String lastName = jsonTeam.getString(JSON_LAST_NAME);
            String studentTeam = jsonTeam.getString(JSON_TEAM);
            System.out.print(studentTeam);
            String role = jsonTeam.getString(JSON_ROLE);
            studentData.addStudent(firstName, lastName, studentTeam, role);
        }
     }
    
    public void loadTAData(AppDataComponent data, JsonObject json) throws IOException{
        
        TAData dataManager = ((WorkspaceData)data).getTAData();
        
        // LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);
        
        if(app!=null){
        TAWorkspace workspaceComponent = ((MasterWorkspace)app.getWorkspaceComponent()).getTAWorkspace();
        workspaceComponent.resetWorkspace();
        workspaceComponent.reloadOfficeHoursGrid(dataManager);
        }

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
       
        JsonObject courseDataJSO = saveCourseData(data);
	JsonObject TADataJSO = saveTAData(data);
        JsonObject recitationDataJSO = saveRecitationData(data);
        JsonObject scheduleDataJSO = saveScheduleData(data);
        JsonObject projectDataJSO = saveProjectData(data);
        
        JsonObjectBuilder objectBuilder =Json.createObjectBuilder();
        for (String key : courseDataJSO.keySet()){
            objectBuilder.add(key, courseDataJSO.get(key));
        }
        for (String key : TADataJSO.keySet()){
            objectBuilder.add(key, TADataJSO.get(key));
        }
        for (String key : recitationDataJSO.keySet()){
            objectBuilder.add(key, recitationDataJSO.get(key));
        }
        for (String key : scheduleDataJSO.keySet()){
            objectBuilder.add(key, scheduleDataJSO.get(key));
        }
        for (String key : projectDataJSO.keySet()){
            objectBuilder.add(key, projectDataJSO.get(key));
        }
        
        JsonObject dataManagerJSO = objectBuilder.build();
        
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        System.out.println("Saving successful");
    }
    
    public JsonObject saveCourseData(AppDataComponent data) throws IOException {
        
        // GET THE DATA
	CourseData dataManager = ((WorkspaceData)data).getCourseData();
        
        // NOW BUILD THE COURSE JSON OBJCTS TO SAVE
	JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
	ObservableList<Page> pages = dataManager.getPages();
        for(Page p:pages){
            JsonObject pJson = Json.createObjectBuilder()
		    .add(JSON_USED, p.isUsed().toString()).build();
	    courseArrayBuilder.add(pJson);
        }
        JsonArray pagesArray = courseArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_SUBJECT, "" + dataManager.getSubject())
		.add(JSON_NUMBER, "" + dataManager.getNumber())
                .add(JSON_SEMESTER, "" + dataManager.getSemester())
                .add(JSON_YEAR, "" + dataManager.getYear())
                .add(JSON_TITLE, "" + dataManager.getTitle())
                .add(JSON_INSTRUCTOR_NAME, "" + dataManager.getInstructorName())
                .add(JSON_INSTRUCTOR_HOME, "" + dataManager.getInstructorHome())
                .add(JSON_EXPORT_DIR, "" + dataManager.getExportDir())
                .add(JSON_TEMPLATE_DIR, "" + dataManager.getTemplateDir())
                .add(JSON_BANNER, "" + dataManager.getSchoolImage())
                .add(JSON_LEFT_FOOTER, "" + dataManager.getLeftFooterImage())
                .add(JSON_RIGHT_FOOTER, "" + dataManager.getRightFooterImage())
                .add(JSON_STYLESHEET, "" + dataManager.getStylesheet())
                .add(JSON_PAGES, pagesArray)
		.build();
        
        return dataManagerJSO;
    }
    
    public JsonObject saveTAData(AppDataComponent data) throws IOException {
        
        // GET THE DATA
	TAData dataManager = ((WorkspaceData)data).getTAData();

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        if(officeHours == null){
            
            officeHours = new ArrayList<>();
            officeHours.add(new TimeSlot("TUESDAY", "12_00pm", "Ron Jeremy"));
            officeHours.add(new TimeSlot("MONDAY", "1_00pm", "Eli Parx"));
            officeHours.add(new TimeSlot("TUESDAY", "12_30pm", "Ron Jeremy"));
            officeHours.add(new TimeSlot("FRIDAY", "10_00am", "Rick Dermy"));
        }
            
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        
        return dataManagerJSO;
    }
    
    public JsonObject saveRecitationData(AppDataComponent data) throws IOException {
        
        // GET THE DATA
	RecitationData dataManager = ((WorkspaceData)data).getRecitationData();

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recs = dataManager.getRecitations();
        for (Recitation rec : recs) {	    
	    JsonObject recJson = Json.createObjectBuilder()
		    .add(JSON_SECTION, rec.getSection())
		    .add(JSON_INSTRUCTOR, rec.getInstructor())
                    .add(JSON_DAY_TIME, rec.getDay())
                    .add(JSON_LOCATION, rec.getLocation())
                    .add(JSON_TA1, rec.getTa1())
                    .add(JSON_TA2, rec.getTa2()).build();
	    recArrayBuilder.add(recJson);
	}
	JsonArray recitationArray = recArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_RECITATIONS, recitationArray)
		.build();
        
        return dataManagerJSO;
        
    }
    
   public JsonObject saveScheduleData(AppDataComponent data) throws IOException {
        
        // GET THE DATA
	ScheduleData dataManager = ((WorkspaceData)data).getScheduleData();

	// NOW BUILD THE SCHEDULE JSON OBJCTS TO SAVE
	JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        Format formater = new SimpleDateFormat("MM/dd/yyyy");
	ObservableList<Schedule> schedule = dataManager.getSchedules();
        for (Schedule s : schedule) {	
	    JsonObject sJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, s.getType())
		    .add(JSON_DATE, s.getDate())
                    .add(JSON_SCHEDULE_TITLE, s.getTitle())
                    .add(JSON_TOPIC, s.getTopic())
                    .add(JSON_TIME_SCHEDULE, s.getTime())
                    .add(JSON_LINK_SCHEDULE, s.getLink())
                    .add(JSON_CRITERIA, s.getCriteria()).build();
	    scheduleArrayBuilder.add(sJson);
	}
	JsonArray scheduleArray = scheduleArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_DATE, "" + formater.format(dataManager.getStartDate()))
                .add(JSON_END_DATE, "" + formater.format(dataManager.getEndDate()))
		.add(JSON_SCHEDULES, scheduleArray)
		.build();
        
        return dataManagerJSO;
   }
   
   public JsonObject saveProjectData(AppDataComponent data) throws IOException {
        
        // GET THE TEAM DATA
	TeamData teamDataManager = ((WorkspaceData)data).getTeamData();

	// NOW BUILD THE SCHEDULE JSON OBJCTS TO SAVE
	JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> team = teamDataManager.getTeams();
        for (Team t : team) {	
	    JsonObject tJson = Json.createObjectBuilder()
		    .add(JSON_TEAM_NAME, t.getName())
                    .add(JSON_COLOR, t.getColor())
                    .add(JSON_TEXT_COLOR, t.getTextColor())
                    .add(JSON_LINK, t.getLink()).build();
	    teamArrayBuilder.add(tJson);
	}
	JsonArray teamArray = teamArrayBuilder.build();
        
        // GET THE STUDENT DATA
	StudentData studentDataManager = ((WorkspaceData)data).getStudentData();

	// NOW BUILD THE STUDENT JSON OBJCTS TO SAVE
	JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> student = studentDataManager.getStudents();
        for (Student s : student) {	
	    JsonObject sJson = Json.createObjectBuilder()
		    .add(JSON_FIRST_NAME, s.getFirstName())
                    .add(JSON_LAST_NAME, s.getLastName())
                    .add(JSON_TEAM, s.getTeam())
                    .add(JSON_ROLE, s.getRole()).build();
	    studentArrayBuilder.add(sJson);
	}
	JsonArray studentArray = studentArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();
        
        return dataManagerJSO;
   }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String srcPath) throws IOException {
        
        File srcDir = new File(srcPath);
        
        CourseData cdata = ((WorkspaceData)app.getDataComponent()).getCourseData();
        File destDir = new File(cdata.getExportDir());
        FileUtils.copyDirectory(srcDir, destDir);
    }
    
    public void saveJsonFiles(AppDataComponent data, String jsonDest) throws FileNotFoundException{
         CourseData cdata = ((WorkspaceData)app.getDataComponent()).getCourseData();
         String filePath = cdata.getExportDir()+jsonDest;
         saveJsonRecitation(data, filePath);
    }
    
    public void saveJsonRecitation(AppDataComponent data, String filePath) throws FileNotFoundException{
        
        filePath = filePath+"RecitationsData.json";
        
        RecitationData dataManager = ((WorkspaceData)data).getRecitationData();

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recs = dataManager.getRecitations();
        for (Recitation rec : recs) {	    
	    JsonObject recJson = Json.createObjectBuilder()
		    .add(JSON_SECTION, rec.getSection()+"</strong> ("+rec.getInstructor()+")")
                    .add(JSON_DAY_TIME, rec.getDay())
                    .add(JSON_LOCATION, rec.getLocation())
                    .add(JSON_TA1, rec.getTa1())
                    .add(JSON_TA2, rec.getTa2()).build();
	    recArrayBuilder.add(recJson);
	}
	JsonArray recitationArray = recArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_RECITATIONS, recitationArray)
		.build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
    }
}