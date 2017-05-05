/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.CSGManager;
import csg.data.CourseData;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.StudentData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.TeamData;
import csg.data.WorkspaceData;
import csg.file.CSGFiles;
import djf.AppTemplate;
import djf.components.AppFileComponent;
import djf.controller.AppFileController;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class TestSave {
    
    WorkspaceData appData;
    
    public TestSave() throws ParseException{
        
        appData = new WorkspaceData();
    }
    
    public void setData() throws ParseException{
        
        CourseData courseData = appData.getCourseData();
        TAData taData = appData.getTAData();
        RecitationData recData = appData.getRecitationData();
        ScheduleData scheduleData = appData.getScheduleData();
        TeamData teamData = appData.getTeamData();
        StudentData studentData = appData.getStudentData();
        setCourseData(courseData);
        setTAData(taData);
        setRecitationData(recData);
        setScheduleData(scheduleData);
        setTeamData(teamData);
        setStudentData(studentData);
        
    }
    
    public void setCourseData(CourseData data){
        
        data.setSubject("CSE");
        data.setNumber("308");
        data.setSemester("Fall");
        data.setYear("2017");
        data.setTitle("Software Engineering");
        data.setInstructorName("Richard Mckenna");
        data.setInstructorHome("http://www.rmckenna.com");
        data.setExportDir(".\\public_html");
        data.setTemplateDir(".\\work\\public_html"); 
        data.setSchoolImage("file:./images/schoolBanner.png");
        data.setLeftFooterImage("file:./images/left.jpg");
        data.setRightFooterImage("file:./images/right.png");
        data.setStylesheet("sea_wolf.css");
    }
       
    public void setTAData(TAData data){
        
        data.initHours("10", "19");
        
        //SET UP THE TAS
        data.addTA(true,"Ron Jeremy", "ron.jermey@rimon.edu");
        data.addTA(true, "Tim Frezee", "tim.freze@stonybrook.edy");
        data.addTA(true, "Rik Dermy", "rik.dermy@stonybrook.ed");
        data.addTA(true, "Piggy Rob", "piggy.rob@wiki.com");
        data.addTA(true, "Eli Parx", "eli.parrx@stony.edu");
        data.addTA(true, "Ronit Sya", "rnit.fur@gmail.com");
        data.addTA(true, "Govid Hihra", "govid.cox@stoyt.edu");
        data.addTA(false, "Leo Harais", "leo.wero@savy.com");
        
        //OFFICE HOURS ARE HARD CODED IN CSGFILES
        
        
               
    }
    
    public void setRecitationData(RecitationData data){
        
        //SET UP THE RECITATION
        data.addRecitation("R02", "Mckenna", "Wed 2:30pm-3:30pm", "Old CS 2114", "Ron Jeremy", "Rik Dermy");
        data.addRecitation("R05", "Banerjee", "Tues 6:00pm-7:00pm", "Old CS 2114", "Eli Parx", "Govid Hihra");
    }
    
    public void setScheduleData(ScheduleData data) throws ParseException{
        
        //SET THE START AND END DATE
        data.initDate("07/23/2017", "12/15/2017");
        
        //MAKE THE DATES
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse("07/29/2017");
        Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse("08/29/2017");
        Date date3 = new SimpleDateFormat("MM/dd/yyyy").parse("09/11/2017");
        
         //SET SCHEDULE
        data.addSchedule("Lecture",date1, "Lecture 3", "Event Prog.", "2:30pm-4:30pm", "http://www3.cs.stonybrook.edu/~cse219/Section02/hw/HW5.html", "None");
        data.addSchedule("Holiday",date2, "SNOW DAY", "", "", "", "None");
        data.addSchedule("Lecture",date3, "Lecture 4", "OOP Prog.", "2:30pm-4:30pm", "http://www3.cs.stonybrook.edu/~cse219/Section02/hw/HW5.html", "None");
        
    }
    
    public void setTeamData(TeamData data){
        
        data.addTeam("Atomic Comics", "552211", "ffffff", "http://atomicomic.com");
        data.addTeam("C4 Comics", "fff000", "000000", "http://c4_comics.com");
    }
    
    public void setStudentData(StudentData data){
        
        data.addStudent("Noonan", "Stiff", "C4 Comics", "Lead Designer");
        data.addStudent("Buae", "Conan", "Atomic Comics", "Lead Programmer");
    }
    
    public void saveRequest() throws IOException{
        CSGFiles file = new CSGFiles();
        file.saveData(appData, "./work/SiteSaveTest.json");
    }
    
    public static void main(String args[]) throws IOException, ParseException{
        TestSave test = new TestSave();
        test.setData();
        test.saveRequest();
    }
}
