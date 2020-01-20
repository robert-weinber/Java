package Modell;

/**
 *
 * @author rober
 */
public class Pozicio {
    private int ID;
    private String nev;
    private String leiras;

    public Pozicio(int id, String nev, String leiras) {
        this.ID = id;
        this.nev = nev;
        this.leiras = leiras;
    }

    public int getID() {
        return ID;
    }

    public String getNev() {
        return nev;
    }

    public String getLeiras() {
        return leiras;
    }
    
    @Override
    public String toString() {
        return nev;
    }
    
}
