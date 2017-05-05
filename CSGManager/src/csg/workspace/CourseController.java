/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGManager;
import static csg.CSGManagerProp.LOAD_IMAGE_TITLE;
import csg.data.CourseData;
import csg.data.TAData;
import csg.data.WorkspaceData;
import static djf.settings.AppPropertyType.EXPORT_WORK_TITLE;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static djf.settings.AppPropertyType.WORK_FILE_EXT;
import static djf.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jtps.jTPS;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 *
 * @author Soumya
 */
public class CourseController {
    
     CSGManager app;

    CourseController(CSGManager initApp) {
         // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    public void handleExportDir(){
        CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CourseData data = ((WorkspaceData)app.getDataComponent()).getCourseData();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
		// PROMPT THE USER FOR A PATH
		File selectedPath = directoryChooser();
		if (selectedPath != null) {
		    data.setExportDir(selectedPath.getPath());
	    }
        } catch (Exception ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }   
    }

   public void handleTemplate() {
       CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CourseData data = ((WorkspaceData)app.getDataComponent()).getCourseData();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        File selectedPath = null;
        String extHtml[] = {"html"};
        String extJs[] = {"js"};
        try {
		// PROMPT THE USER FOR A PATH & ADD ALL THE FILES PRESENT IN THE TABLE
		selectedPath = directoryChooser();
		if (selectedPath != null) {
		    data.setTemplateDir(selectedPath.getPath());
                    
                    //THE HTML FILES
                    Collection<File> htmlFiles = FileUtils.listFiles(selectedPath, extHtml, true);
                    Collection<File> jsFiles = FileUtils.listFiles(selectedPath, extJs, true);
                    for(File f:htmlFiles){
                        for(File j:jsFiles){
                            if((f.getName().equals("index.html") || f.getName().equals("home.html")) && j.getName().contains("Home"))
                                data.addPage(false, "Home", f.getName(), j.getName());
                            if(f.getName().contains("syllabus") && j.getName().contains("Syllabus"))
                                data.addPage(false, "Syllabus", f.getName(), j.getName());
                            if(f.getName().contains("schedule") && j.getName().contains("Schedule"))
                                data.addPage(false, "Schedule", f.getName(), j.getName());
                            if(f.getName().contains("hw") && j.getName().contains("HW"))
                                data.addPage(false, "HWs", f.getName(), j.getName());
                            if(f.getName().contains("project") && j.getName().contains("Project"))
                                data.addPage(false, "Projects", f.getName(), j.getName());
                    }
                 }
	    }
        } catch (Exception ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }   
        
      
            
    }
   
   public File directoryChooser(){
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       
       DirectoryChooser chooser = new DirectoryChooser();
       chooser.setTitle(props.getProperty(EXPORT_WORK_TITLE));
       File destDir = chooser.showDialog(app.getGUI().getWindow());
       return destDir;
   }

    public void handleBannerImageImport() {
        CourseData courseData = ((WorkspaceData)app.getDataComponent()).getCourseData();
        CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        
        File imageFile = fileChooser();
        courseData.setSchoolImage(imageFile.getPath());
        
        //SET THE IMAGE IN IMAGE VIEW
        Image image = new Image(imageFile.toURI().toString());
        workspace.getSchoolBannerImage().setImage(image);
        workspace.getSchoolBannerImage().setFitHeight(50);
        workspace.getSchoolBannerImage().setFitWidth(150);
    }
    
    public void handleLeftImageImport() {
        CourseData courseData = ((WorkspaceData)app.getDataComponent()).getCourseData();
        CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        
        File imageFile = fileChooser();
        courseData.setLeftFooterImage(imageFile.toURI().toString());
        
        //SET THE IMAGE IN IMAGE VIEW
        Image image = new Image(courseData.getLeftFooterImage());
        workspace.getSchoolBannerImage().setImage(image);
        workspace.getSchoolBannerImage().setFitHeight(50);
        workspace.getSchoolBannerImage().setFitWidth(150);
    }
    
    public void handleRightImageImport() {
        CourseData courseData = ((WorkspaceData)app.getDataComponent()).getCourseData();
        CourseWorkspace workspace = ((MasterWorkspace)app.getWorkspaceComponent()).getCourseWorkspace();
        
        File imageFile = fileChooser();
        courseData.setRightFooterImage(imageFile.getPath());
        
        //SET THE IMAGE IN IMAGE VIEW
        Image image = new Image(imageFile.toURI().toString());
        workspace.getSchoolBannerImage().setImage(image);
        workspace.getSchoolBannerImage().setFitHeight(50);
        workspace.getSchoolBannerImage().setFitWidth(150);
    }
    
    public File fileChooser(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_IMAGE_TITLE));
        fc.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
        
        return selectedFile;
    }
    
    
    
    
}
