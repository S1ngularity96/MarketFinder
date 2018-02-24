
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    ArrayList<Market> markt_liste = new ArrayList<>();
    ObservableList<Market> tabelle_Observer_List;
    
    
    
    //Conrolls für GelbeSeiten Tab
    //Slider Controlls
    @FXML private Slider kilometer_slider;
          private int kilometerstand_slider = 1;
    @FXML private Label kilometerstand_label;
    @FXML private TextField gelbeseiten_stichwort;
    @FXML private TextField gelbeSeiten_postleitzahl;
    @FXML private Button gelbeSeiten_commitSearch;
    //--------------------------------------
    
    
    
    // Controlls für REWE TAB 
    //Rewe- Tab- Controlls
    @FXML private TextField rewe_plz_text;
    @FXML private Button rewe_commitSearch;
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
    
  
    /**
     * Zeigt eine Tabelle mit Märkten an
     * @param markets 
     */
    private void erzeugeTabelle(ArrayList<Market> markets){
        tabelle_Observer_List.clear();
        markt_liste.clear();
        
        markt_liste = markets;
        for(Market markt : markets){
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
            }}
    
      
    
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
                export.exportCSVFromList(markt_liste);
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
        tabelle_Observer_List = FXCollections.observableArrayList(markt_liste);
        tabelle_Maerkte.setItems(tabelle_Observer_List);
        

        
        
        
        
        
    }
    
}
