
package marketfinder;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.json.JSONException;

/**
 *
 * @author Andrei
 */
public class Hauptfenster_Controller implements Initializable{
    
    ArrayList<Market> markt_liste = new ArrayList<>();
    ObservableList<Market> tabelle_Observer_List;
    //Slider Controlls
    @FXML private Slider kilometer_slider;
          private int kilometerstand_slider = 0;
    @FXML private Label kilometerstand_label;
    
    //Rewe- Tab- Controlls
    @FXML private TextField rewe_plz_text;
    @FXML private Button rewe_commitSearch;
    
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
     * Liest die Postleitzahl für Rewe ein und speichert die Martkliste
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
                    showAlertMessage("IOException", "Fehler bei der Verbinden mit der Webseite",
                            "Überprüfen Sie Ihre Internetverbindung.");
                } catch (JSONException ex) {
                    showAlertMessage("JSONExcpetion", "Fehler beim Lesen der Daten", "Die empfangenden Daten "
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
