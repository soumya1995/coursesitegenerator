/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import csg.data.CourseData;
import csg.data.WorkspaceData;
import csg.file.CSGFiles;
import csg.style.MasterStyle;
import csg.test_bed.TestSave;
import csg.workspace.CourseWorkspace;
import csg.workspace.MasterWorkspace;
import java.util.Locale;
import djf.AppTemplate;
import java.text.ParseException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Soumya
 */
public class CSGManager extends AppTemplate {
    
   public static final CountDownLatch latch = new CountDownLatch(1);
    /**
     * This hook method must initialize all four components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization.
     * 
     * 
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        
        dataComponent = new WorkspaceData(this);
        workspaceComponent = new MasterWorkspace(this);
        fileComponent = new CSGFiles(this);
        styleComponent = new MasterStyle(this);
        
       /* try {
            //SAVE HARD-CODED DATA
            TestSave save = new TestSave(this);
            save.saveRequest();
        } catch (ParseException ex) {
            Logger.getLogger(CSGManager.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
    }
    
    public static void loadStaticData() throws ParseException{
        
       
    }
    
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}

