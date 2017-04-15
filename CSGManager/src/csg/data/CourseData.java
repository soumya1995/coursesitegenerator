/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGManager;
import csg.CSGManagerProp;
import java.io.File;
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
    StringProperty instructoreHome;
    StringProperty exportDir;
    StringProperty templateDir;
    File schoolImage;
    File leftFooterImage;
    File rightFooterImage;
    StringProperty stylesheet;
    
    public CourseData(CSGManager initApp){
        
        app = initApp;
        pages = FXCollections.observableArrayList();
        addRequiredPages();
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

    public String getInstructoreHome() {
        return instructoreHome.get();
    }

    public void setInstructoreHome(String instructoreHome) {
        this.instructoreHome.set(instructoreHome);
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
        return schoolImage.getPath();
    }

    public void setSchoolImage(String schoolImage) {
        this.schoolImage = new File(schoolImage);
    }

    public String getLeftFooterImage() {
        return leftFooterImage.getPath();
    }

    public void setLeftFooterImage(String leftFooterImage) {
        this.leftFooterImage = new File(leftFooterImage);
    }

    public String getRightFooterImage() {
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
    
    public void addRequiredPages(){
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String homeText = props.getProperty(CSGManagerProp.COURSE_HOME_TEXT.toString());
        String syllabusText = props.getProperty(CSGManagerProp.SYLLABUS_TEXT.toString());
        String scheduleText = props.getProperty(CSGManagerProp.SCHEDULE_TEXT.toString());
        String hwsText = props.getProperty(CSGManagerProp.HWS_TEXT.toString());
        String projectsText = props.getProperty(CSGManagerProp.PROJECTS_TEXT.toString());
        
        addPage(false, "Home", "", "");
        addPage(false, "Sylabus", "", "");
        addPage(false, "Schedule", "", "");
        addPage(false, "HWs", "", "");
        addPage(false, "Projects", "", "");
    }
    
    public void resetData(){
        pages.clear();
    }
    
}
