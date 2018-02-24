/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.codegen.CompilerConstants;




/**
 *
 * @author Andrei
 */
public class MarketFinder extends Application {

    private String mainWindow = "Hauptfenster.fxml";
    
    private Stage primaryStage;
    
    

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        ladeFenster(mainWindow);
        primaryStage.show();
        
    }
    
    /**
    * Lädt ein neues Scene für die Stage
    * @param myWindow 
    */
    private void ladeFenster(String myWindow){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(myWindow));
        
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println("MainApplication kann die FXML- Datei nicht laden.");
            ex.printStackTrace();
            return;
        }
        
        
        
        
        
        
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("MarketFinder");
        primaryStage.setMinWidth(1070);
        primaryStage.setMinHeight(760);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
