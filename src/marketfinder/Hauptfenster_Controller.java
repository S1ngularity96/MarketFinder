
package marketfinder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Andrei
 */
public class Hauptfenster_Controller implements Initializable{
    
    ArrayList<Market> markt_liste;
    //Slider Controlls
    @FXML private Slider kilometer_slider;
          private int kilometerstand_slider = 0;
    @FXML private Label kilometerstand_label;
    
    //Rewe- Tab- Controlls
    @FXML private TextField rewe_plz_text;
    @FXML private Button rewe_commitSearch;
    
    //Tabelle für die Anzeige von Märkten
    @FXML private TableView tabelle_Maerkte;
    
    
    
    
    /**
     * Zeigt eine Tabelle mit Märkten an
     * @param markets 
     */
    private void erzeugeTabelle(ArrayList<Market> markets){
        
        
        
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
    }
    
}
