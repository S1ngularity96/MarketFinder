/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

/**
 *
 * @author Livem
 */
public class ReweMarkt implements Market{
    
    // Wesentlichen Metadaten
    final String marktname;
    final String stadt;
    final String plz;
    final String straße;
    final String hausnummer;
    final String branche = "Lebensmittel";
    
    //geoDaten
    final String latidude;
    final String longitude;
    
    //Markttyp
    final String typ;
    
    //Öffnungszeiten
    final String tage;
    final String uhrzeit;
    
    //Distanz
    final String distance;
    
    //Telefon
    final String tele;

    //Webseite
    final String webseite;
    public ReweMarkt(String marktname, String stadt, String plz, String straße, String hausnummer, String latidude, String longitude, String typ, String tage, String uhrzeit, String distance,String tele,String webseite) {
        this.marktname = marktname;
        this.stadt = stadt;
        this.plz = plz;
        this.straße = straße;
        this.hausnummer = hausnummer;
        this.latidude = latidude;
        this.longitude = longitude;
        this.typ = typ;
        this.tage = tage;
        this.uhrzeit = uhrzeit;
        this.distance = distance;
        this.tele = tele;
        this.webseite = webseite;
    }

    public String getMarktname() {
        return marktname;
    }

    public String getStadt() {
        return stadt;
    }

    public String getPlz() {
        return plz;
    }

    public String getStraße() {
        return straße;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public String getLatidude() {
        return latidude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTyp() {
        return typ;
    }

    public String getTage() {
        return tage;
    }

    public String getDistance() {
        return distance;
    }
    
    public String getTelefon(){
        return tele;
    }
    
    public String getBranche(){
        return branche;
    }

    @Override
    public String toString() {
        return "-------------------------------------------"+
                "\nReweMarkt\n" + 
                "Allgemein:"+
                "\nMarktname: " + marktname + 
                "\nStadt: " + stadt + 
                "\nPLZ: " + plz + 
                "\nStra\u00dfe: " + straße + 
                "\nHausnummer: " + hausnummer +
                "\nTelefonnummer: "+ tele+
                "\nGeodaten:" +
                "\nLatidude: " + latidude + 
                "\nLongitude: " + longitude +
                "\nEntfernung: " + distance+
                "\nArt:"+
                "\nTyp: " + typ +
                "\nBranche: "+ branche +
                "\n\u00d6fnunngszeiten"+
                "\nTag: " + tage + 
                "\nUhrzeit: " + uhrzeit +
                "\nWebseite: "+ webseite + "\n";
    }

    @Override
    public String getWebseite() {
        return webseite;
    }
    
    
}
