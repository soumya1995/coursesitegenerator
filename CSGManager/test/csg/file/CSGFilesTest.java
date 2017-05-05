/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

import csg.CSGManager;
import csg.data.CourseData;
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
import djf.components.AppDataComponent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.JsonObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Soumya
 */
public class CSGFilesTest {
    
    public CSGFilesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class CSGFiles.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
       
        WorkspaceData appData = new WorkspaceData();
        CSGFiles file = new CSGFiles();
        file.loadData(appData, "./work/SiteSaveTest.json");
        
        CourseData courseData = appData.getCourseData();
        TAData taData = appData.getTAData();
        RecitationData recData = appData.getRecitationData();
        ScheduleData scheduleData = appData.getScheduleData();
        TeamData teamData = appData.getTeamData();
        StudentData studentData = appData.getStudentData();
        testLoadCourseData(courseData);
        testLoadTAData(taData);
        testLoadRecitationData(recData);
        testLoadScheduleData(scheduleData);
        testLoadTeamData(teamData);
        testLoadStudentData(studentData);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of loadCourseData method, of class CSGFiles.
     */
    
    public void testLoadCourseData(CourseData data) throws Exception {
        
        assertEquals(data.getSubject(), "CSE");
        assertEquals(data.getNumber(), "308");
        assertEquals(data.getSemester(), "Fall");
        assertEquals(data.getYear(), "2017");
        assertEquals(data.getTitle(), "Software Engineering");
        assertEquals(data.getInstructorName(), "Richard Mckenna");
        assertEquals(data.getInstructorHome(), "http://www.rmckenna.com"); 
        assertEquals(data.getExportDir(), ".\\public_html"); 
        assertEquals(data.getTemplateDir(), ".\\work\\public_html");
        assertEquals(data.getSchoolImage(), "file:.\\images\\schoolBanner.png");
        assertEquals(data.getLeftFooterImage(), "file:.\\images\\left.jpg");
        assertEquals(data.getRightFooterImage(), "file:.\\images\\right.png");
             
    }

    /**
     * Test of loadRecitationData method, of class CSGFiles.
     */
    
    public void testLoadRecitationData(RecitationData data) throws Exception {
       ArrayList<Recitation> recTest = new ArrayList<>();
       recTest.add(new Recitation("R02", "Mckenna", "Wed 2:30pm-3:30pm", "Old CS 2114", "Ron Jeremy", "Rik Dermy"));
       recTest.add(new Recitation("R05", "Banerjee", "Tues 6:00pm-7:00pm", "Old CS 2114", "Eli Parx", "Govid Hihra"));
       
       int r=0;
       List<Recitation> rec = data.getRecitations();
       for(Recitation r1: rec){
           for(Recitation r2: recTest){
               if(r1.compareTo(r2)==0)
                   r++;
           }
       }
       assertEquals(r,2);
    }

    /**
     * Test of loadScheduleData method, of class CSGFiles.
     */
    
    public void testLoadScheduleData(ScheduleData data) throws Exception {
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        assertEquals(df.format(data.getStartDate()), "07/23/2017");
        assertEquals(df.format(data.getEndDate()), "12/15/2017");
        
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse("07/29/2017");
        Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse("08/29/2017");
        Date date3 = new SimpleDateFormat("MM/dd/yyyy").parse("09/11/2017");
        
        ArrayList<Schedule> schedule = new ArrayList<>();
         //SET SCHEDULE
        schedule.add(new Schedule("Lecture",date1, "Lecture 3", "Event Prog.", "2:30pm-4:30pm", "http://www3.cs.stonybrook.edu/~cse219/Section02/hw/HW5.html", "None"));
        schedule.add(new Schedule("Holiday",date2, "SNOW DAY", "", "", "", "None"));
        schedule.add(new Schedule("Lecture",date3, "Lecture 4", "OOP Prog.", "2:30pm-4:30pm", "http://www3.cs.stonybrook.edu/~cse219/Section02/hw/HW5.html", "None"));
        
        List<Schedule> test = data.getSchedules();
        
        int s=0;
        for(Schedule s1: schedule){
            for(Schedule s2: test){
                if(s1.compareTo(s2)==0)
                    s++;
            }
        }
        assertEquals(s,3);
        
    }

    /**
     * Test of loadProjectData method, of class CSGFiles.
     */
   
    public void testLoadTeamData(TeamData data) {
        List<Team> test = data.getTeams();
        
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(new Team("Atomic Comics", "552211", "ffffff", "http://atomicomic.com"));
        teams.add(new Team("C4 Comics", "fff000", "000000", "http://c4_comics.com"));
        
        int t=0;
        for(Team t1: teams){
            for(Team t2:test){
                if(t1.compareTo(t2)==0)
                    t++;
            }
        }
        assertEquals(t, 2);
        
        
    }
    
    public void testLoadStudentData(StudentData data) {
        List<Student> test = new ArrayList<>();
        
        test.add(new Student("Noonan", "Stiff", "C4 Comics", "Lead Designer"));
        test.add(new Student("Buae", "Conan", "Atomic Comics", "Lead Programmar"));
        
        List<Student> students = data.getStudents();
        
        int s=0;
        for(Student s1: test){
            for (Student s2: students){
                if(s1.compareTo(s2)==0)
                    s++;
            }
        }
        
        assertEquals(s,2);
    }

    /**
     * Test of loadTAData method, of class CSGFiles.
     */
    
    public void testLoadTAData(TAData data) throws Exception {
        
        assertEquals(data.getStartHour(), 10);
        assertEquals(data.getEndHour(), 19);
        
        int c=0;
        List<TeachingAssistant> test = new ArrayList<>();
        test.add(new TeachingAssistant(false,"Ron Jeremy", "ron.jermey@rimon.edu"));
        test.add(new TeachingAssistant(false,"Tim Frezee", "tim.freze@stonybrook.edy"));
        test.add(new TeachingAssistant(false,"Rik Dermy", "rik.dermy@stonybrook.ed"));
        test.add(new TeachingAssistant(false,"Piggy Rob", "piggy.rob@wiki.com"));
        test.add(new TeachingAssistant(false,"Eli Parx", "eli.parrx@stony.edu"));
        test.add(new TeachingAssistant(false,"Ronit Sya", "rnit.fur@gmail.com"));
        test.add(new TeachingAssistant(false,"Govid Hihra", "govid.cox@stoyt.edu"));
        test.add(new TeachingAssistant(false,"Leo Harais", "leo.wero@savy.com"));
        
        List<TeachingAssistant> tas = data.getTeachingAssistants();
        for(TeachingAssistant t1: tas){
            for(TeachingAssistant t2: test){
                if(t1.compareTo(t2)==0)
                    c++;
            }   
        }
        
        assertEquals(c,8);
        
        int office = 0;
            ArrayList<TimeSlot> officeHours = new ArrayList<>();
            officeHours.add(new TimeSlot("TUESDAY", "12_00pm", "Ron Jeremy"));
            officeHours.add(new TimeSlot("MONDAY", "1_00pm", "Eli Parx"));
            officeHours.add(new TimeSlot("TUESDAY", "12_30pm", "Ron Jeremy"));
            officeHours.add(new TimeSlot("FRIDAY", "10_00am", "Rick Dermy"));
            
             ArrayList<TimeSlot> officeHoursTest = data.getTestOfficeHours();
             for(TimeSlot t1: officeHours){
                 for(TimeSlot t2: officeHoursTest){
                     if(t1.compareTo(t2)==0){
                         office++;
                     }
                 }
             }
             assertEquals(office, 4);
    }

    /**
     * Test of saveData method, of class CSGFiles.
     */
    
    public void testSaveData() throws Exception {
        System.out.println("saveData");
        AppDataComponent data = null;
        String filePath = "";
        CSGFiles instance = null;
        instance.saveData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveCourseData method, of class CSGFiles.
     */
    
    public void testSaveCourseData() throws Exception {
        System.out.println("saveCourseData");
        AppDataComponent data = null;
        CSGFiles instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.saveCourseData(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveTAData method, of class CSGFiles.
     */
    
    public void testSaveTAData() throws Exception {
        System.out.println("saveTAData");
        AppDataComponent data = null;
        CSGFiles instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.saveTAData(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveRecitationData method, of class CSGFiles.
     */
    
    public void testSaveRecitationData() throws Exception {
        System.out.println("saveRecitationData");
        AppDataComponent data = null;
        CSGFiles instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.saveRecitationData(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveScheduleData method, of class CSGFiles.
     */
    
    public void testSaveScheduleData() throws Exception {
        System.out.println("saveScheduleData");
        AppDataComponent data = null;
        CSGFiles instance = null;
        JsonObject expResult = null;
        JsonObject result = instance.saveScheduleData(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveProjectData method, of class CSGFiles.
     */
    
    

    
    
}
