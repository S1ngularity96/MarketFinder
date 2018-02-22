/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Livem
 */
public class CSVExport {

    //CSV Kopfzeile
    private String CSVData = "Marktname;Branche;Stadt;PLZ;Stra\u00dfe;Hausnummer;Entfernung;Telefon;Webseite";

    public CSVExport() {

    }

    /**
     * Zeigt die Liste mit den Märkten an
     * @param markets 
     */
    public CSVExport(ArrayList<Market> markets) {
        exportCSVFromList(markets);
    }

    
    
    /**
     * Erstellt eine Liste aus Märkten
     *
     * @param markets Liste mit den Märkten
     */
    public void exportCSVFromList(ArrayList<Market> markets) {

        for (Market markt : markets) {
            CSVData = CSVData + "\n"
                    + "\""+ markt.getMarktname()+"\"" + ";"
                    + "\""+ markt.getBranche()+"\"" + ";"
                    + "\""+ markt.getStadt()+"\"" + ";"
                    + "\""+ markt.getPlz()+"\"" + ";"
                    + "\""+ markt.getStraße()+"\"" + ";"
                    + "\""+ markt.getHausnummer()+"\"" + ";"
                    + "\""+ markt.getDistance()+"\"" + ";"
                    + "\""+ markt.getTelefon()+"\"" + ";"
                    + "\""+ markt.getWebseite()+"\"";

        }

    }

    /**
     * Zeigt die CSV Datei an
     */
    public void printCSV() {
        System.out.println(this.CSVData);
    }

    /**
     * Legt eine CSV Datei auf der Festplatte ab
     * @throws IOException 
     */
    public void printToFile() throws IOException {
        OutputStream os = null;
        try {

            os = new FileOutputStream("ExportCSV.csv");
            os.write(239);
            os.write(187);
            os.write(191);
            PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));

            w.print(this.CSVData);
            w.flush();
            w.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVExport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVExport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
