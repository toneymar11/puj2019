
package shop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shop.model.Osoba;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class BlagajnikAdministracijaController implements Initializable {

    @FXML
    private TableView tablicaBlagajnik;
    @FXML
    private TableColumn idBlagajnik;
    @FXML
    private TableColumn imeBlagajnik;
    @FXML
    private TableColumn prezimeBlagajnik;
    @FXML
    private TableColumn e_mailBlagajnik;
    @FXML
    private TableColumn lozinkaBlagajnik;
    @FXML
    private TextField id;
    @FXML
    private TextField ime;
    @FXML
    private TextField prezime;
    @FXML
    private TextField e_mail;
    @FXML
    private TextField lozinka;
    
    private Integer ID;
    private String IME;
    private String PREZIME;
    private String E_MAIL;
    private String LOZINKA;
    
    Osoba blagajnik;

   @FXML
    void obrisiBlagajnika(ActionEvent event) {
        if(tablicaBlagajnik.getSelectionModel().getSelectedItem()!=null){
            
            blagajnik=(Osoba)tablicaBlagajnik.getSelectionModel().getSelectedItem();
            if(blagajnik.isAdministrator()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Greška!");
                alert.setContentText("Nemožete obrisati sami sebe odnosno administratora!"); 
                alert.showAndWait();
            }
            else{
                blagajnik.obrisi();
            
                prikazUtablici();
            }
           
        }
    }
      @FXML
    void spremi(ActionEvent event){
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        if(!id.getText().isEmpty()){
            
            IME=ime.getText();
            PREZIME=prezime.getText();
            E_MAIL=e_mail.getText();
            LOZINKA=lozinka.getText();
            
            Integer br=0;
        
            List<Osoba> korisnici = Osoba.getAll();
        
            if (ime.equals("") || prezime.equals("")|| e_mail.equals("") || lozinka.equals("")) {
            
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Greška!");
                alert.setContentText("Morate unijeti sve vrijednosti!"); 
                alert.showAndWait();    
            }    
            else if(IME.length() < 2 || PREZIME.length() <3){
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Greška!");
                alert.setContentText("Ime manje od 2 ili prezime je manje od 3 znaka!");
                alert.showAndWait();
            }
            else if(LOZINKA.length() < 5){
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Greška!");
                alert.setContentText("Lozinka manja od 5 znakova!");
                alert.showAndWait();
            }
            else{
                blagajnik.setIme(IME);
                blagajnik.setPrezime(PREZIME);
                blagajnik.setE_mail(E_MAIL);
                blagajnik.setLozinka(LOZINKA);
                blagajnik.UrediProfil();
            
                prikazUtablici();  
            }
        }
        else{

            alert.setTitle("Upozorenje");
            alert.setHeaderText("Nemoguće ažurirati blagajnika!");
            alert.setContentText("Niste odabrali ni jednog blagajnika za ažuriranje!");
            alert.showAndWait();
        }
    }
    
  
      @FXML
    void urediBlagajnika(ActionEvent event) {
        
        if(tablicaBlagajnik.getSelectionModel().getSelectedItem()!=null){
            
            blagajnik=(Osoba)tablicaBlagajnik.getSelectionModel().getSelectedItem();
            
            id.setText(String.valueOf(blagajnik.getId()));
            System.out.println(blagajnik.getId()+"  "+id.getText());
            ime.setText(blagajnik.getIme());
            prezime.setText(blagajnik.getPrezime());
            e_mail.setText(blagajnik.getE_mail());
            lozinka.setText(blagajnik.getLozinka());
            
        }
    }
  
    public void prikazUtablici(){
        
        ObservableList<Osoba> data = Osoba.listaBlagajnika();

        idBlagajnik.setCellValueFactory(new PropertyValueFactory<Osoba, Integer>("id"));
        imeBlagajnik.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeBlagajnik.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        e_mailBlagajnik.setCellValueFactory(new PropertyValueFactory<Osoba, String>("e_mail"));
        lozinkaBlagajnik.setCellValueFactory(new PropertyValueFactory<Osoba, String>("lozinka"));
        tablicaBlagajnik.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prikazUtablici();
    }
    
}
