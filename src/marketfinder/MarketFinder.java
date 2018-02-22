/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;


/**
 *
 * @author Livem
 */
public class MarketFinder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Market> liste = null;
        try {
            GelbeSeitenParser gelb = new GelbeSeitenParser();
            gelb.setRequestURL("edeka", "aachen", "52064");
            liste = gelb.commitRequestAndReceive();
            
            CSVExport export = new CSVExport(liste);
            try {
                export.printToFile();
            } catch (IOException ex) {
                System.err.println("Error writing File");
            }
            
        } catch (PageLoadingException ex) {
            System.err.println("Error");
        }
        
        
        
        
    
    }
    
    
}
