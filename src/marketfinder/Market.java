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
     public final static long FILTER_MARTKNAME = 1;
     public final static long FILTER_STADT = 2;
     public final static long FILTER_PLZ = 4;
     public final static long FILTER_STRASSE = 8;
     public final static long FILTER_HAUSNUMMER=16;
     public final static long FILTER_BRANCHE=32;
     public final static long FILTER_ENTFERNUNG=64;
     public final static long FILTER_TELE=128;
     public final static long FILTER_WEBSEITE=256;
	
     SimpleStringProperty marktname;
     SimpleStringProperty stadt;
     SimpleStringProperty plz;
     SimpleStringProperty strasse;
     SimpleStringProperty hausnummer;
     SimpleStringProperty branche;
     SimpleStringProperty entfernung;
     SimpleStringProperty tele;
     SimpleStringProperty webseite;
     /*
      * filterHash is no hash in the usual way.
      * filterHash is there to check in a fast way, 
      * weather a market has a city, postalcode or
      * some other stuff set.
      */
     long filterHash;

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
        generateFilterHash();
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
    
    public void generateFilterHash() {
    	filterHash = 0;
    	if(!(getMarktname() == null || getMarktname().isEmpty())) {
    		filterHash |= FILTER_MARTKNAME;
    	}
    	if(!(getStadt() == null || getStadt().isEmpty())) {
    		filterHash |= FILTER_STADT;
    	}
    	if (!(getPlz() == null || getPlz().isEmpty())) {
    		filterHash |= FILTER_PLZ;
    	}
    	if (!(getStrasse() == null || getStrasse().isEmpty())) {
    		filterHash |= FILTER_STRASSE;
    	}
    	if (!(getHausnummer() == null || getHausnummer().isEmpty())) {
    		filterHash |= FILTER_HAUSNUMMER;
    	}
    	if (!(getEntfernung() == null || getEntfernung().isEmpty())) {
    		filterHash |= FILTER_ENTFERNUNG;
    	}
    	if (!(getTele() == null || getTele().isEmpty())) {
    		filterHash |= FILTER_TELE;
    	}
    	if (!(getWebseite() == null || getWebseite().isEmpty())) {
    		filterHash |= FILTER_WEBSEITE;
    	}
    	if (!(getBranche() == null || getBranche().isEmpty())) {
    		filterHash |= FILTER_BRANCHE;
    	}
    }
    
    public long getFilterHash() {
    	return filterHash;
    }
}
