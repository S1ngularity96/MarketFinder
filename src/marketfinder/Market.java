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
public interface Market {
    @Override
    public String toString();
    
    public String getMarktname(); 

    public String getStadt();

    public String getPlz();

    public String getStraße();

    public String getHausnummer();
    
    public String getDistance();
    
    public String getTelefon();
    
    public String getWebseite();
    
    public String getBranche();
}
