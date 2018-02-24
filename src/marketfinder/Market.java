/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import javafx.beans.property.SimpleStringProperty;

/**
 * Ein Interface für alle Märkte
 * @author Andrei
 */
public abstract class Market {
    
     SimpleStringProperty marktname;
     SimpleStringProperty stadt;
     SimpleStringProperty plz;
     SimpleStringProperty strasse;
     SimpleStringProperty hausnummer;
     SimpleStringProperty branche;
     SimpleStringProperty entfernung;
     SimpleStringProperty tele;
     SimpleStringProperty webseite;
    
         

    public Market(String marktname, String stadt, String plz, String strasse, String hausnummer, String branche, String entfernung, String tele, String webseite) {
        this.marktname = new SimpleStringProperty(marktname);
        this.stadt = new SimpleStringProperty(stadt);
        this.plz = new SimpleStringProperty(plz);
        this.strasse = new SimpleStringProperty(strasse);
        this.hausnummer = new SimpleStringProperty(hausnummer);
        this.branche = new SimpleStringProperty(branche);
        this.entfernung = new SimpleStringProperty(entfernung);
        this.tele = new SimpleStringProperty(tele);
        this.webseite = new SimpleStringProperty(webseite);
    }
    
    
    
    @Override
    public abstract String toString();
    
    
    public abstract String getMarktname(); 

    public abstract String getStadt();

    public abstract String getPlz();

    public abstract String getStrasse();

    public abstract String getHausnummer();
    
    public abstract  String getEntfernung();
    
    public abstract String getTele();
    
    public abstract  String getWebseite();
    
    public abstract  String getBranche();
}
