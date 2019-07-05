
package shop.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tony
 */
public class Kosarica {
    
    private Integer id_kosarica;
    private Date datum;
    private Integer fkBlagajnika;
    private float Cijena;

    public Integer getId_kosarica() {
        return id_kosarica;
    }

    public void setId_kosarica() {
        try{
            ResultSet rs = Baza.DB.select("SELECT LAST_INSERT_ID()");
            if(rs.next()){
                id_kosarica=rs.getInt(1);
                System.out.println(id_kosarica);
            }
            System.out.println(id_kosarica);
        }catch(SQLException ex){
            Logger.getLogger(Kosarica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getFkBlagajnika() {
        return fkBlagajnika;
    }

    public void setFkBlagajnika(Integer fkBlagajnika) {
        this.fkBlagajnika = fkBlagajnika;
    }

    public float getCijena() {
        return Cijena;
    }

    public void setCijena(float Cijena) {
        this.Cijena = Cijena;
    }

    public Kosarica(Date datum, Integer fkBlagajnika, float Cijena) {
        this.datum = datum;
        this.fkBlagajnika = fkBlagajnika;
        this.Cijena = Cijena;
    }
    
   public boolean spasiKosaricu(){
       try{
           PreparedStatement ps = Baza.DB.prepare("INSERT INTO kosarica VALUES(null,?,?,?)");
    
           java.sql.Date sqlDate = new java.sql.Date(this.datum.getTime());
           
           ps.setDate(1, sqlDate);
           ps.setFloat(2, this.Cijena);
           ps.setInt(3, this.fkBlagajnika);
           ps.executeUpdate();
           setId_kosarica();
           System.out.println("Uspješno ste dodali u bazu u tablicu kosarica");
           return true;
           
       }catch(SQLException ex){
           
            System.out.println("Greška : Niste dodali proizvod u bazu u tablicu racun");
            Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
            return false;  
       }
   }
    
}
