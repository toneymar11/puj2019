
package shop.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony
 */
public class Prodaja {
    
    private Integer id_prodaje;
    private Integer fk_kosarice;
    private Integer fk_proizvoda;

    public Integer getId_prodaje() {
        return id_prodaje;
    }

    public void setId_prodaje(Integer id_prodaje) {
        this.id_prodaje = id_prodaje;
    }

    public Integer getFk_kosarice() {
        return fk_kosarice;
    }

    public void setFk_kosarice(Integer fk_kosarice) {
        this.fk_kosarice = fk_kosarice;
    }

    public Integer getFk_proizvoda() {
        return fk_proizvoda;
    }

    public void setFk_proizvoda(Integer fk_proizvoda) {
        this.fk_proizvoda = fk_proizvoda;
    }

    public Prodaja(Integer fk_kosarice, Integer fk_proizvoda) {
        this.fk_kosarice = fk_kosarice;
        this.fk_proizvoda = fk_proizvoda;
    }
    
    public boolean spasiProdaju(){
        try{
            PreparedStatement ps = Baza.DB.prepare("INSERT INTO prodaje VALUES(null,?,?)");
            
            ps.setInt(1, this.fk_proizvoda);
            ps.setInt(2, this.fk_kosarice);
            ps.executeUpdate();
            System.out.println("Uspješno ste dodali u bazu u tablicu prodaje");
            return true;
            
        }catch(SQLException ex){
            
            System.out.println("Pogrška : Niste dodali proizvod u bazu u tablicu prodaje");
            Logger.getLogger(Prodaja.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
