/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Livem
 */
public class GelbeSeitenParser {
    //Request-URL für Gelbeseiten
    private String requestURL;
    
    
    
    public GelbeSeitenParser() {
        
        
        
    }
    
    /**
     * Gibt eine Markliste zurück, basierend nach den Sucheinstellungen
     * @return Liste mit den Märkten
     * @throws PageLoadingException 
     */
    public ArrayList<Market> commitRequestAndReceive() throws PageLoadingException{
        try {
            Document doc = getHtmlDoc();
            return teilnehmerListe(doc);
        } catch (PageLoadingException ex) {
            throw new PageLoadingException();
        }
    }
    
    /**
     * Setze eine neue Request 
     * @param stichwort Stichwort z.B Edeke oder Rewe
     * @param stadt Stadt 
     * @param plz Postleitzahl
     * @param umkreisKm Umkreis in Kilometern
     */
    public void setRequestURL(String stichwort,String stadt, String plz, int umkreisKm) {
        umkreisKm = umkreisKm *1000;
        this.requestURL = "https://www.gelbeseiten.de/"+stichwort+"/"+stadt+",,"+plz+",,,umkreis-"+umkreisKm+"";
        
        
        
        
    }
    
    /**
     * Setze eine neue Request
     * @param stichwort Stichwort z.B Edeke oder Rewe
     * @param stadt Stadt 
     * @param plz Postleitzahl
     */
    public void setRequestURL(String stichwort,String stadt, String plz){
        this.requestURL = "https://www.gelbeseiten.de/"+stichwort+"/"+stadt+",,"+plz;
      
    }
    
    /**
     * 
     * @param url URL von Gelbeseiten mit einer Anfrage
     */
    public void setRequestURL(String url){
        this.requestURL = url;
    }
    
    /**
     * Gibt die eingestellte Request zurück
     * @return Liefert eine voreingestellte Request zurück
     */
    public String getRequestURL() {
        return requestURL;
    }
    
    /**
     * Eine Webseite mit Ergebnissen wird als Dokument 
     * gespeichert
     * @return GelbeSeiten Webseite als Dokument gemäß
     * der Request
     * @throws marketfinder.PageLoadingException Seite 
     * konnte nicht gefunden oder geladen werden.
     *  
     */
    public Document getHtmlDoc() throws PageLoadingException{
        Document gelbeSeite = null;
        
        try {
           gelbeSeite = Jsoup.connect(getRequestURL()).userAgent("Mozilla/5.0(Windows NT 10.0; Win64; x64; rv:58.0) ").get();
        } catch (IOException ex) {
            throw new PageLoadingException();
        }
        
        return gelbeSeite;
    }
    
    /**
     * Gibt Urls zurück für die nächsten Seiten mit den Ergebnissen
     * @param webseite
     * @return 
     */
    public ArrayList<String> getNextURLs(Document webseite){
        //Finde die nächsten Seiten nach der Klasse "Hidden-xs -desktop"
        Elements elemente = webseite.getElementsByClass("hidden-xs desktop").select("a[href]");
         
        //Kopiere Links in die Liste und gib diese zurück
         ArrayList<String> links = (ArrayList<String>) elemente.eachAttr("href");
         return links;
        
    }
     
    /**
     * Gibt die einzelnen Ids der gefundenen Elemente aus der Suche heraus.
     * 
     * @param webseite 
     * @return Eine Liste mit den Ids
     */
    public ArrayList<String> getItemIds(Document webseite){
        ArrayList<String> ids_liste = new ArrayList<>();
        String ids = "";
        Pattern pattern = Pattern.compile("\"id\" : \"[0-9]*\"");
        Matcher matcher = pattern.matcher(webseite.toString());
        
        //Mögliche ids finden
        while(matcher.find()){
            ids = ids + matcher.group() + "\n";
        }
        
        
        //Ids extrahieren
        
        pattern = Pattern.compile("[0-9]+");
        matcher = pattern.matcher(ids);
        
        //Ids Anzeigen/ in einer Liste speichern
        
        while(matcher.find()){
            ids_liste.add(matcher.group());
        }
        
        
        return ids_liste;
        
    }
    
    /**
     * Erstellt eine Teilnehmerliste aus Teilnehmerelemente und gibt eine
     * Marktliste zurück
     * @param webseite GelbeSeiten Webseite
     * @return Teilnehmerliste 
     */
    public ArrayList<Market> teilnehmerListe(Document webseite){
        ArrayList<Market> markets = new ArrayList<>();
        ArrayList<Element> teilnehmer = getTeilnehmerAsList(webseite);
        
        teilnehmer.forEach((element) -> {
            markets.add(new GelbeSeitenMarkt(
                    elementAnzeigen(element, "m08_teilnehmername teilnehmername entry"),
                    elementAnzeigen(element, "branchen_box"),
                    getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "addressLocality"),
                    getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "postalCode"),
                    getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "streetAddress") ,
                    elementAnzeigen(element, "teilnehmerentfernung"),
                    elementAnzeigen(element, "m08_telefonnummer_"),
                    element.getElementsByClass("website hidden-xs").select("a[href]").attr("href")));
        });
        
        return markets;
     
    }
    
    /**
     * Alle Teilnehmen von der ersten Seite werden 
     * ausgegeben
     * @param webseite GelbeSeiten Webseite
     */
    private void teilnehmerAusgeben(Document webseite){
        ArrayList<Element> teilnehmer = getTeilnehmerAsList(webseite);
        for(Element element : teilnehmer){
                System.out.println("Marktname: "+ elementAnzeigen(element, "m08_teilnehmername teilnehmername entry"));
                System.out.println("Branche: "+elementAnzeigen(element, "branchen_box"));
                System.out.println("Stadt: "+getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "addressLocality"));
                System.out.println("PLZ: "+getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "postalCode"));
                System.out.println("Straße: "+getAttributeAddr(element.getElementsByClass("adresse m08_adresse"), "streetAddress"));
                System.out.println("Entfernung: "+elementAnzeigen(element, "teilnehmerentfernung"));
                System.out.println("Telefon: "+elementAnzeigen(element, "m08_telefonnummer_"));
                System.out.println("Webseite: "+element.getElementsByClass("website hidden-xs").select("a[href]").attr("href"));
                System.out.println("---------------------------------------------------------------------\n");
        }
    }
    
    /**
     * Gibt alle Teilnehmerelemente aus der Suche zurück
     * @param webseite GelbeSeiten Webseite
     * @return Teilnehmerelemente
     */
    public  ArrayList<Element> getTeilnehmerAsList(Document webseite){
        ArrayList<String> nextURLs = getNextURLs(webseite);
        ArrayList<String> ids = getItemIds(webseite);
        ArrayList<Element> teilnehmer = new ArrayList<>();
        
        
        //Alle TeilnehmerIDS auslesen
        for(String id : ids){
           teilnehmer.add(webseite.getElementById("teilnehmer_"+id));
           
        }
        
        //Die nächsten Seiten untersuchen
        for(int anzahlSeiten = 0; anzahlSeiten < nextURLs.size(); anzahlSeiten++){
            ids.clear();
        try {
            //Dockument der nächsten Seite anfordern
            Document nextWebsite = Jsoup.connect(nextURLs.get(anzahlSeiten)).userAgent("Mozilla/5.0(Windows NT 10.0; Win64; x64; rv:58            .0) ").get();
       
            //Ids zurückgeben
            ids  =   getItemIds(nextWebsite);
      
            
            //Weitere Teilnehmerelemente zurückgeben
            for(String id : ids){
                teilnehmer.add(nextWebsite.getElementById("teilnehmer_"+id));
            }
              
            } catch (IOException ex) {
                System.err.println("Error reading next Pages");
            }
        }
        
        return teilnehmer;
    }
    
    /**
     * Gibt ein Element über einen Key als String zurück
     * @param element Element aus einer Webseite
     * @param key Schlüsselwort für die Suche
     * @return Element als Text
     */
    public String elementAnzeigen(Element element,String key){
        try{
            return element.getElementsByClass(key).text();
        }catch(NullPointerException e){
            return "Keine Information vorhanden";
        }
        
    }
    
    /**
     * Filtert die einzelnen Attribute aus einer Addresse
     * @param elements Elemente aus einer Webseite
     * @param key Schlüsselwort für die Suche
     * @return Attribt als Text
     */
    public String getAttributeAddr(Elements elements, String key){
        
        elements.select("address").text();
        return elements.select("address").select("span[itemprop="+key+"]").text();
    }
    
}
