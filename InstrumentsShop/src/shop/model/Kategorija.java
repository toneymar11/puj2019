
package shop.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony
 */
public class Kategorija {
    
    private Integer id;
    private String naziv;
    private boolean aktivnost;

    public Kategorija(Integer id, String naziv, boolean aktivnost) {
        this.id = id;
        this.naziv = naziv;
        this.aktivnost = aktivnost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isAktivnost() {
        return aktivnost;
    }

    public void setAktivnost(boolean aktivnost) {
        this.aktivnost = aktivnost;
    }
    
    public static List<Kategorija> getAllKategorija(){
        try{
            List<Kategorija> kategorija = new ArrayList<>();
            
            ResultSet rs = Baza.DB.select("SELECT * FROM kategorija WHERE aktivnost=true");
            
            while(rs.next()){
                kategorija.add(new Kategorija (
                        rs.getInt("id_kategorije"),
                        rs.getString("naziv"),
                        rs.getBoolean("aktivnost")
                ));
            }
            return kategorija;
        }catch(SQLException ex){
            return new ArrayList<>();
        }
    }
    
    public static boolean spasi(String naziv){
        try{ 
            
            PreparedStatement ps = Baza.DB.prepare("INSERT INTO kategorija VALUES(null,?,?)");
            
            ps.setString(1, naziv);
            ps.setBoolean(2, true);
            ps.executeUpdate();
            System.out.println("Uspješno ste unijeli kategoriju");
            return true;
            
        }catch(SQLException ex){
            
            System.out.println("Greška : niste unijeli kategoriju");
            Logger.getLogger(Kategorija.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean obrisi(){
        
        try{
            PreparedStatement ps = Baza.DB.prepare("UPDATE kategorija SET aktivnost=? WHERE id_kategorije=?");
            
            ps.setBoolean(1, false);
            ps.setInt(2, this.id);
            ps.executeUpdate();
            System.out.println("Uspješno ste azurirali aktivnost u bazu u tablicu kategorija");
            return true;
        }catch(SQLException ex){
            System.out.println("Pogreška: Niste ažurirali aktivnost kategorije!");
             Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
