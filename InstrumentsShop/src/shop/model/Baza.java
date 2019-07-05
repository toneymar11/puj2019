package shop.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tony
 */
public class Baza extends Konekcija{
    
    public static final Baza DB = new Baza();
    private Statement iskaz;
    
    public Baza(){
        super();
    }

    public Baza(String host, String korisnik, String lozinka, String baza) {
        super(host, korisnik, lozinka, baza);
    }
    
    public ResultSet select(String sql){
        try{
            this.iskaz=this.konekcija.createStatement();
            return this.iskaz.executeQuery(sql);
        }catch(SQLException ex){
            System.out.println("Greska, select iskaz nije se izvršio");
            return null;
        }
    }
    public PreparedStatement prepare(String sql){
        try{
            return this.konekcija.prepareStatement(sql);
        }catch(SQLException ex){
            System.out.println("Nastala je greška prilikom izvršavanja upita");
        }
        return null;
    }
     public PreparedStatement prepareID_kosarica(String sql, int RETURN_GENERATED_KEYS) {
        try {
            return this.konekcija.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom izvršavanja upit");
            return null;
        }
        
    }
}
