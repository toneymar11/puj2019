
package shop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Observable;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shop.model.Baza;
import shop.model.Kategorija;
import shop.model.Kosarica;
import shop.model.Osoba;
import static shop.model.Osoba.LogiraniKorisnik;
import shop.model.Prodaja;
import shop.model.Proizvod;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class ShopBlagajnikController implements Initializable {

    @FXML
    private TableView tablicaProizvoda;
    @FXML
    private TableView tablicaKosarica;
    @FXML
    private TableColumn imeKosarica;
    @FXML
    private TableColumn kategorijaKosarica;
    @FXML
    private TableColumn cijenaKosarica;
    @FXML
    private Label cijenaLabel;
    @FXML
    private ChoiceBox KategorijaIzbornik;
    @FXML
    private Label prodanoLabel;

    
    private ObservableList<Proizvod> kosaricaData = FXCollections.observableArrayList();

    float ukupnacijena=0;
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
    void DodajUKosaricu(ActionEvent event) {
        
        
        if(tablicaProizvoda.getSelectionModel().getSelectedItem()!=null){
            Proizvod proizvod=(Proizvod)tablicaProizvoda.getSelectionModel().getSelectedItem();
         

            if(proizvod.getKolicina()!=0){
                ukupnacijena+=proizvod.getCijena();
                cijenaLabel.setText(String.valueOf(ukupnacijena));
            
                Integer br=proizvod.getKolicina();
                
                proizvod.setKolicina(br-1);
               
                kosaricaData.add(proizvod);

            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Nemoguće dodati proizvod!");
                alert.setContentText("Trenutno nema više na stanju tog proizvoda!");
                alert.showAndWait();
            }
            
        }
        
        imeKosarica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("ime"));
        kategorijaKosarica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("kategorija"));
        cijenaKosarica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("cijena"));
        tablicaKosarica.setItems(kosaricaData);

    }
      @FXML
    void Prodaj(ActionEvent event) {
        
        if(!kosaricaData.isEmpty()){
                 
            Date datum=new Date();
            float cijenaKos=0;
        
            for(int i=0;i<kosaricaData.size();i++){
                cijenaKos+=kosaricaData.get(i).getCijena();
            }
            
            Kosarica racun=new Kosarica(datum,LogiraniKorisnik.getId(),cijenaKos);
            if(racun.spasiKosaricu()){
                System.out.println("uspjesno prodano");
                
                for(int i=0;i<kosaricaData.size();i++){
                    System.out.println(racun.getId_kosarica());
                    Prodaja proizvod=new Prodaja(racun.getId_kosarica(),kosaricaData.get(i).getId());
                    //System.out.println(kosaricaData.get(i).getKolicina()+"     "+racun.getId_kosarica()+"      "+kosaricaData.get(i).getId());
                    proizvod.spasiProdaju();
                }   
            
                for(int i=0;i<kosaricaData.size();i++){
                    System.out.println(kosaricaData.get(i).getKolicina()+"       "+kosaricaData.get(i).getKolicina());
                    try {
                        PreparedStatement ps=Baza.DB.prepare("UPDATE proizvod SET kolicina=? WHERE id_proizvoda=?");

                        ps.setInt(1 ,kosaricaData.get(i).getKolicina());
                        ps.setInt(2, kosaricaData.get(i).getId());
                        ps.executeUpdate();
                        System.out.println("Uspješno ste azurirali proizvod u bazu");
 
            
                    } 
                    catch (SQLException ex) {
                        System.out.println("Pogrška : Niste azurirali proizvod u bazu");
                        Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
    
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Uspješna prodaja!");
                alert.setContentText(" Ime i Prezime blagajnika"+"\t"+Osoba.LogiraniKorisnik.getIme()+" "+Osoba.LogiraniKorisnik.getPrezime()+
                        " \n\n "+"Datum"+"\t"+datum+" \n\n Ukupna cijena"+" \t"+cijenaKos+"  KM");
                
                alert.showAndWait();
          
                
                ukupnacijena=0;
                cijenaLabel.setText(String.valueOf(ukupnacijena));
                kosaricaData.clear();
                prikazUtablici();
            }
        }
        else{
            Alert alertPraznaKosarica = new Alert(Alert.AlertType.INFORMATION);
            alertPraznaKosarica.setTitle("Neuspjenšno");
            alertPraznaKosarica.setHeaderText("Neuspješna prodaja!");
            alertPraznaKosarica.setContentText("Košarica prazna");
                
            alertPraznaKosarica.showAndWait();
        }
    }

     @FXML
    void UkloniIzKosarice(ActionEvent event) {
        if(tablicaKosarica.getSelectionModel().getSelectedItem()!=null){
            Proizvod proizvod=(Proizvod)tablicaKosarica.getSelectionModel().getSelectedItem();
            
            ukupnacijena-=proizvod.getCijena();
            cijenaLabel.setText(String.valueOf(ukupnacijena));
            
            Integer br=proizvod.getKolicina();
           // System.out.println(br);
            proizvod.setKolicina(br+1);
           // System.out.println(proizvod.getKolicina());
             
            kosaricaData.remove(proizvod);
        }

    }

     @FXML
    void OdaberiKategoriju(ActionEvent event) {
        ObservableList<Proizvod> data = FXCollections.observableArrayList();
        
        PreparedStatement ps = Baza.DB.prepare("SELECT proizvod.id_proizvoda,proizvod.ime,proizvod.kolicina,proizvod.cijena,proizvod.aktivnost,kategorija.naziv FROM proizvod JOIN kategorija ON proizvod.fk_kategorija=kategorija.id_kategorije WHERE kategorija.naziv=? AND proizvod.aktivnost=?");
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
                Logger.getLogger(ShopBlagajnikController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        idTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("id"));
        imeTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("ime"));
        kolicinaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, Integer>("kolicina"));
        kategorijaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("kategorija"));
        cijenaTablica.setCellValueFactory(new PropertyValueFactory<Proizvod, String>("cijena"));
        tablicaProizvoda.setItems(data);
    }

     @FXML
    void odjavise(ActionEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
                 
            stage.setScene(scene);
            stage.show();
            prodanoLabel.getScene().getWindow().hide();
            stage.setResizable(false);
                    
        } 
        catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prikazUtablici();
        
        List<Kategorija> kategorija=Kategorija.getAllKategorija();
        
        for(int i=0;i<kategorija.size();i++){
            KategorijaIzbornik.getItems().add(kategorija.get(i).getNaziv());
    }
    }
    
}
