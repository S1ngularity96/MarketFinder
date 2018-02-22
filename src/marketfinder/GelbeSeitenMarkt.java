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
public class GelbeSeitenMarkt implements Market{

    // Wesentlichen Metadaten
    final String marktname;
    final String branche;
    final String stadt;
    final String plz;
          String strasse;
          String hausnummer;
          String entfernung;
          
    
    
    
    
    //Telefon
    final String tele;

    //Webseite
    final String webseite;

    public GelbeSeitenMarkt(String marktname, String branche, String stadt, String plz, String strasse, String entfernung, String tele, String webseite) {
        this.marktname = marktname;
        this.branche = branche;
        this.stadt = stadt;
        this.plz = plz;
        this.strasse = splitAddr(strasse)[0];
        this.hausnummer = splitAddr(strasse)[1];
        this.entfernung = entfernung;
        this.tele = tele;
        this.webseite = webseite;
    }

   

    public String getMarktname() {
        return marktname;
    }

    @Override
    public String getStadt() {
        return stadt;
    }

    @Override
    public String getPlz() {
        return plz;
    }

    @Override
    public String getStraße() {
        return strasse;
    }

    @Override
    public String getHausnummer() {
        return hausnummer;
    }

    @Override
    public String getBranche() {
        return branche;
    }

    @Override
    public String getWebseite() {
        return webseite;
    }

    @Override
    public String getDistance() {
        return entfernung;
    }

    @Override
    public String getTelefon() {
        return tele;
    }

    @Override
    public String toString() {
        return "-------------------------------------------"+
                "\nGelbeSeiten Markt\n" + 
                "\nMarktname: " + marktname + 
                "\nStadt: " + stadt + 
                "\nPLZ: " + plz + 
                "\nStra\u00dfe: " + strasse + 
                "\nHausnummer: " + hausnummer +
                "\nTelefonnummer: "+ tele+
                "\nEntfernung: " + entfernung+
                "\nBranche: "+ branche +
                "\nWebseite: "+ webseite + "\n";
    }
    
    
    
    /**
     * Teilt Addresse in Hausnummer und Straße.
     * Index 0 Straße
     * Index 1 Hausnummer
     * @param addr
     * @return 
     */
    private String[] splitAddr(String addr){
        String[] splittedAddr = {"",""};
        
        
        
        boolean firstDigit = false;
        for(int i = 0; i < addr.length(); i ++){
            if((int) addr.charAt(i) >= 48 && (int) addr.charAt(i) <= 57){ // Prüfe, ob eine Zahl auftaucht
                firstDigit = true;
            }
            
            if(firstDigit){
                splittedAddr[1] += addr.charAt(i);
            }else{
                splittedAddr[0] += addr.charAt(i);
            }
            
        }
        
        return splittedAddr;
        
    }
    
    
    
    
    
}
