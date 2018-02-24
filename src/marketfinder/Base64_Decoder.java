/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.util.Base64;



/**
 *
 * @author Andrei
 */
public class Base64_Decoder{
    
   
    private String decodedString;
    
    //Leerer Decoder
    public Base64_Decoder(){
    
    }
    
    public String decodeBase64(String src){
        byte decoded[] = Base64.getDecoder().decode(src);
        
        decodedString = new String(decoded);
        return decodedString;
    } 
    
    
    
}
