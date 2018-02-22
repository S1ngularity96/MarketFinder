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
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;





public class ReweParser {

      
    
    
    
  /**
   * Lese den Inhalt einer JSON-Response
   * @param rd Reader
   * @return Inhalt der Datei
   * @throws IOException 
   */    
  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  
  /**
   * Erstelle ein JSONObject mit allen Informationen aus der Google-Maps API
   * @param url
   * @return
   * @throws IOException
   * @throws JSONException 
   */
  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  /**
   * 
   * @param URL Google- MAPS API Request URL
   * @throws IOException
   * @throws JSONException 
   */
  public ReweParser(String URL)throws IOException, JSONException {
      JSONObject json = readJsonFromUrl(URL);
      collectData(json);
      
  }
  
  // leerer Default Konstruktor
  public ReweParser(){}
  
  /**
   * 
   * @param postleitzahl Postleitzahl für die REWE Märkte
   * @return Eine Liste mit allen Rewe- Märkten
   * @throws IOException 
   * @throws JSONException 
   */
  public ArrayList<Market> setNewRequest(int postleitzahl) throws IOException, JSONException{
      JSONObject json =  readJsonFromUrl("https://shop.rewe.de/mc/api/markets-stationary/"+postleitzahl+"/");
      return collectData(json);
  }
  
  /**
   * 
   * @param URL Eine URL mit der GET- Anfrage bei der 
   * REWE- Marktsuche
   * @return Eine Liste mit allen Rewe- Märkten
   * @throws IOException
   * @throws JSONException 
   */
  public ArrayList<Market> setNewRequest(String URL) throws IOException, JSONException{
      JSONObject json =  readJsonFromUrl(URL);
      return collectData(json);
  }
  
  /**
   * Parsed das JSON- Objekt, das von REWE Marktsuche zurück
   * gegeben wird und gibt anschließen eine Liste mit den
   * einzelnen Märkten zurück.
   * @param object
   * @throws JSONException 
   */
  private ArrayList<Market> collectData(JSONObject object) throws JSONException, NullPointerException{
      ArrayList<Market> liste = new ArrayList<>();
      
      JSONObject rewe = object;
      JSONArray markets = rewe.getJSONArray("stationaryMarkets");
      
      for(int market = 0; market < markets.length(); market++){
          JSONObject reweMarket = markets.getJSONObject(market);
          JSONObject addr = reweMarket.getJSONObject("address");
          JSONObject geo = reweMarket.getJSONObject("geoLocation");
          JSONObject company = reweMarket.getJSONObject("type");
          JSONObject opening = reweMarket.getJSONObject("openingHours");
          JSONArray openDetails = opening.getJSONArray("condensed");
          
          //Hinzufügen von einzelnen Elementen zum ReweMarkt
          liste.add(new ReweMarkt(reweMarket.getString("name"), 
                  addr.getString("city"), 
                  addr.getString("postalCode"), 
                  addr.getString("street"), 
                  addr.getString("houseNumber"), 
                  geo.getString("latitude"), 
                  geo.getString("longitude"), 
                  company.getString("name"), 
                  openDetails.getJSONObject(0).getString("days"), 
                  openDetails.getJSONObject(0).getString("hours"), 
                  reweMarket.getString("distance"),
                  reweMarket.getString("phone"),getWebsiteByMarketId(reweMarket.getString("id"))));
          
      
      
      
      }
  
  
  
  
  
 
  
    return liste;
    }
  
  /**
   * Jeder REWE- Markt hat eine eindeutige ID.
   * Über eine ID lässt sich die Webseite des jeweiligen 
   * REWE- Markts ermitteln.
   * @param Id
   * @return 
   */
    public String getWebsiteByMarketId(String Id){
      try {
          Document doc = Jsoup.connect("https://www.rewe.de/marktseite/?wwident="+Id).get();
          return doc.head().getElementsByTag("link").get(0).attr("href");
      } catch (IOException ex) {
          return "keine Webseite";
      }
    }
}
