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
public class PageLoadingException extends Exception{

    public PageLoadingException() {
        super("Seite konnte nicht geladen werden");
    }
    
    
}
