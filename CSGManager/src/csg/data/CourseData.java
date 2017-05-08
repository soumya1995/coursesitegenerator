/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import csg.CSGManagerProp;
import csg.workspace.CourseWorkspace;
import csg.workspace.MasterWorkspace;
import csg.workspace.TAWorkspace;
import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class CourseData {
    
    CSGManager app;
    
    ObservableList<Page> pages;
    StringProperty subject;
    StringProperty number;
    StringProperty semester;
    StringProperty year;
    StringProperty title;
    StringProperty instructorName;
    StringProperty instructorHome;
    StringProperty exportDir;
    StringProperty templateDir;
    File schoolImage;
    File leftFooterImage;
    File rightFooterImage;
    StringProperty stylesheet;
    
    public CourseData(){
        
        pages = FXCollections.observableArrayList();
        ArrayList<Boolean> usedPages = new ArrayList<>();
        subject = new SimpleStringProperty();
        number = new SimpleStringProperty();
        semester = new SimpleStringProperty();
        year = new SimpleStringProperty();
        title = new SimpleStringProperty();
        instructorName = new SimpleStringProperty();
        instructorHome = new SimpleStringProperty();
        exportDir = new SimpleStringProperty();
        templateDir = new SimpleStringProperty();
        number = new SimpleStringProperty();
        stylesheet = new SimpleStringProperty();
       // for(int i=0; i<5; i++)
         //   usedPages.add(true);
      //  addRequiredPages(usedPages);
    }
    
    public CourseData(CSGManager initApp){
        
        app = initApp;
        pages = FXCollections.observableArrayList();
        ArrayList<Boolean> usedPages = new ArrayList<>();
        subject = new SimpleStringProperty();
        number = new SimpleStringProperty();
        semester = new SimpleStringProperty();
        year = new SimpleStringProperty();
        title = new SimpleStringProperty();
        instructorName = new SimpleStringProperty();
        instructorHome = new SimpleStringProperty();
        exportDir = new SimpleStringProperty();
        templateDir = new SimpleStringProperty();
        number = new SimpleStringProperty();
        stylesheet = new SimpleStringProperty();
       // for(int i=0; i<5; i++)
        //    usedPages.add(true);
       // addRequiredPages(usedPages);
    }
    
    
    public ObservableList<Page> getPages(){
        return pages;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getSemester() {
        return semester.get();
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getYear() {
        return year.get();
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getInstructorName() {
        return instructorName.get();
    }

    public void setInstructorName(String instructorName) {
        this.instructorName.set(instructorName);
    }

    public String getInstructorHome() {
        return instructorHome.get();
    }

    public void setInstructorHome(String instructoreHome) {
        this.instructorHome.set(instructoreHome);
    }

    public String getExportDir() {
        return exportDir.get();
    }

    public void setExportDir(String exportDir) {
        this.exportDir.set(exportDir);
    }

    public String getTemplateDir() {
        return templateDir.get();
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir.set(templateDir);
    }

    public String getSchoolImage() {
        if(schoolImage == null)
            return "file:./images/schoolBanner.png";
        return schoolImage.getPath();
    }

    public void setSchoolImage(String schoolImage) {
        this.schoolImage = new File(schoolImage);
    }

    public String getLeftFooterImage() {
        if(schoolImage == null)
            return "file:./images/left.jpg";
        return leftFooterImage.getPath();
    }

    public void setLeftFooterImage(String leftFooterImage) {
        this.leftFooterImage = new File(leftFooterImage);
    }

    public String getRightFooterImage() {
        if(schoolImage == null)
            return "file:./images/right.png";
        return rightFooterImage.getPath();
    }

    public void setRightFooterImage(String rightFooterImage) {
        this.rightFooterImage = new File(rightFooterImage);
    }

    public String getStylesheet() {
        return stylesheet.get();
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet.set(stylesheet);
    }
    

    public void addPage(boolean used, String title, String file, String script){
        
        pages.add(new Page(used, title, file, script));
    }
    
    public void addRequiredPages(ArrayList<Boolean> usedPages){
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String homeText = props.getProperty(CSGManagerProp.COURSE_HOME_TEXT.toString());
        String syllabusText = props.getProperty(CSGManagerProp.SYLLABUS_TEXT.toString());
        String scheduleText = props.getProperty(CSGManagerProp.SCHEDULE_TEXT.toString());
        String hwsText = props.getProperty(CSGManagerProp.HWS_TEXT.toString());
        String projectsText = props.getProperty(CSGManagerProp.PROJECTS_TEXT.toString());
        
        addPage(usedPages.get(0), homeText, "", "");
        addPage(usedPages.get(1), syllabusText, "", "");
        addPage(usedPages.get(2), scheduleText, "", "");
        addPage(usedPages.get(3), hwsText, "", "");
        addPage(usedPages.get(4), projectsText, "", "");
    }
    
    public void setPages(ArrayList<Boolean> usedPages){
        
        for(int i=0; i<5; i++){
            Page p = pages.get(i);
            p.setUsed(new SimpleBooleanProperty(usedPages.get(i)));
        }
    }
    
    public void resetData(){
        pages.clear();
        setSubject("");
        setNumber("");
        setSemester("");
        setYear("");
        setTitle("");
        setInstructorName("");
        setInstructorHome("");
        setExportDir("");
        setTemplateDir("");
        setStylesheet("");
        setSchoolImage("");
        setLeftFooterImage("");
        setRightFooterImage("");
    }
    
    
}
