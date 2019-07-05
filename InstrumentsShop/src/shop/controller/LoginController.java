
package shop.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shop.model.Osoba;
import static shop.model.Osoba.LogiraniKorisnik;

/**
 * FXML Controller class
 *
 * @author Tony
 */
public class LoginController {

    @FXML
    Label statusPrijava;
    
    @FXML
    TextField imePrijava;
    
    @FXML
    PasswordField lozinkaPrijava;
    
    @FXML
    private Button prijaviseBtn;
    
    private String e_mail;
    private  String lozinka;
    
    @FXML
    public void prijavise (ActionEvent e) {
       e_mail = imePrijava.getText();
       lozinka = lozinkaPrijava.getText();
        
        if (e_mail.equals("") || lozinka.equals("")) {
            statusPrijava.setText("Morate unijeti sve vrijednosti!");
        } else {
            if (Osoba.logiraj(e_mail, lozinka)) {
                
                if(LogiraniKorisnik.isAdministrator()){
                    try {
                        statusPrijava.setTextFill(Color.GREEN);
                        statusPrijava.setText("Uspješno ste se prijavili");
                        Parent root;
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/Administracija.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setTitle("Administrator page");
                        stage.setScene(scene);
                        stage.show();
                        statusPrijava.getScene().getWindow().hide();
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }
                else{
                    try {
                        statusPrijava.setTextFill(Color.GREEN);
                        statusPrijava.setText("Uspješno ste se prijavili");
                        Parent root;
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("shop/view/ShopBlagajnik.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setTitle("Blagajnik page");
                        stage.setScene(scene);
                        stage.show();
                        statusPrijava.getScene().getWindow().hide();
                    } 
                    catch (IOException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                }

            } 
            else {
                statusPrijava.setText("Korisnicki podatci nisu ispravni!");
            }
        }
    }

}
