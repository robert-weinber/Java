package Modell;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author rober
 */
public class Munka/* extends JPanel*/ {
    private int ID;
    private String csoport;
    private String datum;
    private String megrendelo;
    private String megnevezes;
    private String felelos;
    private String uzletkoto;
    private String tipus;
    private String status;
    private int osszkoltseg;
    private int felid;
    private int uzlid;

    public Munka(int ID, String csoport, String datum, String megrendelo, String megnevezes, String felelos, String uzletkoto, String tipus, String status, int osszkoltseg, int felid, int uzlid) {
        this.ID = ID;
        this.csoport = csoport;
        this.datum = datum;
        this.megrendelo = megrendelo;
        this.megnevezes = megnevezes;
        this.felelos = felelos;
        this.uzletkoto = uzletkoto;
        this.tipus = tipus;
        this.status = status;
        this.osszkoltseg = osszkoltseg;
        this.felid = felid;
        this.uzlid = uzlid;
        /*this.setLayout(new GridLayout(1,7));
        this.add(new JLabel(ID+""));
        this.add(new JLabel(csoport));
        this.add(new JLabel(datum));
        this.add(new JLabel(megrendelo));
        this.add(new JLabel(megnevezes));
        this.add(new JLabel(felelos));
        this.add(new JLabel(uzletkoto));
        this.add(new JLabel(tipus));
        this.add(new JLabel(status));
        this.add(new JLabel(osszkoltseg+""));*/
    }

    public int getID() {
        return ID;
    }

    public String getCsoport() {
        return csoport;
    }

    public String getDatum() {
        return datum;
    }

    public String getMegrendelo() {
        return megrendelo;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public String getFelelos() {
        return felelos;
    }

    public String getUzletkoto() {
        return uzletkoto;
    }

    public String getTipus() {
        return tipus;
    }

    public String getStatus() {
        return status;
    }

    public int getOsszkoltseg() {
        return osszkoltseg;
    }
    
    public Object[] getAdatok() {
        return new Object[]{ID+"",felid+"",uzlid+"",megrendelo,megnevezes,tipus,status,osszkoltseg+"",csoport,felelos,uzletkoto,datum};
    }
    
    @Override
    public String toString() {
        return megrendelo+" - "+megnevezes;
    }
}