package shop.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import shop.model.Kategorija;
import shop.model.Proizvod;

public class UrediProizvodController  implements Initializable{

    @FXML
    private TextField imeUredi;

    @FXML
    private TextField kolicinaUredi;

    @FXML
    private ChoiceBox<String> KategorijaIzbornik;

    @FXML
    private TextField cijeanUredi;

    @FXML
    private Label status;
   
    private String ime;
    private Integer kolicina;
    private String Kolicina;
    private String cijena;
    private Float Cijena;
    private String kategorija;
    Proizvod selektiraniProizvod;
    
    @FXML
    void UrediProizvod(ActionEvent event) {
        Integer br=0,brCijena=0,brIme=0;
        ime = imeUredi.getText();
        Kolicina =kolicinaUredi.getText();
        cijena = cijeanUredi.getText();
        kategorija=KategorijaIzbornik.getValue().toString();
        
       for(int i=0;i<Kolicina.length();i++){
            if(Kolicina.charAt(i) != '0' && Kolicina.charAt(i) != '1' && Kolicina.charAt(i) != '2' && Kolicina.charAt(i) != '3' && Kolicina.charAt(i) != '4' && Kolicina.charAt(i) != '5' && Kolicina.charAt(i) != '6' && Kolicina.charAt(i) != '7' && Kolicina.charAt(i) != '8' && Kolicina.charAt(i) != '9'){
            
                br++;
                System.out.println(Kolicina.charAt(i));
            }
        }
        for(int i=0;i<cijena.length();i++){
            if(cijena.charAt(i) != '0' && cijena.charAt(i) != '1' && cijena.charAt(i) != '2' && cijena.charAt(i) != '3' && cijena.charAt(i) != '4' && cijena.charAt(i) != '5' && cijena.charAt(i) != '6' && cijena.charAt(i) != '7' && cijena.charAt(i) != '8' && cijena.charAt(i) != '9' && cijena.charAt(i) != '.'){
            
                brCijena++;
                System.out.println(cijena.charAt(i));
            }
        }
        
        List<Proizvod> proizvod = Proizvod.getAll();
        
        for(int i=0;i<proizvod.size();i++){
           
            
            String name=proizvod.get(i).getIme().toLowerCase();
            String name1=ime.toLowerCase();
            
            if(name.equals(name1) && !proizvod.get(i).getId().equals(selektiraniProizvod.getId())){
                brIme++;
            }
        }
    
        if (ime.equals("") || Kolicina.equals("")|| cijena.equals("") || kategorija.equals("")) {
            status.setText("Morate unijeti sve vrijednosti!");
        }
        else if(ime.length()<2){
            status.setText("Ime je manje od 2 znaka");
        }
        else if(br !=0 || brCijena !=0){
            
            status.setText("Pogrešan unus količine ili cijene");
        }
        else if(Kolicina.charAt(0)=='0' && Kolicina.length() != 1){
            status.setText("Pogrešan unus količine");
        }
        else if(cijena.charAt(0)=='.'){
            status.setText("Pogrešan unus cijene");
        }
        else if(cijena.charAt(0)=='0' && cijena.length()!=1){
            status.setText("Pogrešan unus cijene");
        }
        else if(cijena.charAt(0)=='0' && cijena.charAt(1)=='.' && cijena.length()==2){
            status.setText("Pogrešan unus cijene");
        }
        else if(brIme != 0){
            status.setText("Postoji proizvod s takvim imenom u bazi");
        }
        else{
                
            kolicina = Integer.valueOf(kolicinaUredi.getText());
            Cijena = Float.valueOf(cijeanUredi.getText());
            System.out.println(kolicina+" "+ime+" "+Cijena+" "+kategorija);
            
            if(Proizvod.azuriraj(selektiraniProizvod.getId(),ime, kolicina, Cijena, kategorija)){
                
                status.setTextFill(Color.GREEN);
                status.setText("Uspješno ste ažurirali u bazu");
                
                imeUredi.setText("");
                cijeanUredi.setText("");
                kolicinaUredi.setText("");
            }
        }        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selektiraniProizvod=AdministracijaController.Selektoraniproizvod;
        
        List<Kategorija> kategorija=Kategorija.getAllKategorija();
        
        for(int i=0;i<kategorija.size();i++){
            KategorijaIzbornik.getItems().add(kategorija.get(i).getNaziv());
        }
   
        KategorijaIzbornik.setValue(selektiraniProizvod.getKategorija());
        
        imeUredi.setText(selektiraniProizvod.getIme());
        kolicinaUredi.setText(String.valueOf(selektiraniProizvod.getKolicina()));
        cijeanUredi.setText((String.valueOf(selektiraniProizvod.getCijena())));
               
    }

}
