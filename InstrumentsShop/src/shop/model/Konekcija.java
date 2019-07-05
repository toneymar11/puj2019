package shop.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tony
 */
public class Konekcija {
    
    private String host;
    private String korisnik;
    private String lozinka;
    private String baza;
    
    protected Connection konekcija;

    public Konekcija(String host, String korisnik, String lozinka, String baza) {
        this.host = host;
        this.korisnik = korisnik;
        this.lozinka = lozinka;
        this.baza = baza;
        this.spoji();
    }

    public Konekcija() {
        
         this.host = "localhost";
        this.korisnik = "root";
        this.lozinka = "";
        this.baza = "puj";
        this.spoji();
    }
    
    public void spoji(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try{
                this.konekcija = DriverManager.getConnection(
                        "jdbc:mysql://"+this.host+"/"
                                +this.baza
                                +"?user="+this.korisnik
                                +"&password="+this.lozinka
                );
            }catch(SQLException ex){
                System.out.println("Neuspjesno povezivanje, greska u podacima");               
            }
        }catch(ClassNotFoundException ex){
            System.out.println("Klasa nije pronadjena");
        }
    
    }
    public void odspoji(){
        try{
            this.konekcija.close();
        }catch(SQLException ex){
            System.out.println("Neuspješno odspajanje");
        }
    }  
}
