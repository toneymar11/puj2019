
package shop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import shop.model.Proizvod;

/**
 *
 * @author Tony
 */

public class ObrisiProizvodController {
    
    @FXML
    private Label label;


    @FXML
    void DA(ActionEvent event) {
        
        Proizvod OdabraniProizvod=AdministracijaController.Selektoraniproizvod;
          
        if(Proizvod.obrisi(OdabraniProizvod.getId())){

            label.getScene().getWindow().hide();
        }  
    }
    
    @FXML
    void NE(ActionEvent event) {
        
        label.getScene().getWindow().hide();    
        
    }


}
