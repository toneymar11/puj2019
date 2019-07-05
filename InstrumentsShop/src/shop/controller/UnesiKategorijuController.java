package shop.controller;


import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shop.model.Kategorija;

public class UnesiKategorijuController {

    @FXML
    private TextField UnesiKategoriju;

    @FXML
    private Label status;
    
    private String kategorija;
   // public static String dodanaKategorija;

    @FXML
    void SpasiKategoriju(ActionEvent event) {
        Integer br=0;
        kategorija=UnesiKategoriju.getText();
        
        List<Kategorija> k=Kategorija.getAllKategorija();
        
        String naziv1,naziv2;
        
        //Da li unesena kategorija ima u bazi i ako da inkrementiraj brojač
        for(int i=0;i<k.size();i++){
            
            naziv1=k.get(i).getNaziv().toLowerCase();
            naziv2=this.kategorija.toLowerCase();
            if(naziv1.equals(naziv2)){
                br++;
            }
        } 
        
        //Ispitivam unesenu kategoriju da li je valjana i ako je unosi mo je u bazu i ChoiseBox u glavnom administrativnom prozoru
        if(kategorija.equals("")){
            status.setText("Unesite kategoriju.");
        }
        else if(kategorija.length()<2){
            status.setText("Kategorija manja od 2 znaka.");
        }
        else if(br != 0){
            status.setText("Unesena kategorija vec postoji u bazi");
        }
        else{
            
            if(Kategorija.spasi(kategorija)){
                AdministracijaController.IzbornikKategorija.getItems().add(kategorija);   
                status.getScene().getWindow().hide();
            }
            
        }
    }

}
