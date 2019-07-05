
package shop.controller;


import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import shop.model.Osoba;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class DodajBlagajnikaController  {

    @FXML
    private TextField Ime;
    @FXML
    private TextField Prezime;
    @FXML
    private Label status;
    @FXML
    private TextField E_mail;
    @FXML
    private PasswordField PonLozinka;
    @FXML
    private PasswordField Lozinka;
    
    private String ime;
    private String prezime;
    private String e_mail;
    private String lozinka;
    private String ponLozinka;
    
      @FXML
    void spasi(ActionEvent event) {
        
        ime = Ime.getText();
        prezime = Prezime.getText();
        e_mail = E_mail.getText();
        lozinka= Lozinka.getText();
        ponLozinka= PonLozinka.getText();
        
        Integer br=0;
        
        List<Osoba> korisnici = Osoba.getAll();
        
        for(int i=0;i<korisnici.size();i++){
            //System.out.println(korisnici.get(i).getE_mail()+"  "+e_mail);
            
            if(korisnici.get(i).getE_mail().equals(e_mail)){
                System.out.println("Postoji registriran blagajnik s takvim e_mailom");
                br++;
            }
        }
        if(br != 0){
            status.setText("Postoji registriran korisnik s takvim e_mailom");
        }
        else{
            if (ime.equals("") || prezime.equals("")|| e_mail.equals("") || lozinka.equals("") || ponLozinka.equals("") ) {
            status.setText("Morate unijeti sve vrijednosti!");
            }
            else if(ime.length() < 3 || prezime.length() <3){
                status.setText("Ime ili prezime je manje od 3 znaka");
            }else if(lozinka.length() < 5 || ponLozinka.length() <5 || !lozinka.equals(ponLozinka)){
                status.setText("Lozinke se ne podudaraju ili su manje od 5 znakova");
            }
            else if(e_mail.length() <7) {
                status.setText("E-mail mora imati najmanje 7 znakova");
            }
            else if(!e_mail.contains("@")){
                status.setText("Pogrešan e-mail nema znaka @");
            }
            else {
                
                Osoba s=new Osoba(0,ime,prezime,e_mail,lozinka,false);
                if (s.registracija()) {
                    status.setTextFill(Color.GREEN);
                    status.setText("Uspješno dodali blagajnika");
                    status.getScene().getWindow().hide();
                } else {
                    status.setText("Korisnicki podatci nisu ispravni!");
                }
            }
        }
    }
    
}
