 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Livem
 */
public class GelbeSeitenMarkt extends Market{

    

    public GelbeSeitenMarkt(String marktname, String branche, String stadt, String plz, String strasse, String entfernung, String tele, String webseite) {
        
        super(marktname, stadt, plz, "", "", branche, entfernung, tele, webseite);
        this.strasse = new SimpleStringProperty(splitAddr(strasse)[0]);
        this.hausnummer = new SimpleStringProperty(splitAddr(strasse)[1]);
        
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

    @Override
    public String getBranche() {
        return branche.get();
    }

    @Override
    public String getWebseite() {
        return webseite.get();
    }

    @Override
    public String getEntfernung() {
        return entfernung.get();
    }

    @Override
    public String getTele() {
        return tele.get();
    }

    @Override
    public String toString() {
        return "-------------------------------------------"+
                "\nGelbeSeiten Markt\n" + 
                "\nMarktname: " + getMarktname() + 
                "\nStadt: " + getStadt() + 
                "\nPLZ: " + getPlz() + 
                "\nStra\u00dfe: " + getStrasse() + 
                "\nHausnummer: " + getHausnummer() +
                "\nTelefonnummer: "+ getTele()+
                "\nEntfernung: " + getEntfernung()+
                "\nBranche: "+ getBranche() +
                "\nWebseite: "+ getWebseite() + "\n";
    }
    
    
    
    /**
     * Teilt Addresse in Hausnummer und Straße.
     * Index 0 Straße
     * Index 1 Hausnummer
     * @param addr
     * @return Array mit einer Straße und einer Hausnummer
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
