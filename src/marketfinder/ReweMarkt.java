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
public class ReweMarkt extends Market{
    
    // Wesentlichen Metadaten
    
    
    //geoDaten
    final String latidude;
    final String longitude;
    
    //Markttyp
    final String typ;
    
    //Öffnungszeiten
    final String tage;
    final String uhrzeit;
    
    
    
    public ReweMarkt(String marktname, String stadt, String plz, String straße, String hausnummer, String latidude, String longitude, String typ, String tage, String uhrzeit, String distance,String tele,String webseite) {
        
        super(marktname, stadt, plz, straße, hausnummer, "Lebensmittel", distance, tele, webseite);
        this.latidude = latidude;
        this.longitude = longitude;
        this.typ = typ;
        this.tage = tage;
        this.uhrzeit = uhrzeit;
    }

    @Override
    public String getMarktname() {
        return marktname.get();
    }

    @Override
    public String getStadt() {
        return stadt.get();
    }

    @Override
    public String getPlz() {
        return plz.get();
    }

    @Override
    public String getStrasse() {
        return strasse.get();
    }

    @Override
    public String getHausnummer() {
        return hausnummer.get();
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

    @Override
    public String getEntfernung() {
        return entfernung.get();
    }
    
    @Override
    public String getTele(){
        return tele.get();
    }
    
    @Override
    public String getBranche(){
        return branche.get();
    }

    @Override
    public String toString() {
        return "-------------------------------------------"+
                "\nReweMarkt\n" + 
                "Allgemein:"+
                "\nMarktname: " + getMarktname() + 
                "\nStadt: " + getStadt() + 
                "\nPLZ: " + getPlz() + 
                "\nStra\u00dfe: " + getStrasse() + 
                "\nHausnummer: " + getHausnummer() +
                "\nTelefonnummer: "+ getTele()+
                "\nGeodaten:" +
                "\nLatidude: " + latidude + 
                "\nLongitude: " + longitude +
                "\nEntfernung: " + getEntfernung()+
                "\nArt:"+
                "\nTyp: " + typ +
                "\nBranche: "+ getBranche() +
                "\n\u00d6fnunngszeiten"+
                "\nTag: " + tage + 
                "\nUhrzeit: " + uhrzeit +
                "\nWebseite: "+ getWebseite() + "\n";
    }

    /**
     * Gemeint ist ein Link, der zu einer Seite vom
     * jeweiligen dargestellten Markt.
     * @return 
     */
    @Override
    public String getWebseite() {
        return webseite.get();
    }
    
    
}
