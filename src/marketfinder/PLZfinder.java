/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Bei einer Eingabe einer Postleitzahl werden Vorschläge angezeigt
 * @author Livem
 */
public class PLZfinder {
    
    public PLZfinder(){
        
    }
    
 
public static String getHTML(String urlToRead) {
      URL url;
      HttpURLConnection conn;
      BufferedReader rd;
      String line;
      String result = "";
      try {
         url = new URL(urlToRead);
         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
         conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result += line;
         }
         rd.close();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result;
   }

  
  
  
  /**
   * 
   * @param postleitzahl Postleitzahl
   * @throws IOException 
   * @throws JSONException 
   * @return Postleitzahl, Stadt, Land
   */
  public ArrayList<String> setNewRequest(int postleitzahl) throws IOException, JSONException{
      JSONArray json = new JSONArray(getHTML("http://www.plz-suche.org/?api_region=de&action=plz&term="+postleitzahl));
      ArrayList<String> collected_data = collectData(json);
      return collected_data;
  }
  
  
  
  /**
   * Parsed das JSON- Objekt, und gibt eine Postleitzahl suche aus
   * @param object
   * @throws JSONException 
   */
  private ArrayList<String> collectData(JSONArray object) throws JSONException, NullPointerException{
     ArrayList<String> plz_Data = new ArrayList<>();
     
     JSONArray json_array = object;
     
      
     for(int array_position = 0; array_position < json_array.length(); array_position++){
         if(array_position == 19){
             return plz_Data;
         }else{
             JSONObject meta_object = json_array.getJSONObject(array_position);
             String meta_Data = meta_object.getString("zipcode") + ", "+
                                meta_object.getString("name")+ ", "+
                                meta_object.getString("land").toUpperCase();
             
             plz_Data.add(meta_Data);
         }
         
         
     }
     
     return plz_Data;
  }
    
}
