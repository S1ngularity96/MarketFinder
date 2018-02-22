/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

/**
 * Wenn eine Webseite nicht korrekt geladen wird, dann 
 * wird die Exeption geworfen
 * @author Andrei
 */
public class PageLoadingException extends Exception{

    public PageLoadingException() {
        super("Seite konnte nicht geladen werden");
    }
    
    
}
