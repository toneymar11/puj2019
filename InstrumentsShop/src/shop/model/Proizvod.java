
package shop.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tony
 */
public class Proizvod {
    
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty ime = new SimpleStringProperty();
    SimpleIntegerProperty kolicina = new SimpleIntegerProperty();
    SimpleStringProperty kategorija = new SimpleStringProperty();
    SimpleFloatProperty cijena = new SimpleFloatProperty();
    private boolean aktivnost;

    public Proizvod(Integer id,String ime, Integer kolicina, String kategorija, Float cijena ,boolean aktivnost) {
        this.id = new SimpleIntegerProperty (id);
        this.ime = new SimpleStringProperty(ime);
        this.kolicina = new SimpleIntegerProperty(kolicina);
        this.kategorija = new SimpleStringProperty(kategorija);
        this.cijena = new SimpleFloatProperty(cijena);
        this.aktivnost=aktivnost;
    }

    public Integer getId() {
        return id.get();
    }

    public String getIme() {
        return ime.get();
    }
    public boolean isAktivnost() {
        return aktivnost;
    }

    public void setAktivnost(boolean aktivnost) {
        this.aktivnost = aktivnost;
    }
    public void setIme(String ime) {
        this.ime = new SimpleStringProperty(ime);
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public Integer getKolicina() {
        return kolicina.get();
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = new SimpleIntegerProperty(kolicina);
    }

    public String getKategorija() {
        return kategorija.get();
    }

    public void setKategorija(String kategorija) {
        this.kategorija = new SimpleStringProperty(kategorija);
    }

    public Float getCijena() {
        return cijena.get();
    }

    public void setCijena(Float cijena) {
        this.cijena = new SimpleFloatProperty(cijena);
    }
    public static ObservableList<Proizvod> listaProizvoda () {
        ObservableList<Proizvod> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT proizvod.id_proizvoda,proizvod.ime,proizvod.kolicina,proizvod.cijena,proizvod.aktivnost,kategorija.naziv FROM proizvod JOIN kategorija ON proizvod.fk_kategorija=kategorija.id_kategorije WHERE proizvod.aktivnost=true");
        
        try {
            while (rs.next()) {
                lista.add(new Proizvod(rs.getInt("id_proizvoda"), rs.getString("ime"), rs.getInt("kolicina"),rs.getString("naziv"),rs.getFloat("cijena"),rs.getBoolean("aktivnost")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    
    //
    public static List<Proizvod> getAll () {
        try{
            List<Proizvod> proizvod = new ArrayList <>();
           
            
            ResultSet rs = Baza.DB.select("SELECT * FROM proizvod WHERE aktivnost=true");
            while (rs.next()) {
                proizvod.add (new Proizvod (
                        rs.getInt("id_proizvoda"),
                        rs.getString("ime"),
                        rs.getInt("kolicina"),
                        rs.getString("fk_kategorija"),
                        rs.getFloat("cijena"),
                        rs.getBoolean("aktivnost")
                ));
            }
            return proizvod;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
    public static boolean spasi(String ime,Integer kolicina,Float cijena,String kategorija){
        
        try {
            PreparedStatement ps=Baza.DB.prepare("INSERT INTO proizvod VALUE(null,?,?,?,(SELECT id_kategorije FROM kategorija WHERE naziv=?),?)");
            
            ps.setString(1 ,ime);
            ps.setInt(2 ,kolicina);
            ps.setFloat(3 , cijena);
            ps.setString(4 , kategorija);
            ps.setBoolean(5, true);
            ps.executeUpdate();
            System.out.println("Uspješno ste dodali u bazu");
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Pogrška : Niste dodali proizvod u bazu");
            Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public static boolean azuriraj(Integer id,String ime,Integer kolicina,Float cijena,String kategorija){
        
        try {
            PreparedStatement ps=Baza.DB.prepare("UPDATE proizvod SET ime=?, kolicina=?, cijena=?, fk_kategorija=(SELECT id_kategorije FROM kategorija WHERE naziv=?), aktivnost=? WHERE id_proizvoda=?");
            
            ps.setString(1 ,ime);
            ps.setInt(2 ,kolicina);
            ps.setFloat(3 , cijena);
            ps.setString(4 , kategorija);
            ps.setBoolean(5, true);
            ps.setInt(6, id);
            ps.executeUpdate();
            System.out.println("Uspješno ste azurirali u bazu");
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Pogrška : Niste azurirali proizvod u bazu");
            Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //Ažuriraj aktivnost proizoda 
    public static boolean obrisi(Integer id){
        
        try {
            PreparedStatement ps=Baza.DB.prepare("UPDATE proizvod SET aktivnost=? WHERE id_proizvoda=?");
            
            ps.setBoolean(1 ,false);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Uspješno ste azurirali aktivnost u bazu");
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Pogrška : Niste azurirali aktivnost proizvod u bazu");
            Logger.getLogger(Proizvod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
}
