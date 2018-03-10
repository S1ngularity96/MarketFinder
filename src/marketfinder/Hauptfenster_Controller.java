
package marketfinder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.json.JSONException;

/**
 *
 * @author Andrei
 */
public class Hauptfenster_Controller implements Initializable{
    
    
    //Tabelle und Liste
	
    ArrayList<Market> markt_liste_original = new ArrayList<>();
    ArrayList<Market> markt_liste_oberserved = new  ArrayList<>();
    ObservableList<Market> tabelle_Observer_List;
    
    
    
    //Conrolls für GelbeSeiten Tab
    //Slider Controlls
    @FXML private Slider kilometer_slider;
          private int kilometerstand_slider = 1;
    @FXML private Label kilometerstand_label;
    @FXML private TextField gelbeseiten_stichwort;
    @FXML private TextField gelbeSeiten_postleitzahl;
    @FXML private Button gelbeSeiten_commitSearch;
    @FXML private ListView plz_search_gelbeSeiten;
    @FXML private ObservableList<String> plz_search_observer_gelbeSeiten;
    
    //--------------------------------------
    
    
    
    // Controlls für REWE TAB 
    //Rewe- Tab- Controlls
    @FXML private TextField rewe_plz_text;
    @FXML private Button rewe_commitSearch;
    @FXML private ListView plz_search_rewe;
    @FXML private ObservableList<String> plz_search_observer_rewe;
    //--------------------------------------
    
    
    
    //Tabelle für die Anzeige von Märkten
    @FXML private TableView tabelle_Maerkte;
    //Spalten von der Tabelle
    @FXML private TableColumn column_marktname;
    @FXML private TableColumn column_branche;
    @FXML private TableColumn column_stadt;
    @FXML private TableColumn column_postleitzahl;
    @FXML private TableColumn column_strasse;
    @FXML private TableColumn column_hausnummer;
    @FXML private TableColumn column_entfernung;
    @FXML private TableColumn column_telefon;
    @FXML private TableColumn column_webseite;
    
    //Checkboxen für das Filtern
    @FXML private CheckBox checkbox_marktname;
    @FXML private CheckBox checkbox_branche;
    @FXML private CheckBox checkbox_stadt;
    @FXML private CheckBox checkbox_plz;
    @FXML private CheckBox checkbox_strasse;
    @FXML private CheckBox checkbox_hausnummer;
    @FXML private CheckBox checkbox_entfernung;
    @FXML private CheckBox checkbox_telefon;
    @FXML private CheckBox checkbox_webseite;
    
    //FilterHash
    
    private long FILTER_HASH = 0;
  
    //PLZ Suche
    PLZfinder plz_finder = new PLZfinder();
    ArrayList<String> plz_suche = new ArrayList<>();
    /**
     * Zeigt eine Tabelle mit Märkten an
     * @param markets 
     */
    private void erzeugeTabelle(ArrayList<Market> markets){
    	markt_liste_original = markets;
        
    	tabelle_Observer_List.clear();
        markt_liste_oberserved.clear();
        
       
        for(Market markt : markt_liste_original){
            markt_liste_oberserved.add(markt);
            tabelle_Observer_List.add(markt);
        }
        
       
    
    }
  
    
    /**
     * Ändert den Wert vom Umkreis und die Anzeige davon
     * @param km 
     */
    private void aendereKilometerstand(int km){
        kilometerstand_slider = km;
        kilometerstand_label.setText(kilometerstand_slider+ " Kilometer");
    }
    
    /**
     * Liest die Postleitzahl für Rewe ein und speichert eine Liste mit den
     * gefunden Märkten
     * Fängt Fehler ab: Falsche Eingaben
     */
    @FXML public void rewe_CommitSearch(){
        if(!rewe_plz_text.getText().isEmpty()){
            try{
                int rewe_plz = Integer.parseInt(rewe_plz_text.getText());
                ReweParser parser = new ReweParser();
                
                try {
                    ArrayList<Market> parsed = parser.setNewRequest(rewe_plz);
                    erzeugeTabelle(parsed);
                } catch (IOException ex) {
                    showAlertMessage("IOException", "Fehler beim Verbinden mit der Webseite",
                            "Überprüfen Sie Ihre Internetverbindung.");
                } catch (JSONException ex) {
                    showAlertMessage("JSONExcpetion", "Fehler beim Lesen der Daten", 
                              "Die empfangenden Daten "
                            + "sind fehlerhaft. Versuchen Sie erneut die Suche zu starten.");
                }
                    
                
            }catch(NumberFormatException ex){
                showAlertMessage("Fehler", "Keine Postleitzahl erkannt", "Bitte "
                               + "geben Sie nur Zahlen ein, die eine Postleitzahl darstellen "
                               + ", um eine Rewe- Marktsuche starten zu können.");
                
            }
        }else{
            showAlertMessage("Fehler", "Postleitzahl fehlt", "Bitte geben Sie "
                           + "eine Postleitzahl ein, um eine Rewe- Marktsuche starten"
                           + " zu können.");
        }
        
        this.applyFilter();
        
    }
    
    
    
    
    /**
     * Liest Stichwort und Postleitzahl für Rewe ein und speichert eine Liste 
     * mit den gefunden Märkten
     */
    @FXML public void gelbeSeiten_CommitSearch(){
        String stichwort = gelbeseiten_stichwort.getText();
        String plz = gelbeSeiten_postleitzahl.getText();
        
        
            
            if(!stichwort.isEmpty() && !plz.isEmpty() ){
                try{ // Überprüfen, ob es sich um eine Postleitzahl handelt
                    Integer.parseInt(plz);
                }catch(NumberFormatException ex){
                    showAlertMessage("Fehler", "Postleitzahl fehlt", "Bitte geben Sie "
                    + "eine Postleitzahl ein, um eine Rewe- Marktsuche starten"
                    + " zu können.");
                    return;
                }
                
                //Parsen von Gelbe Seiten und Liste mit Märkten sichern
            GelbeSeitenParser parser = new GelbeSeitenParser();
            parser.setRequestURL(stichwort, "", plz, this.kilometerstand_slider);
            try {
                ArrayList<Market> parsed = parser.commitRequestAndReceive();
                erzeugeTabelle(parsed);
            } catch (PageLoadingException ex) {
                showAlertMessage("PageLoadingException", 
                                 "Keine Antwort von GelbeSeiten.de ", 
                                 "Ihre Eingaben könnten falsch sein, oder "
                               + "ansonten überprüfen Sie Ihre Internetverbindung.");
            }
            }else{
                showAlertMessage("Fehler", "Stichwort oder Postleitzahl fehlt",
                        "Für eine korrekte Suche müssen Sie ein Stichwort und"
                      + " eine Postleitzahl eingeben.");
            }
            	this.applyFilter();
    }
    
      
    
        /**
         * Speichert die Tabelle als CSV- Datei
         */
        @FXML public void marketFinder_CSV_speichern(){
            File datei  = null;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Tour speichern");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Datei", "*.csv"));
            //Dialog zum Speichern öffnen
            datei = fileChooser.showSaveDialog(tabelle_Maerkte.getScene().getWindow());
            
            if(datei!=null){
                CSVExport export = new CSVExport();
                //CSV String erstellen
                
                export.exportCSVFromList(markt_liste_oberserved);
                try {
                    export.printToFile(datei);
                } catch (IOException ex) {
                    showAlertMessage("Datei- Fehler", "Fehler beim Speichern", 
                                     "Überprüfen Sie den Speicherort, und beachten Sie"
                                   + ", falls Sie eine Datei überschreiben, dass diese"
                                   + " nicht geöffnet ist.");
                }
            }
            
        }
    
    
    
    /**
     * Fassade für eine Alert- Nachricht
     * @param title
     * @param HeaderText
     * @param Content 
     */
    public void showAlertMessage(String title,String HeaderText,String Content){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle(title);
                alert.setHeaderText(HeaderText);
                alert.setContentText(Content);
                alert.showAndWait();
    }
    
    // Filtereinstellungen
    
    /**
     * Verändert die Tablle nach den Filtereinstellungen
     */
    public void applyFilter() {
    	tabelle_Observer_List.clear();
        markt_liste_oberserved.clear();
        for(Market markt : markt_liste_original){
        	if((FILTER_HASH & (~markt.getFilterHash())) == 0) {
                        markt_liste_oberserved.add(markt);
        		tabelle_Observer_List.add(markt);
        	}
        }
    }
    
    /**
     * Verändert die Filtereinstellungen für den Tabelleninhalt
     */
    @FXML public void changeFilterParameters(){
        FILTER_HASH = 0;
        
        if(checkbox_marktname.isSelected()){
            FILTER_HASH |= Market.FILTER_MARTKNAME;
        }
        
        if(checkbox_branche.isSelected()){
            FILTER_HASH |= Market.FILTER_BRANCHE;
        }
        
        if(checkbox_stadt.isSelected()){
            FILTER_HASH |= Market.FILTER_STADT;
        }
        
        if(checkbox_plz.isSelected()){
            FILTER_HASH |= Market.FILTER_PLZ;
        }
        
        if(checkbox_strasse.isSelected()){
            FILTER_HASH |= Market.FILTER_STRASSE;
        }
        
        if(checkbox_hausnummer.isSelected()){
            FILTER_HASH |= Market.FILTER_HAUSNUMMER;
        }
        
        if(checkbox_entfernung.isSelected()){
            FILTER_HASH |= Market.FILTER_ENTFERNUNG;
        }
        
        if(checkbox_telefon.isSelected()){
            FILTER_HASH |= Market.FILTER_TELE;
        }
        
        if(checkbox_webseite.isSelected()){
            FILTER_HASH |= Market.FILTER_WEBSEITE;
        }
        
        this.applyFilter();
        //System.out.println("Der FilterHash: " + FILTER_HASH);
    }
    
    
    // Funktionen für eine einfachere Suche nach PLZ 
    @FXML public void gelbeSeiten_plz_search(){
        String plz_text = gelbeSeiten_postleitzahl.getText();
        
        if(!(plz_text.length() <=5)){
            plz_search_gelbeSeiten.setVisible(false);
            return;
        }
        
        try{
            int plz = Integer.parseInt(plz_text);
            
            plz_suche.clear();
            plz_search_observer_gelbeSeiten.clear();
            
            for(String metaData :plz_finder.setNewRequest(plz)){
                plz_suche.add(metaData);
                plz_search_observer_gelbeSeiten.add(metaData);
            }
            
            plz_search_gelbeSeiten.setVisible(false);
            plz_search_gelbeSeiten.setVisible(true);
            
            
            
        }catch(NumberFormatException e){
            plz_search_gelbeSeiten.setVisible(false);
        } catch (IOException ex) {
            plz_search_rewe.setVisible(false);
        } catch (JSONException ex) {
           plz_search_rewe.setVisible(false);
        }
        
    }
    
    @FXML public void rewe_plz_search(){
        String plz_text = rewe_plz_text.getText();
        
        if(!(plz_text.length() <=5)){
            plz_search_rewe.setVisible(false);
            return;
        }
        
        try{
            int plz = Integer.parseInt(plz_text);
            plz_suche.clear();
            plz_search_observer_rewe.clear();
            
            for(String metaData :plz_finder.setNewRequest(plz)){
                plz_suche.add(metaData);
                plz_search_observer_rewe.add(metaData);
            }
            plz_search_gelbeSeiten.setVisible(false);
            plz_search_rewe.setVisible(true);
            
        }catch(NumberFormatException e){
            plz_search_rewe.setVisible(false);
        } catch (IOException ex) {
            plz_search_rewe.setVisible(false);
        } catch (JSONException ex) {
            plz_search_rewe.setVisible(false);
        }
    }
    
    /**
     * Starteigenschaften werden hier an die Benutzer- 
     * oberfläche übergeben
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ChangeListener für Slider, um Kilometerstand zu ändern
        
        kilometer_slider.valueProperty().addListener(new ChangeListener<Number>() {
    @Override
    public void changed(ObservableValue<? extends Number> observable, 
                        Number oldValue, Number newValue) {
                 aendereKilometerstand((int) kilometer_slider.getValue());
            }
        });
        
        //SetCellValueFactory
        
        column_marktname.setCellValueFactory(new PropertyValueFactory<Market,String>("marktname"));
        column_branche.setCellValueFactory(new PropertyValueFactory<Market,String>("branche"));
        column_stadt.setCellValueFactory(new PropertyValueFactory<Market,String>("stadt"));
        column_postleitzahl.setCellValueFactory(new PropertyValueFactory<Market,String>("plz"));
        column_strasse.setCellValueFactory(new PropertyValueFactory<Market,String>("strasse"));
        column_hausnummer.setCellValueFactory(new PropertyValueFactory<Market,String>("hausnummer"));
        column_entfernung.setCellValueFactory(new PropertyValueFactory<Market,String>("entfernung"));
        column_telefon.setCellValueFactory(new PropertyValueFactory<Market,String>("tele"));
        column_webseite.setCellValueFactory(new PropertyValueFactory<Market,String>("webseite"));
        
        
        //Oberservierende Liste einsetzen
        tabelle_Observer_List = FXCollections.observableArrayList(markt_liste_oberserved);
        tabelle_Maerkte.setItems(tabelle_Observer_List);
        
        //Observierende Listen für die Plz- Suche
        plz_search_gelbeSeiten.setVisible(false);
        plz_search_rewe.setVisible(false);
        
        plz_search_observer_gelbeSeiten = FXCollections.observableArrayList(plz_suche);
        plz_search_gelbeSeiten.setItems(plz_search_observer_gelbeSeiten);
        plz_search_observer_rewe = FXCollections.observableArrayList(plz_suche);
        plz_search_rewe.setItems(plz_search_observer_rewe);
        
        
        
        
    }
    
}
