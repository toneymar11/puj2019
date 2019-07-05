
package shop.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Tony
 */
public class Osoba {
    
    private Integer id;
    private String ime;
    private String prezime;
    private String e_mail;
    private String lozinka;
    private boolean administrator;

    
    public static Osoba LogiraniKorisnik;
    
    
    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }


    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Osoba(Integer id,String ime, String prezime, String e_mail, String lozinka, boolean administrator) {
        this.id=id;
        this.ime = ime;
        this.prezime = prezime;
        this.e_mail = e_mail;
        this.lozinka = lozinka;
        this.administrator=administrator;
    }
    
    public static boolean logiraj (String e_mail, String lozinka) {
        //Baza db = new Baza();
        PreparedStatement ps = Baza.DB.prepare("SELECT * FROM osoba WHERE e_mail =? AND lozinka=? AND aktivnost=?");
        try {
            ps.setString(1, e_mail);
            ps.setString(2, lozinka);
            ps.setBoolean(3, true);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Osoba logiraniKorisnik = new Osoba(
                            rs.getInt("id_osoba"),
                            rs.getString("ime"),
                            rs.getString("prezime"),
                            rs.getString("e_mail"),
                            rs.getString("lozinka"),
                            rs.getBoolean("administrator")
                    );
                LogiraniKorisnik=logiraniKorisnik;
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
            return false;
        }
    }
    
    public boolean registracija () {
        //Baza db = new Baza();
        PreparedStatement ps = Baza.DB.prepare("INSERT INTO osoba VALUES (null, ?, ?, ?, ?, ?, ?)");
        try {
            ps.setString(1, this.ime);
            ps.setString(2, this.prezime);
            ps.setString(3, this.e_mail);
            ps.setString(4, this.lozinka);
            ps.setBoolean(5, this.administrator);
            ps.setBoolean(6, true);
            ps.executeUpdate();
            System.out.println("Uspješno ste dodali u bazu");
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
            return false;
        }
    }
    
    public void UrediProfil(){
        
        PreparedStatement ps =Baza.DB.prepare("UPDATE osoba SET ime=?, prezime=?, e_mail=?, lozinka=? WHERE id_osoba=?");
        try {
            ps.setString(1, this.ime);
            ps.setString(2, this.prezime);
            ps.setString(3, this.e_mail);
            ps.setString(4, this.lozinka);
            ps.setInt(5, this.id);
            ps.executeUpdate();
            System.out.println("Uspješno ste ažurirali u bazu osoba");
            
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom azuriranja osoba: "+ex.getMessage());
        }
       
    }
    
    public static List<Osoba> getAll () {
        try{
            List<Osoba> korisnici = new ArrayList <>();
           
            
            ResultSet rs = Baza.DB.select("SELECT * FROM osoba WHERE aktivnost=true");
            while (rs.next()) {
                korisnici.add (new Osoba (
                        rs.getInt("id_osoba"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("e_mail"),
                        rs.getString("lozinka"),
                        rs.getBoolean("administrator")
                ));
            }
            return korisnici;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
    public static ObservableList<Osoba> listaBlagajnika () {
        ObservableList<Osoba> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT * FROM osoba WHERE aktivnost=true");
        
        try {
            while (rs.next()) {
                lista.add (new Osoba (
                        rs.getInt("id_osoba"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("e_mail"),
                        rs.getString("lozinka"),
                        rs.getBoolean("administrator")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
        
    public boolean obrisi(){
        
        try {
            PreparedStatement ps=Baza.DB.prepare("UPDATE osoba SET aktivnost=? WHERE id_osoba=?");
            
            ps.setBoolean(1 ,false);
            ps.setInt(2, this.id);
            ps.executeUpdate();
            System.out.println("Uspješno ste azurirali aktivnost u bazu");
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Pogrška : Niste azurirali aktivnost proizvod u bazu");
            Logger.getLogger(Osoba.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }        
    
    
}
