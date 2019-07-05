/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shop.model.Baza;
import shop.model.Kategorija;
import shop.model.Proizvod;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class AdministracijaController implements Initializable {

    @FXML
    private TableView tablicaProizvoda;
    @FXML
    private TableColumn idTablica;
    @FXML
    private TableColumn imeTablica;
    @FXML
    private TableColumn kolicinaTablica;
    @FXML
    private TableColumn kategorijaTablica;
    @FXML
    private TableColumn cijenaTablica;
    @FXML
    private Button ḐodajBlag;
    @FXML
    private Label kate;
    @FXML
    private ChoiceBox KategorijaIzbornik;
    
    public static ChoiceBox IzbornikKategorija;
    public static Proizvod Selektoraniproizvod;

     @FXML
     public void odjavise(ActionEvent e){
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
                 
            stage.setScene(scene);
            stage.show();
            kate.getScene().getWindow().hide();
            stage.setResizable(false);
                    
        } 
        catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void DodajBlagajnika(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/DodajBlagajnika.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
                 
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
              
        } 
        catch (IOException ex) {
            System.out.println("NE");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

      @FXML
    void PregledBlagajnika(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/BlagajnikAdministracija.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
                 
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);           
        } 
        catch (IOException ex) {
            System.out.println("NE");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   @FXML
    void DodajAdministracija(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/DodajProizvod.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
                 
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } 
        catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

     @FXML
    void UrediTablica(ActionEvent event) {
        
        if(tablicaProizvoda.getSelectionModel().getSelectedItem()!=null){
            Selektoraniproizvod=(Proizvod) tablicaProizvoda.getSelectionModel().getSelectedItem();
            
            try {
                Parent root;
                root=FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/UrediProizvod.fxml"));
                Stage stage=new Stage();
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
          } catch (IOException ex) {
              Logger.getLogger(AdministracijaController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        
    }
    @FXML
    void ObrisiProizvod(ActionEvent event) {
        
        if(tablicaProizvoda.getSelectionModel().getSelectedItem()!=null){
            Selektoraniproizvod=(Proizvod) tablicaProizvoda.getSelectionModel().getSelectedItem();
  
            try {
                Parent root;
                root=FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/ObrisiProizvod.fxml"));
                Stage stage=new Stage();
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
          } catch (IOException ex) {
              Logger.getLogger(AdministracijaController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    
    
    }

   @FXML
    void OdaberiKategoriju(ActionEvent event) {
        ObservableList<Proizvod> data = FXCollections.observableArrayList();
        
        PreparedStatement ps = Baza.DB.prepare("SELECT proizvod.id_proizvoda,proizvod.ime,"
                + "proizvod.kolicina,proizvod.cijena,proizvod.aktivnost,kategorija.naziv"
                + " FROM proizvod JOIN kategorija ON proizvod.fk_kategorija=kategorija.id_kategorije"
                + " WHERE kategorija.naziv=? AND proizvod.aktivnost=?");
            try {
                ps.setString(1, KategorijaIzbornik.getValue().toString());
                ps.setBoolean(2,true);
                ResultSet rs =ps.executeQuery();
                try {
                    while (rs.next()) {
                    data.add(new Proizvod(rs.getInt("id_proizvoda"), rs.getString("ime"), rs.getInt("kolicina"),rs.getString("naziv"),rs.getFloat("cijena"),rs.getBoolean("aktivnost")));
                }
                } catch (SQLException ex) {
                    System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdministracijaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        idTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("id"));
        imeTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("ime"));
        kolicinaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("kolicina"));
        kategorijaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("kategorija"));
        cijenaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("cijena"));
        tablicaProizvoda.setItems(data);        
  
    }

     @FXML
    void dodajKategoriju(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/UnesiKategoriju.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(DodajProizvodController.class.getName()).log(Level.SEVERE, null, ex);
        }

 
    }

    @FXML
    void ObrisiKategoriju(ActionEvent event) {
        
        List<Proizvod> proizvodi=Proizvod.getAll();
        String kategorija = (String)KategorijaIzbornik.getValue();
        Kategorija kat;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        if(kategorija !=null){
            try {
                //System.out.println(kategorija);
                //Dohvatim iz baze kategoriju s tim nazivom zato sto metoda Proizvod.getAll() dohvaca sve proizvode ali u varijablu kategorija vraca strani kljuc na kategoriju
                PreparedStatement ps = Baza.DB.prepare("SELECT * FROM kategorija WHERE aktivnost=? AND naziv=?");
                
                ps.setBoolean(1, true);
                ps.setString(2,kategorija);
                ps.execute();
                
                ResultSet rs =ps.getResultSet();
                if(rs.first()){
                    kat=new Kategorija (rs.getInt("id_kategorije"),rs.getString("naziv"),rs.getBoolean("aktivnost"));
                    
                    //Sve proizvode s tom kategorijom brisem iz baze odnosno postavljam im vrijednost akktivnost na false
                    for(int i=0;i<proizvodi.size();i++){
                        //System.out.println(proizvodi.get(i).getKategorija()+"  "+kat.getId());
                        if(Integer.valueOf(proizvodi.get(i).getKategorija()) == kat.getId()){
                            System.out.println("uspijesno obrisani proizvodi");
                            proizvodi.get(i).obrisi(proizvodi.get(i).getId());
                        }
                    }
                    //Onda brišem iz baze i tu kategoriju odnosno postavljam joj vrijednost aktivnost na false
                    if(kat.obrisi()){
                        KategorijaIzbornik.getItems().removeAll(kat.getNaziv());
                        prikazUtablici();
                        alert.setTitle("Uspiješno");
                        alert.setHeaderText("Poruka!");
                        alert.setContentText("Uspiješno ste obrisali kategoriju i sve pripadajuće proizvode te kategorije ako ih je bilo!"); 
                        alert.showAndWait();
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(AdministracijaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Greška!");
            alert.setContentText("Niste izabrali kategoriju za obrisati!"); 
            alert.showAndWait();
        }
        
    }   
   
    public void prikazUtablici(){
        
        ObservableList<Proizvod> data = Proizvod.listaProizvoda();
        
        idTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("id"));
        imeTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("ime"));
        kolicinaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("kolicina"));
        kategorijaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("kategorija"));
        cijenaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("cijena"));
        tablicaProizvoda.setItems(data);
    }
    
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prikazUtablici();
        
        List<Kategorija> kategorija=Kategorija.getAllKategorija();
        
        for(int i=0;i<kategorija.size();i++){
            KategorijaIzbornik.getItems().add(kategorija.get(i).getNaziv());
        } 
        //Da bi mogao dodati novu kategoriju u ChoiseBox iz drugog kontrolera
        IzbornikKategorija=KategorijaIzbornik;
    } 
    
}
